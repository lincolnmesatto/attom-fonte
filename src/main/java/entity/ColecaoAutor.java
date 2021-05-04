package entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "tb_colecao_autor")
@Entity

@NamedQueries({
	@NamedQuery(name = "ColecaoAutor.listarPorColecao", query = "SELECT ca FROM ColecaoAutor ca WHERE ca.id.colecao.id = :id")
})
public class ColecaoAutor implements Serializable {

	private static final long serialVersionUID = -5932492280191923813L;

	@EmbeddedId
	private ColecaoAutorId id;
	
	public ColecaoAutor() {}

	public ColecaoAutor(ColecaoAutorId id) {
		this.id = id;
	}

	public ColecaoAutorId getId() {
		return id;
	}

	public void setId(ColecaoAutorId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ColecaoAutor other = (ColecaoAutor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
