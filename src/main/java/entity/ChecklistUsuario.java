package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "tb_checklist_usuario")
@Entity

@NamedQueries({
	@NamedQuery(name = "ChecklistUsuario.listChecklistUsuario", query = "SELECT c FROM ChecklistUsuario c WHERE c.usuario.id = :id"),
	@NamedQuery(name = "ChecklistUsuario.obterChecklistUsuario", query = "SELECT c FROM ChecklistUsuario c WHERE c.usuario.id = :idUsuario and c.checklist.id = :idChecklist"),
	@NamedQuery(name = "ChecklistUsuario.listarPorChecklist", query = "SELECT c FROM ChecklistUsuario c WHERE c.checklist.id = :id")
})


public class ChecklistUsuario implements Serializable {

	private static final long serialVersionUID = -4976542908437908812L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_checklist_usuario")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = true)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "cod_checklist", nullable = true)
	private Checklist checklist;

	public ChecklistUsuario(Usuario usuario, Checklist checklist) {
		this.usuario = usuario;
		this.checklist = checklist;
	}

	public ChecklistUsuario() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}
}
