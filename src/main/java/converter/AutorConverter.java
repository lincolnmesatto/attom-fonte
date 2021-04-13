package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Autor;
import repository.AutorRepository;

@FacesConverter("autorConverter")
@Named(value="autorConverter")
public class AutorConverter implements Converter {

	@Inject
	private AutorRepository autorRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Autor a = autorRepository.obterAutorPorNome(value);
		return a;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Autor a = (Autor) value;
		return a.getNome();
	}

}