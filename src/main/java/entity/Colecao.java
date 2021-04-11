package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "tb_colecao")
@Entity
public class Colecao implements Serializable {

	private static final long serialVersionUID = 5965675303661427310L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_colecao")
	private Integer id;
	
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;

	@Column(name = "isCompleto")
	private boolean completo;

	@Column(name = "ultimo_lido")
	private Integer ultimoLido;

	@Column(name = "ultimo_comprado")
	private Integer ultimoComprado;
	
	@ManyToOne
	@JoinColumn(name = "cod_editora", nullable = false)
	private Editora editora;

	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "cod_selo", nullable = true)
	private Selo selo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isCompleto() {
		return completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	public Integer getUltimoLido() {
		return ultimoLido;
	}

	public void setUltimoLido(Integer ultimoLido) {
		this.ultimoLido = ultimoLido;
	}

	public Integer getUltimoComprado() {
		return ultimoComprado;
	}

	public void setUltimoComprado(Integer ultimoComprado) {
		this.ultimoComprado = ultimoComprado;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Selo getSelo() {
		return selo;
	}

	public void setSelo(Selo selo) {
		this.selo = selo;
	}
}
