package controller;


import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import entity.Usuario;
import repository.UsuarioRepository;
import util.Uteis;

@Named(value = "perfilController")
@SessionScoped
public class PerfilController implements Serializable{

	private static final long serialVersionUID = 8199504929257291609L;

	@Inject
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Usuario usuarioModal;
    
    public Usuario getUsuarioModal() {
		return usuarioModal;
	}

	public void setUsuarioModal(Usuario usuarioModal) {
		this.usuarioModal = usuarioModal;
	}

	public String iniciaProcesso(){
        return "editarPerfil?faces-redirect=true";
    }

    public String salvar(){
        try {
            Usuario u = getUsuario();
            usuarioRepository.updateUsuario(u);
            
            return "home?faces-redirect=true";
	    }catch (Exception e){
            e.getStackTrace();
        }
        
        return null;
    }
    
    public boolean validarSenhaAntiga(String senhaAntiga) {
    	senhaAntiga = criarHash(getUsuario(), senhaAntiga);
    	
    	if(!senhaAntiga.equals(getUsuario().getSenha())) {
    		Uteis.mensagemAtencao("Senha antiga incorreta!");
    		getUsuario().setHabilitaConfirmar(false);
    	}else
    		getUsuario().setHabilitaConfirmar(true);
    	
    	return getUsuario().isHabilitaConfirmar();
    }
    
//    public boolean validarSenhaNova(Usuario usuario) {
//    	if(!usuario.getSenhaModal().equals(usuario.getSenhaModalConfirmar())) {
//    		Uteis.mensagemAtencao("As senhas sao diferentes!");
//    		getUsuario().setHabilitaNovoConfirmar(false);
//    	}else
//    		getUsuario().setHabilitaNovoConfirmar(true);
//    	
//    	return getUsuario().isHabilitaNovoConfirmar();
//    }
    
	public String criarHash(Usuario u, String senha){
		try {
			Integer soma = 0;
			for (char c : u.getLogin().toCharArray()) {
				soma += (int) c;
			}

			String senhaHash = senha;
			for (int i = 0; i < soma; i++) {
				senhaHash = Uteis.md5(senhaHash);
			}

			return senhaHash;
		}catch (Exception e){
			e.getStackTrace();
		}
		return null;
	}
	
	public void alterarSenha(Usuario usuario) {
		String senhaAntiga = criarHash(getUsuario(), getUsuario().getSenhaAntiga());
    	
    	if(!senhaAntiga.equals(getUsuario().getSenha())) {
    		Uteis.mensagemAtencao("Senha antiga incorreta!");
    	}else if(!getUsuario().getSenhaModal().equals(getUsuario().getSenhaModalConfirmar())) {
    		Uteis.mensagemAtencao("As senhas sao diferentes!");
    	}else {
			Usuario u = usuarioRepository.obterUsuarioPorId(Uteis.getIdUsuarioLogado());
			String senhaHash = criarHash(u, usuario.getSenhaModalConfirmar());
			u.setSenha(senhaHash);
			
			usuarioRepository.updateUsuario(u);
			
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('modalAlterarSenha').hide();");
			
			getUsuario().setSenhaAntiga(null);
			getUsuario().setSenhaModal(null);
			getUsuario().setSenhaModalConfirmar(null);
			
			Uteis.mensagemInfo("Senha alterada com sucesso!");
    	}	
	}

    public Usuario getUsuario() {
        return usuarioRepository.obterUsuarioPorId(Uteis.getIdUsuarioLogado());
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}