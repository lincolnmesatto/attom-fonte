package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Genero;
import repository.GeneroRepository;

@FacesConverter("generoConverter")
@Named(value="generoConverter")
public class GeneroConverter implements Converter {

	@Inject
	private GeneroRepository generoRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Genero g = generoRepository.obterGeneroPorDescricao(value);
		return g;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Genero g = (Genero) value;
		return g.getDescricao();
	}

}