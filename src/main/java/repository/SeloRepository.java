package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Selo;
import util.Uteis;

public class SeloRepository implements Serializable {

	private static final long serialVersionUID = 7342733279646109911L;
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Selo> listarSelos(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Selo.listAll");
			
			return (Collection<Selo>)query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public Selo obterSeloPorNome(String nome){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Selo.obterPorNome");
			query.setParameter("nome", nome);
			
			return (Selo)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void salvar(Selo selo) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(selo);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
}
