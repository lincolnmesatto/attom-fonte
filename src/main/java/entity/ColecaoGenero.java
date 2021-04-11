package entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_colecao_genero")
@Entity
public class ColecaoGenero implements Serializable {
	private static final long serialVersionUID = -3034939477151035891L;

	@EmbeddedId
	private ColecaoGeneroId id;

	public ColecaoGeneroId getId() {
		return id;
	}

	public void setId(ColecaoGeneroId id) {
		this.id = id;
	}
}
