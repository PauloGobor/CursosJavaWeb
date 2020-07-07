package Utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import Controller.FuncionarioBean;
import Model.Funcionario;


@Named
@RequestScoped
@FacesConverter(forClass = Funcionario.class, value = "ConverterFuncionario")
public class ConverterFuncionario implements  Converter{
	private FuncionarioBean f = new FuncionarioBean();

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		Funcionario retorno = null;

		if (string != null || !string.isEmpty()) {
			try {
				retorno = f.buscarFuncionarioPorId(Long.valueOf(string));
				return retorno;
			} catch (Exception e) {
				return retorno;
			}
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Funcionario) {
			Funcionario funcionario = (Funcionario) arg2;
			return String.valueOf(funcionario.getId());
		}
		return null;

	}

}
