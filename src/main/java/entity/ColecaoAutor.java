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
}
