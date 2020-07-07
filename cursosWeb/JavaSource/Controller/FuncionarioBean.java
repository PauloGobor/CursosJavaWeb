package Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.ChamadoDAO;
import DAO.FuncionarioDAO;
import Model.Chamado;
import Model.Funcionario;

@Named
@SessionScoped
public class FuncionarioBean implements Serializable {
	public FuncionarioBean() {
		funcionario = new Funcionario();
		chamados = new ArrayList<Chamado>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Funcionario funcionario = new Funcionario();
	private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	private Chamado chamado = new Chamado();
	private ChamadoDAO chamadoDAO = new ChamadoDAO();
	List<Chamado> chamados;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	public String salvar(Funcionario funcionario) {
		if (funcionarioDAO.buscarPorCpf(funcionario.getCpf()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Cpf " + funcionario.getCpf() + " já Cadastrado."));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "CadastrarFuncionario.xhtml?faces-redirect=true";
		}
		funcionarioDAO.salvar(funcionario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Cadastro do Funcionario " + funcionario.getNome() + " Realizado."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		funcionario = new Funcionario();
		return "ListarFuncionario.xhtml?faces-redirect=true";
	}

	public List<Funcionario> listarFuncionarios() {
		funcionario = new Funcionario();
		return funcionarioDAO.listar();
	}

	public String excluir(Long cod) {
		funcionarioDAO.excluir(cod);
		return "sucesso";
	}

	public Funcionario buscarFuncionarioPorId(Long cod) {
		funcionario = funcionarioDAO.buscarPorId(cod);
		return funcionario;
	}

	public String alterar(Long cod) {

		return "sucesso";
	}

	public String listarChamadoPorfuncionario() {

		String url = "ListarChamadoPorFuncionario.xhtml?faces-redirect=true";
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idFuncionario"));
		funcionario = buscarFuncionarioPorId(id);
//		String status = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("status");
//		chamados = funcionarioDAO.listarChamadoPorFuncionario(id, status);
		/*
		 * if (status.equals("ABERTO")) {
		 * 
		 * } else { url =
		 * "ListarChamadoConcluidoPorFuncionario.xhtml?faces-redirect=true"; }
		 */
		return url;
	}

	public String iniciarAtendimento() {
		final String status = "TODOS";

		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idChamado"));
		chamado = chamadoDAO.buscarPorId(id);
		chamado.setStatus("PROCESSANDO");
		chamado.setDataAtendimento(new Date());
		chamadoDAO.salvar(chamado);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Chamado N°" + chamado.getId() + " do setor "
						+ chamado.getSetor() + " Mudou para o status: " + chamado.getStatus()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		chamado = new Chamado();

		// chamados =
		// funcionarioDAO.listarChamadoPorFuncionario(chamado.getFuncionario().getId(),
		// status);
		return "ListarChamadoPorFuncionario.xhtml?faces-redirect=true";
	}

	public String encerrarAtendimento() {
		final String status = "TODOS";
		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idChamado"));
		chamado = chamadoDAO.buscarPorId(id);
		chamado.setStatus("CONCLUIDO");
		chamado.setDataConcluido(new Date());
		LocalDateTime start = LocalDateTime.ofInstant(chamado.getDataAtendimento().toInstant(), ZoneId.systemDefault());

		LocalDateTime end = LocalDateTime.ofInstant(chamado.getDataConcluido().toInstant(), ZoneId.systemDefault());
		Duration diferenca = Duration.between(start, end);

		int segundos = (int) (diferenca.toMillis() / 1000) % 60; // se não precisar de segundos, basta remover esta
																	// linha.
		int minutos = (int) (diferenca.toMillis() / 60000) % 60; // 60000 = 60 * 1000
		int horas = (int) diferenca.toMillis() / 3600000;

		LocalTime time = LocalTime.of(horas, minutos, segundos);
		/*
		 * Instant instant =
		 * time.atDate(LocalDate.now()).atZone(ZoneId.of("America/Sao_Paulo")).toInstant
		 * ();
		 * 
		 * Date timeCall = Date.from(instant);
		 */

		chamado.setTempoChamado(time.toString());
		chamadoDAO.salvar(chamado);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Chamado N°" + chamado.getId() + " do setor "
						+ chamado.getSetor() + " Mudou para o status: " + chamado.getStatus()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		chamado = new Chamado();

		// chamados =
		// funcionarioDAO.listarChamadoPorFuncionario(chamado.getFuncionario().getId(),
		// status);
		return "ListarChamadoPorFuncionario.xhtml?faces-redirect=true";
	}

	public String vizulizarChamadosFuncionario() {

		long id = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idFuncionario"));
		funcionario = funcionarioDAO.buscarPorId(id);
		return "ListarChamadoPorFuncionario.xhtml?faces-redirect=true";
	}

	public List<Chamado> listarChamadoPorStatusFuncionarios(Long id, String status) {
		chamados = funcionarioDAO.listarChamadoPorFuncionario(id, status);
		return chamados;
	}

}
