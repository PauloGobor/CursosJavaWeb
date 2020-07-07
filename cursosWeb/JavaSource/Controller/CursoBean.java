package Controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.CursoDAO;
import Model.Curso;

@Named
@SessionScoped
public class CursoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4641423032009099889L;
	private Curso curso = new Curso();
	private static CursoDAO cursoDAO = new CursoDAO();

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public CursoBean() {
		curso = new Curso();
	}

	public String salvar(Curso curso) {
		if (cursoDAO.buscarPorNome(curso.getNome()) != null) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Curso " + curso.getNome() + " já Cadastrado."));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "CadastrarCurso.xhtml?faces-redirect=true";
		}
		cursoDAO.salvar(curso);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Cadastro do Curso " + curso.getNome() + " Realizado."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		curso = new Curso();
		return "ListarCurso.xhtml?faces-redirect=true";

	}

	public String alterar(Curso curso) {
		
		cursoDAO.alterar(curso);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Alteração do Curso " + curso.getNome() + " Realizada."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		curso = new Curso();
		return "ListarCurso.xhtml?faces-redirect=true";
	}

	public List<Curso> listarCurso() {
		curso = new Curso();
		return cursoDAO.listar();
	}

	public String excluir(Long cod) {
		curso = cursoDAO.buscarPorId(cod);
		cursoDAO.excluir(cod);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Curso " + curso.getNome() + " Removido"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		curso = new Curso();
		return "ListarCurso.xhtml?faces-redirect=true";
	}

	public Curso buscarCursoPorId(Long cod) {
		curso = cursoDAO.buscarPorId(cod);
		return curso;
	}

	public Curso BuscarCursoPorNome(String nome) {
		return cursoDAO.buscarPorNome(nome);
	}

	public String detalhar() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCurso"));
		curso = buscarCursoPorId(id);
		return "AlterarCurso.xhtml";
	}

	public String renderizar() {
		/*
		 * FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		 */
		return "CadastrarCurso.jsf";
	}

}
