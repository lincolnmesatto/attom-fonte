package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ColecaoAutorId implements Serializable {

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

	private static final long serialVersionUID = -1130128252033423806L;

	@ManyToOne
	@JoinColumn(name = "cod_colecao", nullable = false)
	private Colecao colecao;

	@ManyToOne
	@JoinColumn(name = "cod_autor", nullable = false)
	private Autor autor;
	
}
