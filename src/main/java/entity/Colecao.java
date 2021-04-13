package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tb_colecao")
@Entity

@NamedQueries({
	@NamedQuery(name = "Colecao.obterPorTituloUsuario", query = "SELECT c FROM Colecao c WHERE c.titulo = :titulo AND c.usuario.id = :id")
})

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
	
	@Transient
	private String editoraSelecionada;
	
	@Transient
	private Collection<Autor> autoresSelecionados;

	@Transient
	private Collection<Genero> generosSelecionados;
	
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

	public String getEditoraSelecionada() {
		return editoraSelecionada;
	}

	public void setEditoraSelecionada(String editoraSelecionada) {
		this.editoraSelecionada = editoraSelecionada;
	}

	public Collection<Autor> getAutoresSelecionados() {
		if(this.autoresSelecionados == null)
			autoresSelecionados = new ArrayList<>();
		return autoresSelecionados;
	}

	public void setAutoresSelecionados(Collection<Autor> autoresSelecionados) {
		this.autoresSelecionados = autoresSelecionados;
	}

	public Collection<Genero> getGenerosSelecionados() {
		if(this.generosSelecionados == null)
			generosSelecionados = new ArrayList<>();
		return generosSelecionados;
	}

	public void setGenerosSelecionados(Collection<Genero> generosSelecionados) {
		this.generosSelecionados = generosSelecionados;
	}
}
