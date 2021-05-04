package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ColecaoGeneroId implements Serializable {

	private static final long serialVersionUID = -1130128252033423806L;

//	@ManyToOne
//	@JoinColumn(name = "cod_colecao", nullable = false)
	private Colecao colecao;

//	@ManyToOne
//	@JoinColumn(name = "cod_genero", nullable = false)
	private Genero genero;

	public ColecaoGeneroId(){}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colecao == null) ? 0 : colecao.hashCode());
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
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
		ColecaoGeneroId other = (ColecaoGeneroId) obj;
		if (colecao == null) {
			if (other.colecao != null)
				return false;
		} else if (!colecao.equals(other.colecao))
			return false;
		if (genero == null) {
			if (other.genero != null)
				return false;
		} else if (!genero.equals(other.genero))
			return false;
		return true;
	}
}
