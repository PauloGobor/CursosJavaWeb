package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.ProfessorDAO;
import Model.Curso;
import Model.Professor;

@Named
@SessionScoped
public class ProfessorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8619259897104885789L;
	private Professor professor = new Professor();
	private static ProfessorDAO professorDAO = new ProfessorDAO();
	private List<Curso> cursos;

	public ProfessorBean() {
		professor = new Professor();
		cursos = new ArrayList<Curso>();
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public String salvar(Professor professor) {
		if (professorDAO.buscarPorCpf(professor.getCpf()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Cpf " + professor.getCpf() + " já Cadastrado."));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "CadastrarProfessor.xhtml?faces-redirect=true";
		}
		professorDAO.salvar(professor);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Cadastro do Professor " + professor.getNome() + " Realizado."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		professor = new Professor();
		return "ListarProfessor.xhtml?faces-redirect=true";
	}

	public List<Professor> listarProfessor() {
		return professorDAO.listar();
	}

	public String listarCursoPorProfessor() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProfessor"));
		cursos = professorDAO.listarCursoPorProfessor(id);

		return "ListarCursoPorProfessor.xhtml?faces-redirect=true";
	}

	public Professor BuscarProfessorPorId(Long cod) {
		professor = professorDAO.buscarPorId(cod);
		return professor;
	}

	public Professor BuscarProfessorPorCpf(String cpf) {
		return professorDAO.buscarPorCpf(cpf);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
