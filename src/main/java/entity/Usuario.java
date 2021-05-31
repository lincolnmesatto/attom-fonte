package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tb_usuario")
@Entity

@NamedQueries({
	@NamedQuery(name = "Usuario.findUser", query= "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha"),
	@NamedQuery(name = "Usuario.findById", query= "SELECT u FROM Usuario u WHERE u.id = :id"),
	@NamedQuery(name = "Usuario.findByLogin", query= "SELECT u FROM Usuario u WHERE u.login = :login"),
	@NamedQuery(name = "Usuario.findByEmail" , query= "SELECT u FROM Usuario u WHERE u.email = :email")
})	

public class Usuario implements Serializable {

	private static final long serialVersionUID = 3804791055346363735L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_usuario")
	private Integer id;
	
	@Column(name = "login", length = 45, nullable = false)
	private String login;
	
	@Column(name = "senha", length = 45, nullable = false)
	private String senha;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "email", length = 45, nullable = false)
	private String email;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "perfil", nullable = true)
	private TipoPerfilEnum perfil;
	
	@Transient
	private String senhaAntiga;
	
	@Transient
	private String senhaModal;
	
	@Transient
	private String senhaModalConfirmar;
	
	@Transient
	private boolean habilitaConfirmar;
	
	@Transient
	private boolean habilitaNovoConfirmar;

	public Usuario() {}
	
	public Usuario(Integer id, String login, String senha, String nome, String email, TipoPerfilEnum perfil) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaModal() {
		return senhaModal;
	}

	public void setSenhaModal(String senhaModal) {
		this.senhaModal = senhaModal;
	}

	public String getSenhaModalConfirmar() {
		return senhaModalConfirmar;
	}

	public void setSenhaModalConfirmar(String senhaModalConfirmar) {
		this.senhaModalConfirmar = senhaModalConfirmar;
	}

	public boolean isHabilitaConfirmar() {
		return habilitaConfirmar;
	}

	public void setHabilitaConfirmar(boolean habilitaConfirmar) {
		this.habilitaConfirmar = habilitaConfirmar;
	}

	public boolean isHabilitaNovoConfirmar() {
		return habilitaNovoConfirmar;
	}

	public void setHabilitaNovoConfirmar(boolean habilitaNovoConfirmar) {
		this.habilitaNovoConfirmar = habilitaNovoConfirmar;
	}

	public TipoPerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(TipoPerfilEnum perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
	
	
}
