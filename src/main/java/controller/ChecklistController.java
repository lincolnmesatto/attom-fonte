package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import entity.Checklist;
import entity.ChecklistUsuario;
import entity.Editora;
import repository.ChecklistRepository;
import repository.ChecklistUsuarioRepository;
import repository.EditoraRepository;
import util.Uteis;

@Named(value="checklistController")
@SessionScoped
public class ChecklistController implements Serializable {

	private static final long serialVersionUID = 4893657327728525299L;
	private static Logger logger = Logger.getLogger(ChecklistController.class);
	
	@Inject
	private EditoraRepository editoraRepository;
	
	@Inject
	private ChecklistRepository checklistRepository;
	
	@Inject
	private ChecklistUsuarioRepository checklistUsuarioRepository;
	
	private Collection<Editora> listaEditoras;
	private Collection<String> listaNomeEditoras;
	private Checklist checklist;
	
	private Collection<Checklist> listaChecklist;
	private Checklist checklistSelecionado;
	
	public String iniciarProcesso() {
		listaChecklist = checklistRepository.listarChecklistAdmin();
		
		return "listarChecklist?faces-redirect=true";
	}
	
	public String iniciarIncluir() {
		listaEditoras = editoraRepository.listarEditoras();
		
		checklist = new Checklist();
		listaChecklist = new ArrayList<>();
		
		return "manterChecklist?faces-redirect=true";
	}
	
    public Collection<String> completeEditora(String query) {
        String queryLowerCase = query.toLowerCase();
        listaNomeEditoras = new ArrayList<>();
        
        for (Editora editora : listaEditoras) {
			listaNomeEditoras.add(editora.getNome());
		}

        return listaNomeEditoras.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
    
    public String adicionarChecklist() {
    	Checklist c = new Checklist();
    	c.setEditoraSelecionada(getChecklist().getEditoraSelecionada());
    	c.setPreco(getChecklist().getPreco());
    	c.setTitulo(getChecklist().getTitulo());

		Editora editora = editoraRepository.obterEditoraPorNome(c.getEditoraSelecionada());
		if(editora == null) {
			editora = new Editora(c.getEditoraSelecionada());
			editoraRepository.salvar(editora);
		}
		c.setEditora(editora);
		
		checklistRepository.salvar(c);
		
		listaChecklist.add(c);
		
		logger.info("checklist cadastrado com sucesso");
		
		return iniciarProcesso();
    }
    
    public String removerChecklist(Integer id) {
    	Checklist c = checklistRepository.obterPorId(id);
    	
		checklistRepository.deletar(c);
    	
    	listaChecklist = new ArrayList<>();
    	listaChecklist = checklistRepository.listarChecklistAdmin();
    	
    	logger.error("checklist removido com sucesso");
    	
    	return "listarChecklist?faces-redirect=true";
    }
    
    public boolean habilitarExcluir(Checklist check) {
    	Collection<ChecklistUsuario> listaChecklistUsuario = checklistUsuarioRepository.listarPorChecklist(check.getId());
    	
    	return listaChecklistUsuario == null ? false : true;
    }
    
	public Collection<Editora> getListaEditoras() {
		return listaEditoras;
	}

	public void setListaEditoras(Collection<Editora> listaEditoras) {
		this.listaEditoras = listaEditoras;
	}

	public Collection<String> getListaNomeEditoras() {
		return listaNomeEditoras;
	}

	public void setListaNomeEditoras(Collection<String> listaNomeEditoras) {
		this.listaNomeEditoras = listaNomeEditoras;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
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

}
