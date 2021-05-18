package controller;


import entity.Colecao;
import entity.Usuario;
import repository.UsuarioRepository;
import util.Uteis;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "perfilController")
@SessionScoped

public class PerfilController implements Serializable{
    private static final long serialVersionUID = -8873744053437205877L;

    @Inject
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    public String iniciaProcesso(){
        usuario = usuarioRepository.obterUsuarioPorId(Uteis.getIdUsuarioLogado());

        return "editarPerfil?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Boolean salvar(){

        try {
            Usuario u = getUsuario();
            usuarioRepository.updateUsuario(u);

          return true;
    }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
}

