package controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import entity.Autor;
import entity.Colecao;
import entity.ColecaoAutor;
import entity.ColecaoGenero;
import entity.Editora;
import entity.Genero;
import entity.Selo;
import entity.Usuario;
import repository.AutorRepository;
import repository.ColecaoRepository;
import repository.EditoraRepository;
import repository.GeneroRepository;
import repository.SeloRepository;
import repository.UsuarioRepository;
import util.Uteis;

@Named(value="colecaoController")
@SessionScoped
public class ColecaoController implements Serializable {

	private static final long serialVersionUID = 4389998621549207729L;
	
	@Inject
	private ColecaoRepository colecaoRepository;
	
	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private EditoraRepository editoraRepository;

	@Inject
	private AutorRepository autorRepository;

	@Inject
	private GeneroRepository generoRepository;

	@Inject
	private SeloRepository seloRepository; 
	
	private Colecao colecao;
	private Collection<Editora> listaEditoras;
	private Collection<Selo> listaSelos;
	private Collection<String> listaNomeEditoras;
	private Collection<String> listaNomeSelos;
	
	private boolean isVolumeUnico;
	private Collection<Colecao> listaColecoes;
	private Colecao colecaoSelecionada;
	
	public String iniciarProcesso(boolean volumeUnico) {
		colecao = new Colecao();
		listaEditoras = editoraRepository.listarEditoras();
		listaSelos = seloRepository.listarSelos();
		isVolumeUnico = volumeUnico;
		
		return "manterColecao?faces-redirect=true";
	}
	
