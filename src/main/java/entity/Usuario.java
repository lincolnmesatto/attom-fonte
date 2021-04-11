package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "tb_usuario")
@Entity

@NamedQuery(name = "Usuario.findUser", query= "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha")

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

	public Usuario() {}
	
	public Usuario(Integer id, String login, String senha, String nome, String email) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
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
}
