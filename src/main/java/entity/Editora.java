package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_editora")
@Entity
public class Editora implements Serializable {

	private static final long serialVersionUID = 3602203509805632630L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_editora")
	private Integer id;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	public Editora(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Editora(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