    public Collection<String> completeEditora(String query) {
        String queryLowerCase = query.toLowerCase();
        listaNomeEditoras = new ArrayList<>();
        
        for (Editora editora : listaEditoras) {
			listaNomeEditoras.add(editora.getNome());
		}

        return listaNomeEditoras.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public Collection<String> completeSelo(String query) {
    	String queryLowerCase = query.toLowerCase();
    	listaNomeSelos = new ArrayList<>();
    	
    	for (Selo selo : listaSelos) {
    		listaNomeSelos.add(selo.getDescricao());
    	}
    	
    	return listaNomeSelos.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
    
    public Collection<Autor> completeAutor(String query) {
        String queryLowerCase = query.toLowerCase();
        Collection<Autor> autores = autorRepository.listarAutores();
        return autores.stream().filter(t -> t.getNome().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }

    public Collection<Genero> completeGenero(String query) {
    	String queryLowerCase = query.toLowerCase();
    	Collection<Genero> generos = generoRepository.listarGeneros();
    	return generos.stream().filter(t -> t.getDescricao().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }
	
	public Boolean salvar() {
		try {
			Colecao c = getColecao();

			Usuario usuario = usuarioRepository.obterUsuarioPorId(getIdUsuarioLogado());
			c.setUsuario(usuario);
			
			if(!validaColecao()) {
				Uteis.mensagemAtencao("Coleção já cadastrada!");
				return null;
			}
				
			Editora editora = editoraRepository.obterEditoraPorNome(c.getEditoraSelecionada());
			if(editora == null) {
				editora = new Editora(c.getEditoraSelecionada());
				editoraRepository.salvar(editora);
			}
			c.setEditora(editora);
			
			if(c.getSeloSelecionado() != null && c.getSeloSelecionado() != "") {
				Selo selo = seloRepository.obterSeloPorNome(c.getSeloSelecionado());
				if(selo == null) {
					selo = new Selo(c.getSeloSelecionado(), editora);
					seloRepository.salvar(selo);
				}
				c.setSelo(selo);
			}
			
			c.setVolumeUnico(isVolumeUnico);
			
			colecaoRepository.salvar(c);

			salvarRelacionamentos(c);
			
			return true;
		}catch (Exception e) {
			e.getStackTrace();
		}
		return null;	
	}


	public void salvarRelacionamentos(Colecao c) {
		
		for (Autor autor : c.getAutoresSelecionados()) {
			ColecaoAutor colecaoAutor = new ColecaoAutor(c, autor);
			colecaoRepository.salvarColecaoAutor(colecaoAutor);
		}

		for (Genero genero : c.getGenerosSelecionados()) {
			ColecaoGenero colecaoGenero = new ColecaoGenero(c, genero);
			colecaoRepository.salvarColecaoGenero(colecaoGenero);
		}
	}

	public boolean validaColecao() {
		Colecao c = colecaoRepository.obterColecaoPorTituloUsuario(colecao.getTitulo(), getIdUsuarioLogado());
		
		if(c != null)
			return false;
		
		return true;
	}
	
	public Integer getIdUsuarioLogado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	
		return (Integer)facesContext.getExternalContext().getSessionMap().get("idUsuarioAutenticado");
	}
	
	public void salvarAutor() {
		if(colecao.getAutorModal() != null) {
			Autor autor = autorRepository.obterAutorPorNome(colecao.getAutorModal());
			
			if(autor == null) {
				autor = new Autor();
				autor.setNome(colecao.getAutorModal());
			
				autorRepository.salvar(autor);
				
				colecao.setAutorModal(null);
				
				PrimeFaces current = PrimeFaces.current();
				current.executeScript("PF('modalAddAutor').hide();");
			}else {
				colecao.setAutorModal(null);
				Uteis.mensagemAtencao("Autor já cadastrado!");
			}
		}else {
			Uteis.mensagemAtencao("Nome do Autor não informado!");
		}
	}

	public void salvarGenero() {
		if(colecao.getGeneroModal() != null) {
			Genero genero = generoRepository.obterGeneroPorDescricao(colecao.getGeneroModal());
			
			if(genero == null) {
				genero = new Genero();
				genero.setDescricao(colecao.getGeneroModal());
				
				generoRepository.salvar(genero);
				
				colecao.setGeneroModal(null);
				
				PrimeFaces current = PrimeFaces.current();
				current.executeScript("PF('modalAddGenero').hide();");
			}else {
				colecao.setAutorModal(null);
				Uteis.mensagemAtencao("Gênero já cadastrado!");
			}
		}else {
			Uteis.mensagemAtencao("Gênero não informado!");
		}
	}

	public Colecao getColecao() {
		if(colecao == null)
			colecao = new Colecao();
		return colecao;
	}

	public void setColecao(Colecao colecao) {
		this.colecao = colecao;
	}

	public Collection<Editora> getListaEditoras() {
		if(listaEditoras == null)
			listaEditoras = new ArrayList<>();
		return listaEditoras;
	}

	public void setListaEditoras(Collection<Editora> listaEditoras) {
		this.listaEditoras = listaEditoras;
	}

	public Collection<String> getListaNomeEditoras() {
		if(listaNomeEditoras == null)
			listaNomeEditoras = new ArrayList<>();
		return listaNomeEditoras;
	}

	public void setListaNomeEditoras(Collection<String> listaNomeEditoras) {
		this.listaNomeEditoras = listaNomeEditoras;
	}

	public Collection<Selo> getListaSelos() {
		return listaSelos;
	}

	public void setListaSelos(Collection<Selo> listaSelos) {
		this.listaSelos = listaSelos;
	}

	public Collection<String> getListaNomeSelos() {
		return listaNomeSelos;
	}

	public void setListaNomeSelos(Collection<String> listaNomeSelos) {
		this.listaNomeSelos = listaNomeSelos;
	}

	public boolean isVolumeUnico() {
		return isVolumeUnico;
	}

	public void setVolumeUnico(boolean isVolumeUnico) {
		this.isVolumeUnico = isVolumeUnico;
	}

	public Collection<Colecao> getListaColecoes() {
		if(listaColecoes == null) {
			listaColecoes = colecaoRepository.listarPorUsuario(getIdUsuarioLogado());
			for (Colecao colecao : listaColecoes) {
				colecao.setListaColecaoAutor(colecaoRepository.listarPorAutoresPorColecao(colecao));
			}
		}	
		return listaColecoes;
	}

	public void setListaColecoes(Collection<Colecao> listaColecoes) {
		this.listaColecoes = listaColecoes;
	}

	public Colecao getColecaoSelecionada() {
		return colecaoSelecionada;
	}

	public void setColecaoSelecionada(Colecao colecaoSelecionada) {
		this.colecaoSelecionada = colecaoSelecionada;
	}
	
	

	
	private CroppedImage croppedImage;

    private UploadedFile originalImageFile;

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public UploadedFile getOriginalImageFile() {
        return originalImageFile;
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = null;
        this.croppedImage = null;
        UploadedFile file = event.getFile();
        if(file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            this.originalImageFile = file;
            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crop() {
        if (this.croppedImage == null || this.croppedImage.getBytes() == null || this.croppedImage.getBytes().length == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cropped successfully."));
        }
    }

    public StreamedContent getImage() {
        return DefaultStreamedContent.builder()
            .contentType(originalImageFile == null ? null : originalImageFile.getContentType())
            .stream(() -> {
                if (originalImageFile == null
                        || originalImageFile.getContent() == null
                        || originalImageFile.getContent().length == 0) {
                    return null;
                }

                try {
                    return new ByteArrayInputStream(originalImageFile.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .build();
    }

    public StreamedContent getCropped() {
        return DefaultStreamedContent.builder()
                .contentType(originalImageFile == null ? null : originalImageFile.getContentType())
                .stream(() -> {
                    if (croppedImage == null
                            || croppedImage.getBytes() == null
                            || croppedImage.getBytes().length == 0) {
                        return null;
                    }

                    try {
                        return new ByteArrayInputStream(this.croppedImage.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .build();
    }

}
