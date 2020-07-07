package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.ConteudoCursoDAO;
import DAO.CursoDAO;
import DAO.ProfessorDAO;
import Model.ConteudoCurso;
import Model.Curso;

@Named
@SessionScoped
public class ConteudoCursoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7522907955335490075L;
	ConteudoCurso conteudo = new ConteudoCurso();
	private static ConteudoCursoDAO conteudoDAO = new ConteudoCursoDAO();
	Curso curso = new Curso();
	CursoDAO cursoDAO = new CursoDAO();
	List<ConteudoCurso> conteudos;
	ProfessorDAO professorDAO = new ProfessorDAO();
	List<Curso> cursos;

	public ConteudoCurso getConteudo() {
		return conteudo;
	}

	public void setConteudo(ConteudoCurso conteudo) {
		this.conteudo = conteudo;
	}

	public ConteudoCursoBean() {
		conteudo = new ConteudoCurso();
		curso = new Curso();
		conteudos = new ArrayList<ConteudoCurso>();
		cursos = new ArrayList<Curso>();
	}

	public String salvar(ConteudoCurso conteudo) {
		conteudo.setCurso(curso);
		conteudoDAO.salvar(conteudo);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Novo conteudo adicionado para o curso  " + curso.getNome() + " ."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		cursos = professorDAO.listarCursoPorProfessor(curso.getProfessor().getId());
		conteudo = new ConteudoCurso();
		curso = new Curso();
		return "ListarCursoPorProfessor.xhtml?faces-redirect=true";
	}

	public String adicionarConteudo() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCurso"));
		curso = cursoDAO.buscarPorId(id);
		conteudo = new ConteudoCurso();
		return "AdicionarConteudo.xhtml?faces-redirect=true";
	}

	public String listarConteudoPorCurso() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCurso"));
		curso = cursoDAO.buscarPorId(id);
		conteudos = conteudoDAO.listarConteudoPorCurso(id);
		return "ListarConteudoPorCurso.xhtml?faces-redirect=true";
	}

	public List<ConteudoCurso> listarConteudoPorCurso2(long id) {

		conteudos = conteudoDAO.listarConteudoPorCurso(id);
		return conteudos;
	}

	public List<ConteudoCurso> getConteudos() {
		return conteudos;
	}

	public void setConteudos(List<ConteudoCurso> conteudos) {
		this.conteudos = conteudos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

}
