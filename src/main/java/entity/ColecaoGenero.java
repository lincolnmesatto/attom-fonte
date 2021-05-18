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

@Table(name = "tb_colecao_genero")
@Entity

@NamedQueries({
	@NamedQuery(name = "ColecaoGenero.listarPorColecao", query = "SELECT cg FROM ColecaoGenero cg WHERE cg.colecao.id = :id")
})
public class ColecaoGenero implements Serializable {
	private static final long serialVersionUID = -3034939477151035891L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_colecao_genero")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "cod_colecao", nullable = false)
	private Colecao colecao;

	@ManyToOne
	@JoinColumn(name = "cod_genero", nullable = false)
	private Genero genero;

	public ColecaoGenero(Integer id, Colecao colecao, Genero genero) {
		super();
		this.id = id;
		this.colecao = colecao;
		this.genero = genero;
	}

	public ColecaoGenero(Colecao colecao, Genero genero) {
		super();
		this.colecao = colecao;
		this.genero = genero;
	}

	public ColecaoGenero() {
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

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

}
