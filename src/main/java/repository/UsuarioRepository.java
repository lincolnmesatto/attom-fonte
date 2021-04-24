package repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Editora;
import entity.Usuario;
import util.Uteis;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 3598547438948248246L;
	EntityManager entityManager;

	public Usuario validaUsuario(Usuario usuario){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Usuario.findUser");
 
			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", usuario.getSenha());
 
			return (Usuario)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Usuario obterUsuarioPorId(Integer id){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Usuario.findById");
			query.setParameter("id", id);
			
			return (Usuario)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	public Usuario validarLogin(Usuario usuario){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Usuario.findByLogin");


			query.setParameter("login", usuario.getLogin());

			return (Usuario)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
	public Usuario validarEmail(Usuario usuario){
		try {
			Query query = Uteis.jpaEntityManager().createNamedQuery("Usuario.findByEmail");


			query.setParameter("email", usuario.getEmail());

			return (Usuario)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
	public void cadastrarUsuario(Usuario usuario) {
		try {
			entityManager =  Uteis.jpaEntityManager();

			entityManager.persist(usuario);
		}catch (Exception e) {
			e.getStackTrace();
		}
	}

}
