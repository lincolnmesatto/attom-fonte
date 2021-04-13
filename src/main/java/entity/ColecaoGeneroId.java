package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ColecaoGeneroId implements Serializable {

	private static final long serialVersionUID = -1130128252033423806L;

	@ManyToOne
	@JoinColumn(name = "cod_colecao", nullable = false)
	private Colecao colecao;

	@ManyToOne
	@JoinColumn(name = "cod_genero", nullable = false)
	private Genero genero;

	
	public ColecaoGeneroId(Colecao colecao, Genero genero) {
		this.colecao = colecao;
		this.genero = genero;
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
