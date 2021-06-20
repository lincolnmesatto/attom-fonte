package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import entity.Colecao;
import entity.ColecaoAutor;
import entity.ColecaoGenero;
import util.Uteis;

public class ColecaoRepository implements Serializable {

	private static final long serialVersionUID = -8080794805801850994L;
	private static Logger logger = Logger.getLogger(ColecaoRepository.class);

	EntityManager entityManager;
	
	public void salvar(Colecao colecao) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecao);
		}catch (Exception e) {
			logger.error("erro ao salvar colecao"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void alterar(Colecao colecao) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.merge(colecao);
		}catch (Exception e) {
			logger.error("erro ao alterar colecao"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void deletar(Colecao colecao) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.remove(colecao);
		}catch (Exception e) {
			logger.error("erro ao deletar colecao"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void deletar(ColecaoAutor colecaoAutor) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.remove(colecaoAutor);
		}catch (Exception e) {
			logger.error("erro ao deletar colecao autor"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void deletar(ColecaoGenero colecaoGenero) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.remove(colecaoGenero);
		}catch (Exception e) {
			logger.error("erro ao deletar colecao genero"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void salvarColecaoAutor(ColecaoAutor colecaoAutor) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecaoAutor);
		}catch (Exception e) {
			logger.error("erro ao salvar colecao autor"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void salvarColecaoGenero(ColecaoGenero colecaoGenero) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(colecaoGenero);
		}catch (Exception e) {
			logger.error("erro ao salvar colecao genero"+ e.getMessage());
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
			logger.error("erro ao obterColecaoPorTituloUsuario"+ e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Colecao> listarPorUsuario(Integer idUsuarioLogado) {
		Query query = Uteis.jpaEntityManager().createNamedQuery("Colecao.listarPorUsuario");
		query.setParameter("id", idUsuarioLogado);
		
		return (Collection<Colecao>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColecaoAutor> listarPorAutoresPorColecao(Colecao colecao){
		Query query = Uteis.jpaEntityManager().createNamedQuery("ColecaoAutor.listarPorColecao");
		query.setParameter("id", colecao.getId());
		
		return (Collection<ColecaoAutor>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColecaoGenero> listarPorGenerosPorColecao(Colecao colecao){
		Query query = Uteis.jpaEntityManager().createNamedQuery("ColecaoGenero.listarPorColecao");
		query.setParameter("id", colecao.getId());
		
		return (Collection<ColecaoGenero>)query.getResultList();
	}
	
	public Colecao obterColecaoPorId(Integer id){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Colecao.obterPorId");
			query.setParameter("id", id);
			
			return (Colecao)query.getSingleResult();
		} catch (Exception e) {
			logger.error("erro ao obterColecaoPorId"+ e.getMessage());
			return null;
		}
	}
}
