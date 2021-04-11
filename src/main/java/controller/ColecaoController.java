package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Autor;
import entity.Colecao;
import entity.ColecaoAutor;
import entity.ColecaoAutorId;
import entity.ColecaoGenero;
import entity.ColecaoGeneroId;
import entity.Editora;
import entity.Genero;
import entity.Usuario;
import repository.ColecaoRepository;

@Named(value="colecaoController")
@SessionScoped
public class ColecaoController implements Serializable {

	private static final long serialVersionUID = 4389998621549207729L;
	
	@Inject
	private ColecaoRepository colecaoRepository;
	
	private Colecao colecaoRetorno;
	
	public String iniciarProcesso() {
		System.out.println("chegou");
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return "manterColecao?faces-redirect=true";
	}
	
	public void salvar() {
		try {
			Colecao colecao = new Colecao();
			
			colecao.setCompleto(false);
			colecao.setTitulo("One piece");
			colecao.setUltimoComprado(97);
			colecao.setUltimoLido(97);
			
			colecao.setUsuario(new Usuario(1, "teste", "teste", "Vou ser Deletado", "teste@email.com"));
			colecao.setEditora(new Editora(1, "Panini"));
			
			colecaoRepository.salvar(colecao);
			colecaoRetorno = colecao;

			salvarPt2();
			
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}

	public void salvarPt2() {
		
		ColecaoAutorId colecaoAutorId = new ColecaoAutorId();
		colecaoAutorId.setAutor(new Autor(1, "Eiichiro Oda"));
		colecaoAutorId.setColecao(colecaoRetorno);
		
		ColecaoAutor colecaoAutor = new ColecaoAutor();
		colecaoAutor.setId(colecaoAutorId);
		
		colecaoRepository.salvarColecaoAutor(colecaoAutor);

		ColecaoGeneroId colecaoGeneroId = new ColecaoGeneroId();
		colecaoGeneroId.setGenero(new Genero(1, "Aventura"));
		colecaoGeneroId.setColecao(colecaoRetorno);
		
		ColecaoGenero colecaoGenero = new ColecaoGenero();
		colecaoGenero.setId(colecaoGeneroId);
		
		colecaoRepository.salvarColecaoGenero(colecaoGenero);
		
	}

	public Colecao getColecaoRetorno() {
		return colecaoRetorno;
	}

	public void setColecaoRetorno(Colecao colecaoRetorno) {
		this.colecaoRetorno = colecaoRetorno;
	}
}
