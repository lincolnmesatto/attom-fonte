package repository;

import java.io.Serializable;

import javax.persistence.Query;

import entity.Usuario;
import util.Uteis;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 3598547438948248246L;

	public Usuario validaUsuario(Usuario usuario){
		 
		try {
 
			Query query = Uteis.JpaEntityManager().createNamedQuery("Usuario.findUser");
 
			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", usuario.getSenha());
 
			//RETORNA O USU�RIO SE FOR LOCALIZADO
			return (Usuario)query.getSingleResult();
 
		} catch (Exception e) {
 
			return null;
		}
 
 
 
	}
}