package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Genero;
import util.Uteis;

public class GeneroRepository implements Serializable {

	private static final long serialVersionUID = -8666196408104128266L;
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Genero> listarGeneros(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Genero.listAll");
			
			return (Collection<Genero>)query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public Genero obterGeneroPorDescricao(String descricao){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Genero.obterPorDescricao");
			query.setParameter("descricao", descricao);
			
			return (Genero)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void salvar(Genero genero) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(genero);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
}
