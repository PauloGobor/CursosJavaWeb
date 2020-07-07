package Controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.ChamadoDAO;
import Model.Chamado;

@Named
@SessionScoped
public class ChamadoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7308922828716711708L;
	private Chamado chamado = new Chamado();
	private static ChamadoDAO chamadoDAO = new ChamadoDAO();
	List<Chamado> chamados;

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	public ChamadoBean() {
		chamado = new Chamado();
		chamados = new ArrayList<Chamado>();

	}

	public String salvar(Chamado chamado) {

		chamadoDAO.salvar(chamado);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Chamado N°" + chamado.getId() + " aberto para o setor " + chamado.getSetor()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		chamado = new Chamado();
		return "ListarChamado.xhtml?faces-redirect=true";
	}

	public List<Chamado> listarChamados() {
		chamado = new Chamado();
		return chamadoDAO.listar();
		
	}

	public String excluir(Long cod) {
		chamadoDAO.excluir(cod);
		return "sucesso";
	}

	public Chamado buscarChamadoPorId(Long cod) {
		chamado = chamadoDAO.buscarPorId(cod);
		return chamado;
	}

	public String alterar(Long cod) {
		
		return "sucesso";
	}

	public String alterarChamado() {
		return "AtenderChamado.xhtml?faces-redirect=true";
	}

	public List<Chamado> listarchamadoPorStatus(String status) {
		chamados = chamadoDAO.listarChamadoPorStatus(status);
		return chamados;
	}

}
