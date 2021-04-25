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

	private boolean verificaCadastro = false;
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
 
		return "/index.xhtml?faces-redirect=true";
	}
	
	public String efetuarLogin(){
		if(StringUtils.isEmpty(usuario.getLogin()) || StringUtils.isBlank(usuario.getLogin())){
 
			Uteis.mensagem("Favor informar o login!");
			return null;
		}else if(StringUtils.isEmpty(usuario.getSenha()) || StringUtils.isBlank(usuario.getSenha())){
 
			Uteis.mensagem("Favor informara senha!");
			return null;
		}else{
			usuario.setSenha(criarHash(usuario));
			usuario = usuarioRepository.validaUsuario(usuario);
			if(usuario!= null){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("idUsuarioAutenticado", usuario.getId());
 
				return "sistema/home?faces-redirect=true";
			}else{
				Uteis.mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}
 
	}
	public String cadastrarUsuario() {
		Usuario u = usuarioRepository.validarLogin(usuario);
		if(u != null){
			Uteis.mensagemAtencao("Login já cadastrado!");
			return null;
		}
		
		u = usuarioRepository.validarEmail(usuario);
		if(u != null){
			Uteis.mensagemAtencao("Email já cadastrado!");
		}

		u = usuario;
		u.setSenha(criarHash(u));

		usuarioRepository.cadastrarUsuario(u);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().put("idUsuarioAutenticado", u.getId());

		return "sistema/home?faces-redirect=true";
	}
	
	public String criarHash(Usuario u){
		try {
			Integer soma = 0;
			for (char c : u.getLogin().toCharArray()) {
				soma += (int) c;
			}

			String senhaHash = u.getSenha();
			for (int i = 0; i < soma; i++) {
				senhaHash = Uteis.md5(senhaHash);
			}

			return senhaHash;
		}catch (Exception e){
			e.getStackTrace();
		}
		return null;
	}

	
//	public Usuario getUsuarioSession(){
//		FacesContext facesContext = FacesContext.getCurrentInstance();
// 
//		return (Usuario)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
//	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isVerificaCadastro() {
		return verificaCadastro;
	}

	public void setVerificaCadastro(boolean verificaCadastro) {
		this.verificaCadastro = verificaCadastro;
	}
}

