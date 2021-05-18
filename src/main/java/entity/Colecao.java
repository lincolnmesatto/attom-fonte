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
	@NamedQuery(name = "Colecao.obterPorTituloUsuario", query = "SELECT c FROM Colecao c WHERE c.titulo = :titulo AND c.usuario.id = :id"),
	@NamedQuery(name = "Colecao.listarPorUsuario", query = "SELECT c FROM Colecao c WHERE c.usuario.id = :id"),
	@NamedQuery(name = "Colecao.obterPorId", query = "SELECT c FROM Colecao c WHERE c.id = :id")
})

public class Colecao implements Serializable {

	private static final long serialVersionUID = 5719715873793078938L;

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
	
	@Column(name = "isVolumeUnico")
	private boolean volumeUnico;
	
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
	
	@Transient
	private String seloSelecionado;
	
	@Transient
	private String autorModal;

	@Transient
	private String generoModal; 
	
	@Transient
	private String autoresFormatado;
	
	@Transient
	private Collection<ColecaoAutor> listaColecaoAutor;
	
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

	public String getSeloSelecionado() {
		return seloSelecionado;
	}

	public void setSeloSelecionado(String seloSelecionado) {
		this.seloSelecionado = seloSelecionado;
	}

	public String getAutorModal() {
		return autorModal;
	}

	public void setAutorModal(String autorModal) {
		this.autorModal = autorModal;
	}

	public String getGeneroModal() {
		return generoModal;
	}

	public void setGeneroModal(String generoModal) {
		this.generoModal = generoModal;
	}

	public boolean isVolumeUnico() {
		return volumeUnico;
	}

	public void setVolumeUnico(boolean volumeUnico) {
		this.volumeUnico = volumeUnico;
	}

	public String getAutoresFormatado() {
		int cont = 0;
		for (ColecaoAutor ca : this.listaColecaoAutor) {
			if(cont == 0)
				autoresFormatado = ca.getAutor().getNome();
			else
				autoresFormatado += ";"+ca.getAutor().getNome();
			cont++;
		}
		return autoresFormatado;
	}

	public void setAutoresFormatado(String autoresFormatado) {
		this.autoresFormatado = autoresFormatado;
	}

	public Collection<ColecaoAutor> getListaColecaoAutor() {
		return listaColecaoAutor;
	}

	public void setListaColecaoAutor(Collection<ColecaoAutor> listaColecaoAutor) {
		this.listaColecaoAutor = listaColecaoAutor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completo ? 1231 : 1237);
		result = prime * result + ((editora == null) ? 0 : editora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((selo == null) ? 0 : selo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((ultimoComprado == null) ? 0 : ultimoComprado.hashCode());
		result = prime * result + ((ultimoLido == null) ? 0 : ultimoLido.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + (volumeUnico ? 1231 : 1237);
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
		Colecao other = (Colecao) obj;
		if (completo != other.completo)
			return false;
		if (editora == null) {
			if (other.editora != null)
				return false;
		} else if (!editora.equals(other.editora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (selo == null) {
			if (other.selo != null)
				return false;
		} else if (!selo.equals(other.selo))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (ultimoComprado == null) {
			if (other.ultimoComprado != null)
				return false;
		} else if (!ultimoComprado.equals(other.ultimoComprado))
			return false;
		if (ultimoLido == null) {
			if (other.ultimoLido != null)
				return false;
		} else if (!ultimoLido.equals(other.ultimoLido))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (volumeUnico != other.volumeUnico)
			return false;
		return true;
	}
}
