package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.MatriculaDAO;
import DAO.UsuarioDAO;
import Model.Matricula;
import Model.Professor;
import Model.Usuario;
import Utils.Valida;

@Named
@SessionScoped
public class MatriculaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7886765682482270398L;
	Matricula matricula = new Matricula();
	private List<Matricula> matriculas;
	private Professor professor = new Professor();
	private Usuario usuario = new Usuario();
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	private static MatriculaDAO matriculaDAO = new MatriculaDAO();

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public List<Matricula> getMatriculas() {

		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public MatriculaBean() {
		matricula = new Matricula();
		matriculas = new ArrayList<Matricula>();
		professor = new Professor();
		/*
		 * matricula.setUsuario(new Usuario()); matricula.setCurso(new Curso());
		 */
	}

	public String salvar(Matricula matricula) {
		/*
		 * if(matriculaDAO.validaUsuarioMatricula(matricula.getUsuario().getId(),
		 * matricula.getCurso().getId())
		 */
		usuario = usuarioDAO.buscarPorCpf(matricula.getUsuario().getCpf());

		if (usuario != null) {
			// verfica se tem quantidade de vagas maior que 1
			if (matriculaDAO.validaUsuarioMatricula(usuario.getId(), matricula.getCurso().getId()) == null) {

				// valida usuario na matricula se nja está cadastrado
				if (Valida.QuantidadeVagasCurso(matricula.getCurso())) {
					matricula.setUsuario(usuario);
					matricula.setStatus("CURSANDO");
					matriculaDAO.salvar(matricula);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
									"Matricula realizada no Curso " + matricula.getCurso().getNome() + " para o Aluno "
											+ matricula.getUsuario().getNome()));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					matricula = new Matricula();
					return "ListarMatricula.xhtml?faces-redirect=true";
				} else {
					// sem vagas curso
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro", "Não a mais vagas para o Curso " + matricula.getCurso().getNome() + " ."));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "RealizarMatricula.xhtml?faces-redirect=true";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuario " + usuario.getNome()
								+ "  já está matriculado no Curso " + matricula.getCurso().getNome() + " ."));
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return "RealizarMatricula.xhtml?faces-redirect=true";
			}
		} else {
			// usuario null

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Usuario com o CPF " + matricula.getUsuario().getCpf() + " não encontrado."));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			matricula = new Matricula();

			return "RealizarMatricula.xhtml?faces-redirect=true";

		}

	}

	public List<Matricula> listarMatriculas() {
		matricula = new Matricula();
		return matriculaDAO.listar();
	}

	public List<Matricula> listarMatriculasPorCurso(Long cod) {
		return matriculaDAO.listarMatriculasPorCurso(cod);
	}

	public Matricula validaUsuarioMatricula(Long usrcod, Long crscod) {
		return matriculaDAO.validaUsuarioMatricula(usrcod, crscod);
	}

	// mostrar para o aluno os cursos que ele esta matriculado
	public List<Matricula> listarMatriculasPorAluno(Long cod) {
		return matriculaDAO.listarMatriculasPorAluno(cod);
	}

	public List<Matricula> listarMatriculasPorProfessor(Long cod) {
		return matriculaDAO.listarMatriculasPorProfessor(cod);
	}

	public Matricula buscarMatriculaPorId(Long id) {
		matricula = matriculaDAO.buscarPorId(id);
		return matricula;
	}

	public String aprovar() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMatricula"));
		matricula = buscarMatriculaPorId(id);
		matricula.setStatus("APROVADO");
		matriculaDAO.alterar(matricula);
		matriculas = listarMatriculasPorProfessor(matricula.getCurso().getProfessor().getId());
		return "ListarMatriculasPorProfessor.xhtml?faces-redirect=true";

	}

	public String reprovar() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMatricula"));
		matricula = buscarMatriculaPorId(id);
		matricula.setStatus("REPROVADO");
		matriculaDAO.alterar(matricula);
		matriculas = listarMatriculasPorProfessor(matricula.getCurso().getProfessor().getId());
		return "ListarMatriculasPorProfessor.xhtml?faces-redirect=true";
	}

	public String listarAlunos() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAluno"));
		matriculas = listarMatriculasPorAluno(id);
		return "ListarMatriculaPorAluno.xhtml?faces-redirect=true";

	}

	public String listarCursos() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCurso"));
		matriculas = listarMatriculasPorCurso(id);
		return "ListarMatriculaPorCurso.xhtml?faces-redirect=true";

	}

	public String listarMatriculaPorProfessor() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProfessor"));
		matriculas = listarMatriculasPorProfessor(id);
		return "ListarMatriculasPorProfessor.xhtml?faces-redirect=true";

	}

	public String detalhar() {
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMatricula"));
		matricula = buscarMatriculaPorId(id);
		return "DetalheMatricula.xhtml?faces-redirect=true";
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
