package repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Colecao;
import entity.ColecaoAutor;
import entity.ColecaoGenero;
import util.Uteis;

public class ColecaoRepository implements Serializable {

	private static final long serialVersionUID = -8080794805801850994L;

	EntityManager entityManager;
	
	public void salvar(Colecao colecao) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecao);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
	
	public void salvarColecaoAutor(ColecaoAutor colecaoAutor) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecaoAutor);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
	
	public void salvarColecaoGenero(ColecaoGenero colecaoGenero) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecaoGenero);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
	
	public Colecao obterColecaoPorTituloUsuario(String titulo, Integer idUsuario){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Colecao.obterPorTituloUsuario");
			query.setParameter("titulo", titulo);
			query.setParameter("id", idUsuario);
			
			return (Colecao)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
