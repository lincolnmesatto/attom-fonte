package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "tb_selo")
@Entity
public class Selo implements Serializable {

	private static final long serialVersionUID = -5545294113058381494L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_genero")
	private Integer id;
	
	@Column(name = "descricao", length = 45, nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "cod_editora", nullable = false)
	private Editora editora;

	public Selo(String descricao, Editora editora) {
		this.descricao = descricao;
		this.editora = editora;
	}
	
	public Selo() {}

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

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
}
