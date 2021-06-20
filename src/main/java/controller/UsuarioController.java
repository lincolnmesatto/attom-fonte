package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import entity.TipoPerfilEnum;
import entity.Usuario;
import repository.UsuarioRepository;
import util.Uteis;

@Named(value="usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = -6004071469836183158L;
	private static Logger logger = Logger.getLogger(UsuarioController.class);
	

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
		try {
			
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
	 
					logger.info("login realizado com sucesso: "+usuario.getLogin());
					return "sistema/home?faces-redirect=true";
				}else{
					logger.error("login e/ou senha incorretos");
					Uteis.mensagem("Não foi possível efetuar o login com esse usuário e senha!");
					return null;
				}
			}
		}catch (Exception e) {
			logger.error("Erro no login: "+e.getMessage());
		}
		return null;	
 
	}
	public String cadastrarUsuario() {
		Usuario u = usuarioRepository.validarLogin(usuario);
		if(u != null){
			Uteis.mensagemAtencao("Login já cadastrado!");
			logger.error("Login já cadastrado!");
			
			return null;
		}
		
		u = usuarioRepository.validarEmail(usuario);
		if(u != null){
			Uteis.mensagemAtencao("Email já cadastrado!");
			logger.error("Email já cadastrado!");
		}

		u = usuario;
		u.setSenha(criarHash(u));
		u.setPerfil(TipoPerfilEnum.USUARIO);

		usuarioRepository.cadastrarUsuario(u);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().put("idUsuarioAutenticado", u.getId());

		logger.info("Usuário cadastrado com sucesso");
		
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

	public void linkHomeScreen(){
		verificaCadastro = !verificaCadastro;
	}

	public boolean verificarAcesso() {
		Usuario u = usuarioRepository.obterUsuarioPorId(Uteis.getIdUsuarioLogado());
		if(u.getPerfil().equals(TipoPerfilEnum.ADMIN))
			return true;
		
		return false;
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

