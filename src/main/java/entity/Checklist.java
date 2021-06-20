package entity;

import java.io.Serializable;

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

@Table(name = "tb_checklist")
@Entity

@NamedQueries({
	@NamedQuery(name = "Checklist.listChecklistAdmin", query = "SELECT c FROM Checklist c WHERE c.usuario is null"),
	@NamedQuery(name = "Checklist.obterPorId", query = "SELECT c FROM Checklist c WHERE c.id = :id")
})


public class Checklist implements Serializable {

	private static final long serialVersionUID = -1277423301366038334L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_checklist")
	private Integer id;
	
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;
	
	@Column(name = "preco", length = 100, nullable = false)
	private Float preco;
	
	@ManyToOne
	@JoinColumn(name = "cod_editora", nullable = false)
	private Editora editora;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = true)
	private Usuario usuario;
	
	@Transient
	private String editoraSelecionada;
	
	@Transient
	private String precoFormatado;
	
	@Transient
	private boolean selecionado;

	public Checklist(Integer id, String titulo, Float preco, Editora editora, Usuario usuario) {
		this.id = id;
		this.titulo = titulo;
		this.preco = preco;
		this.editora = editora;
		this.usuario = usuario;
	}

	public Checklist(String titulo, Float preco, Editora editora) {
		super();
		this.titulo = titulo;
		this.preco = preco;
		this.editora = editora;
	}

	public Checklist() {}

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

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
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

	public String getEditoraSelecionada() {
		return editoraSelecionada;
	}

	public void setEditoraSelecionada(String editoraSelecionada) {
		this.editoraSelecionada = editoraSelecionada;
	}

	public String getPrecoFormatado() {
		return precoFormatado;
	}

	public void setPrecoFormatado(String precoFormatado) {
		this.precoFormatado = precoFormatado;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
}
