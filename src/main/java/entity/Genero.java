package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "tb_genero")
@Entity

@NamedQueries({
	@NamedQuery(name = "Genero.listAll", query = "SELECT g FROM Genero g "),
	@NamedQuery(name = "Genero.obterPorDescricao", query = "SELECT g FROM Genero g WHERE g.descricao = :descricao")
})

public class Genero implements Serializable {

	private static final long serialVersionUID = 6751241645969533104L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_genero")
	private Integer id;
	
	@Column(name = "descricao", length = 45, nullable = false)
	private String descricao;

	public Genero(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Genero() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
