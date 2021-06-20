package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import entity.Checklist;
import entity.ChecklistUsuario;
import entity.Usuario;
import repository.ChecklistRepository;
import repository.ChecklistUsuarioRepository;
import repository.UsuarioRepository;
import util.Uteis;

@Named(value="checklistUsuarioController")
@SessionScoped
public class ChecklistUsuarioController implements Serializable {

	private static final long serialVersionUID = -2091336672353982656L;

	private static Logger logger = Logger.getLogger(ChecklistUsuarioController.class);
	
	@Inject
	private ChecklistRepository checklistRepository;
	
	@Inject
	private ChecklistUsuarioRepository checklistUsuarioRepository;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	private Collection<Checklist> listaChecklist;
	private Collection<ChecklistUsuario> listaChecklistUsuario;
	private Collection<ChecklistUsuario> listaChecklistUsuarioAdicionar;

	private Checklist checklistSelecionado;
	
	private Usuario usuario;
	
	private float valorGasto;
	
	public String iniciarProcesso() {
		popular();
		
		valorGasto = 0f;
		
		for (ChecklistUsuario checklistUsuario : listaChecklistUsuario) {
			valorGasto += checklistUsuario.getChecklist().getPreco();
		}
		
		return "listarChecklistUsuario?faces-redirect=true";
	}
	
	public void popular() {
		usuario = usuarioRepository.obterUsuarioPorId(Uteis.getIdUsuarioLogado());
		listaChecklist = checklistRepository.listarChecklistAdmin();
		
		listaChecklistUsuarioAdicionar = new ArrayList<>();
		
		listaChecklistUsuario = new ArrayList<>();
		listaChecklistUsuario = checklistUsuarioRepository.listarChecklistusuario(usuario.getId());
		
		if(listaChecklistUsuario == null)
			listaChecklistUsuario = new ArrayList<>();
	}
	
	public String iniciarIncluir() {
		popular();
		
		for (ChecklistUsuario checklistUsuario : listaChecklistUsuario) {
			for (Checklist checklist : listaChecklist) {
				if(checklistUsuario.getChecklist().equals(checklist)) {
					checklist.setSelecionado(true);
					break;
				}
			}
		}
		
		return "manterChecklistUsuario?faces-redirect=true";
	}

    public String adicionarChecklist() {
    	for (ChecklistUsuario checklistUsuario : listaChecklistUsuarioAdicionar) {
			ChecklistUsuario c = checklistUsuario;
			
			checklistUsuarioRepository.salvar(c);
		}
    	
    	Usuario u = getUsuario();
    	usuarioRepository.updateUsuario(u);
		
		logger.info("checklistUsuario atualizado com sucesso");

		return iniciarProcesso();
    }
    
    public void checkSelecionado(Checklist check) {
    	ChecklistUsuario cUsuario = new ChecklistUsuario(getUsuario(), check);
    	if(check.isSelecionado()) {
    		ChecklistUsuario clu = checklistUsuarioRepository.obterChecklistUsuarioChecklist(getUsuario(), check);
    		
    		if(clu == null)
    			listaChecklistUsuarioAdicionar.add(cUsuario);
    	}else {
    		ChecklistUsuario clu = checklistUsuarioRepository.obterChecklistUsuarioChecklist(getUsuario(), check);
    		if(clu != null)
    			checklistUsuarioRepository.deletar(clu);
    		else {
    			for (ChecklistUsuario checklistUsuario : listaChecklistUsuarioAdicionar) {
					if(check.equals(checklistUsuario.getChecklist())) {
						listaChecklistUsuarioAdicionar.remove(checklistUsuario);
					}
				}
    		}
    	}
    }

	public Collection<Checklist> getListaChecklist() {
		if(listaChecklist == null)
			listaChecklist = new ArrayList<>();
		return listaChecklist;
	}

	public void setListaChecklist(Collection<Checklist> listaChecklist) {
		this.listaChecklist = listaChecklist;
	}

	public Checklist getChecklistSelecionado() {
		return checklistSelecionado;
	}

	public void setChecklistSelecionado(Checklist checklistSelecionado) {
		this.checklistSelecionado = checklistSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<ChecklistUsuario> getListaChecklistUsuario() {
		return listaChecklistUsuario;
	}

	public void setListaChecklistUsuario(Collection<ChecklistUsuario> listaChecklistUsuario) {
		this.listaChecklistUsuario = listaChecklistUsuario;
	}

	public float getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(float valorGasto) {
		this.valorGasto = valorGasto;
	}

	public Collection<ChecklistUsuario> getListaChecklistUsuarioAdicionar() {
		if(listaChecklistUsuarioAdicionar == null)
			listaChecklistUsuarioAdicionar = new ArrayList<>();
		return listaChecklistUsuarioAdicionar;
	}

	public void setListaChecklistUsuarioAdicionar(Collection<ChecklistUsuario> listaChecklistUsuarioAdicionar) {
		this.listaChecklistUsuarioAdicionar = listaChecklistUsuarioAdicionar;
	}

}
