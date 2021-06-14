package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import entity.Autor;
import util.Uteis;

public class AutorRepository implements Serializable {

	private static final long serialVersionUID = -8666196408104128266L;
	private static Logger logger = Logger.getLogger(AutorRepository.class);
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Autor> listarAutores(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Autor.listAll");
			
			return (Collection<Autor>)query.getResultList();
		} catch (Exception e) {
			logger.error("erro ao listarAutores"+ e.getMessage());
			return null;
		}
	}

	public Autor obterAutorPorNome(String nome){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Autor.obterPorNome");
			query.setParameter("nome", nome);
			
			return (Autor)query.getSingleResult();
		} catch (Exception e) {
			logger.error("erro ao obter autorPorNome"+ e.getMessage());
			return null;
		}
	}
	
	public void salvar(Autor autor) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(autor);
		}catch (Exception e) {
			logger.error("erro ao salvar autor"+ e.getMessage());
			e.getStackTrace();
		}	
	}
}
