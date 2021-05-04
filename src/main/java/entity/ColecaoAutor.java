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

@Table(name = "tb_colecao_autor")
@Entity

@NamedQueries({
	@NamedQuery(name = "ColecaoAutor.listarPorColecao", query = "SELECT ca FROM ColecaoAutor ca WHERE ca.colecao.id = :id")
})
public class ColecaoAutor implements Serializable {

	private static final long serialVersionUID = -5932492280191923813L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_colecao_autor")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cod_colecao", nullable = false)
	private Colecao colecao;

	@ManyToOne
	@JoinColumn(name = "cod_autor", nullable = false)
	private Autor autor;
	
	public ColecaoAutor() {}
	
	public ColecaoAutor(Colecao colecao, Autor autor) {
		super();
		this.colecao = colecao;
		this.autor = autor;
	}

	public ColecaoAutor(Integer id, Colecao colecao, Autor autor) {
		super();
		this.id = id;
		this.colecao = colecao;
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Colecao getColecao() {
		return colecao;
	}

	public void setColecao(Colecao colecao) {
		this.colecao = colecao;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
}
