package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import entity.Editora;
import util.Uteis;

public class EditoraRepository implements Serializable {

	private static final long serialVersionUID = -5250014497810012921L;
	private static Logger logger = Logger.getLogger(EditoraRepository.class);
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Editora> listarEditoras(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Editora.listAll");
			
			return (Collection<Editora>)query.getResultList();
		} catch (Exception e) {
			logger.error("erro ao listarEditoras"+ e.getMessage());
			return null;
		}
	}

	public Editora obterEditoraPorNome(String nome){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Editora.obterPorNome");
			query.setParameter("nome", nome);
			
			return (Editora)query.getSingleResult();
		} catch (Exception e) {
			logger.error("erro ao obterEditoraPorNome"+ e.getMessage());
			return null;
		}
	}
	
	public void salvar(Editora editora) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(editora);
		}catch (Exception e) {
			logger.error("erro ao salvar editora"+ e.getMessage());
			e.getStackTrace();
		}	
	}
}
