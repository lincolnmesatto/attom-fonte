package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Checklist;
import util.Uteis;

public class ChecklistRepository implements Serializable {

	private static final long serialVersionUID = -1494989440332980715L;
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Checklist> listarChecklistAdmin(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Checklist.listChecklistAdmin");
			
			return (Collection<Checklist>)query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Checklist obterPorId(Integer id){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Checklist.obterPorId");
			query.setParameter("id", id);
			
			return (Checklist)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void salvar(Checklist c) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(c);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
	
	public void deletar(Checklist c) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.remove(c);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
}
