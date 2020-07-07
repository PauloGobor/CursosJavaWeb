package Utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import Controller.ProfessorBean;
import Model.Professor;

@Named
@RequestScoped
@FacesConverter(forClass = Professor.class, value = "ConverterProf")
public class ConverterProf implements Converter {

	private ProfessorBean p = new ProfessorBean();

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		Professor retorno = null;

		if (string != null || !string.isEmpty()) {
			try {
				retorno = p.BuscarProfessorPorId(Long.valueOf(string));
				return retorno;
			} catch (Exception e) {
				return retorno;
			}
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Professor) {
			Professor produto = (Professor) arg2;
			return String.valueOf(produto.getId());
		}
		return null;

	}

}