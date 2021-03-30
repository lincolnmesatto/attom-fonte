package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "usuario")
@Entity

@NamedQuery(name = "Usuario.findUser", query= "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha")

public class Usuario implements Serializable {

	private static final long serialVersionUID = 3804791055346363735L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_usuario")
	private int id;
	
	@Column(name = "login", length = 45)
	private String login;
	
	@Column(name = "senha", length = 45)
	private String senha;
	
	@Column(name = "nome", length = 100)
	private String nome;
	
	@Column(name = "email", length = 45)
	private String email;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
