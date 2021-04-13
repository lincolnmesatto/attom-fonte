package repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Colecao;
import entity.Editora;
import util.Uteis;

public class EditoraRepository implements Serializable {

	private static final long serialVersionUID = -5250014497810012921L;
	
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Collection<Editora> listarEditoras(){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Editora.listAll");
			
			return (Collection<Editora>)query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public Editora obterEditoraPorNome(String nome){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Editora.obterPorNome");
			query.setParameter("nome", nome);
			
			return (Editora)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void salvar(Editora editora) {
		try {
			entityManager =  Uteis.jpaEntityManager();
			
			entityManager.persist(editora);
		}catch (Exception e) {
			e.getStackTrace();
		}	
	}
}
