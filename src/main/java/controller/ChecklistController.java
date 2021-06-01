package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value="checklistController")
@SessionScoped
public class ChecklistController implements Serializable {

	private static final long serialVersionUID = 4893657327728525299L;
	
	public String iniciarProcesso() {
		
		return "manterChecklist?faces-redirect=true";
	}

}
