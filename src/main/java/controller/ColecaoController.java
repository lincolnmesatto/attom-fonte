package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

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
	private static Logger logger = Logger.getLogger(ColecaoController.class);
	
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
	
	private boolean isExibir;
	
	public String iniciarProcesso(boolean volumeUnico) {
		colecao = new Colecao();
		carregarListas();
		isVolumeUnico = volumeUnico;
		
		return "manterColecao?faces-redirect=true";
	}
	
	public String iniciarEditar(Integer id, Boolean exibir) {
		isExibir = exibir;
		colecao = colecaoRepository.obterColecaoPorId(id);
		carregarListas();
		isVolumeUnico = colecao.isVolumeUnico();
		
		colecao.setEditoraSelecionada(colecao.getEditora().getNome());
		
		if(colecao.getSelo() != null)
			colecao.setSeloSelecionado(colecao.getSelo().getDescricao());
		colecao.setSeloSelecionado(colecao.getSelo().getDescricao());
		
		Collection<ColecaoAutor> ca = colecaoRepository.listarPorAutoresPorColecao(colecao);
		Collection<ColecaoGenero> cg = colecaoRepository.listarPorGenerosPorColecao(colecao);
		
		for (ColecaoGenero colecaoGenero : cg) {
			colecao.getGenerosSelecionados().add(colecaoGenero.getGenero());
		}
		
		for (ColecaoAutor colecaoAutor : ca) {
			colecao.getAutoresSelecionados().add(colecaoAutor.getAutor());
		}

		return "manterColecao?faces-redirect=true";
	}
	
	public String iniciarProcessoHome() {
		return "home?faces-redirect=true";
	}
	
	public void carregarListas() {
		listaEditoras = editoraRepository.listarEditoras();
		listaSelos = seloRepository.listarSelos();
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
	
	public String salvar() {
		try {
			Colecao c = getColecao();

			Usuario usuario = usuarioRepository.obterUsuarioPorId(getIdUsuarioLogado());
			c.setUsuario(usuario);
			
			if(c.getId() == null) {
				if(!validaColecao()) {
					Uteis.mensagemAtencao("Coleção já cadastrada!");
					logger.error("colecao ja cadastrada");
					return null;
				}
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
			
			if(colecao.getId()!=null)
				colecaoRepository.alterar(c);
			else
				colecaoRepository.salvar(c);

			salvarRelacionamentos(c);
			
			logger.info("colecao cadastrada com sucesso");
			return "home?faces-redirect=true";
		}catch (Exception e) {
			e.getStackTrace();
		}
		return null;	
	}


	public void salvarRelacionamentos(Colecao c) {
		
		Collection<ColecaoAutor> listaColecaoAutor = colecaoRepository.listarPorAutoresPorColecao(c);
		Collection<ColecaoGenero> listaColecaoGenero = colecaoRepository.listarPorGenerosPorColecao(c);
		
		for (Autor autor : c.getAutoresSelecionados()) {
			ColecaoAutor colecaoAutor = new ColecaoAutor(c, autor);
			boolean salvar = true;
			for (ColecaoAutor ca : listaColecaoAutor) {
				if(ca.getAutor().equals(autor)) {
					salvar = false;
					break;
				}	
			}
			
			if(salvar)
				colecaoRepository.salvarColecaoAutor(colecaoAutor);
		}

		for (Genero genero : c.getGenerosSelecionados()) {
			ColecaoGenero colecaoGenero = new ColecaoGenero(c, genero);
			boolean salvar = true;
			for (ColecaoGenero cg : listaColecaoGenero) {
				if(cg.getGenero().equals(genero)) {
					salvar = false;
					break;
				}	
			}
			
			if(salvar)
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
				
				logger.info("autor salvo com sucesso");
				
				PrimeFaces current = PrimeFaces.current();
				current.executeScript("PF('modalAddAutor').hide();");
			}else {
				colecao.setAutorModal(null);
				logger.error("autor ja cadastrado");
				Uteis.mensagemAtencao("Autor já cadastrado!");
			}
		}else {
			Uteis.mensagemAtencao("Nome do Autor não informado!");
			logger.error("autor nao informado");
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
				
				logger.info("genero cadastrado com sucesso");
			}else {
				colecao.setAutorModal(null);
				Uteis.mensagemAtencao("Gênero já cadastrado!");
				logger.error("genero ja cadastrado");
			}
		}else {
			Uteis.mensagemAtencao("Gênero não informado!");
			logger.error("genero nao informado");
		}
	}
	
	public void deletarColecao(Integer id) {
		Colecao colecao = colecaoRepository.obterColecaoPorId(id);

		Collection<ColecaoAutor> ca = colecaoRepository.listarPorAutoresPorColecao(colecao);
		Collection<ColecaoGenero> cg = colecaoRepository.listarPorGenerosPorColecao(colecao);
		
		for (ColecaoGenero colecaoGenero : cg) {
			colecaoRepository.deletar(colecaoGenero);
		}
		
		for (ColecaoAutor colecaoAutor : ca) {
			colecaoRepository.deletar(colecaoAutor);
		}
		
		colecaoRepository.deletar(colecao);
		
		logger.info("colecao deletado com sucesso");
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
//		if(listaColecoes == null) {
			listaColecoes = colecaoRepository.listarPorUsuario(getIdUsuarioLogado());
			for (Colecao colecao : listaColecoes) {
				colecao.setListaColecaoAutor(colecaoRepository.listarPorAutoresPorColecao(colecao));
			}
//		}	
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
	
	public boolean isExibir() {
		return isExibir;
	}

	public void setExibir(boolean isExibir) {
		this.isExibir = isExibir;
	}
}
