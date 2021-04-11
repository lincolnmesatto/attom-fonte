package entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_colecao_autor")
@Entity
public class ColecaoAutor implements Serializable {

	private static final long serialVersionUID = -5932492280191923813L;

	@EmbeddedId
	private ColecaoAutorId id;

	public ColecaoAutorId getId() {
		return id;
	}

	public void setId(ColecaoAutorId id) {
		this.id = id;
	}
}
