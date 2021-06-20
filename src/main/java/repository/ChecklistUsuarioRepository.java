package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import entity.Checklist;
import entity.ChecklistUsuario;
import entity.Usuario;
import util.Uteis;

public class ChecklistUsuarioRepository implements Serializable {

	private static final long serialVersionUID = -1825187133514956594L;

	private static Logger logger = Logger.getLogger(ChecklistUsuarioRepository.class);
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<ChecklistUsuario> listarChecklistusuario(Integer id){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("ChecklistUsuario.listChecklistUsuario");
			query.setParameter("id", id);
			
			return (Collection<ChecklistUsuario>)query.getResultList();
		} catch (Exception e) {
			logger.error("erro ao listarChecklistusuario"+ e.getMessage());
			return null;
		}
	}
	
	public ChecklistUsuario obterChecklistUsuarioChecklist(Usuario u, Checklist c){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("ChecklistUsuario.obterChecklistUsuario");
			query.setParameter("idUsuario", u.getId());
			query.setParameter("idChecklist", c.getId());
			
			return (ChecklistUsuario)query.getSingleResult();
		} catch (Exception e) {
			logger.error("erro ao obterChecklistUsuarioChecklist"+ e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ChecklistUsuario> listarPorChecklist(Integer id){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("ChecklistUsuario.listarPorChecklist");
			query.setParameter("id", id);
			
			return (Collection<ChecklistUsuario>)query.getResultList();
		} catch (Exception e) {
			logger.error("erro ao listarPorChecklist"+ e.getMessage());
			return null;
		}
	}
	
	public void salvar(ChecklistUsuario c) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(c);
		}catch (Exception e) {
			logger.error("erro ao salvar ChecklistUsuario"+ e.getMessage());
			e.getStackTrace();
		}	
	}
	
	public void deletar(ChecklistUsuario c) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.remove(c);
		}catch (Exception e) {
			logger.error("erro ao deletar ChecklistUsuario"+ e.getMessage());
			e.getStackTrace();
		}	
	}
}
