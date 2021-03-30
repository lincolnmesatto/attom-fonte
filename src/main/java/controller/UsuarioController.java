package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import entity.Usuario;
import repository.UsuarioRepository;
import util.Uteis;

@Named(value="usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = -6004071469836183158L;

	@Inject
	private UsuarioRepository usuarioRepository;
 
	@Inject
	private Usuario usuario;
	
	public String logout(){
 
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
 
		return "/index.xhtml?faces-redirect=true";
	}
	
	public String efetuarLogin(){
 
		if(StringUtils.isEmpty(usuario.getLogin()) || StringUtils.isBlank(usuario.getLogin())){
 
			Uteis.Mensagem("Favor informar o login!");
			return null;
		}
		else if(StringUtils.isEmpty(usuario.getSenha()) || StringUtils.isBlank(usuario.getSenha())){
 
			Uteis.Mensagem("Favor informara senha!");
			return null;
		}
		else{	
 
			usuario = usuarioRepository.validaUsuario(usuario);
 
			if(usuario!= null){
 
				usuario.setSenha(null);
 
				FacesContext facesContext = FacesContext.getCurrentInstance();
 
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuario);
 
 
				return "sistema/home?faces-redirect=true";
			}
			else{
 
				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}
 
	}
	
	public Usuario getUsuarioSession(){
		 
		FacesContext facesContext = FacesContext.getCurrentInstance();
 
		return (Usuario)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
