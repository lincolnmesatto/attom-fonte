package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Uteis {
	
	public static EntityManager jpaEntityManager(){
		 
		FacesContext facesContext = FacesContext.getCurrentInstance();
 
		ExternalContext externalContext = facesContext.getExternalContext();
 
		HttpServletRequest request  = (HttpServletRequest)externalContext.getRequest();
 
		return (EntityManager)request.getAttribute("entityManager");
	} 
	
	//MOSTRAR MENSAGEM
	public static void mensagem(String mensagem){
 
		FacesContext facesContext = FacesContext.getCurrentInstance();
 
		facesContext.addMessage(null, new FacesMessage("Alerta",mensagem));
	}
 
	//MOSTRAR MENSAGEM
	public static void mensagemAtencao(String mensagem){
 
		FacesContext facesContext = FacesContext.getCurrentInstance();
 
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o:", mensagem));
	}
 
	//MOSTRAR MENSAGEM
	public static void mensagemInfo(String mensagem){
 
		FacesContext facesContext = FacesContext.getCurrentInstance();
 
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	}
	public static String md5(String mensagem) throws Exception{
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(mensagem.getBytes(),0,mensagem.length());

		return new BigInteger(1, m.digest()).toString(16);
	}
	public static Integer getIdUsuarioLogado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();

		return (Integer)facesContext.getExternalContext().getSessionMap().get("idUsuarioAutenticado");
	}
}
