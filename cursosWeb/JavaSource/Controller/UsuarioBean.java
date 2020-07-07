
package Controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import DAO.UsuarioDAO;
import Model.Usuario;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588887649521432716L;
	private Usuario usuario = new Usuario();
	private static UsuarioDAO usuarioDAO = new UsuarioDAO();

	public UsuarioBean() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String salvar(Usuario usuario) {
		if (usuarioDAO.buscarPorCpf(usuario.getCpf()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Cpf " + usuario.getCpf() + " já Cadastrado."));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "CadastrarUsuario.xhtml?faces-redirect=true";
		}
		usuarioDAO.salvar(usuario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Cadastro do Usuario " + usuario.getNome() + " Realizado."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		usuario = new Usuario();
		return "ListarUsuario.xhtml?faces-redirect=true";
	}

	public String alterar(Usuario usuario) {
		usuarioDAO.alterar(usuario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
				"Alteração do Usuario " + usuario.getNome() + " Realizada."));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		usuario = new Usuario();
		return "ListarUsuario.xhtml?faces-redirect=true";
	}

	public String detalhar() {
		long idUsuario = Long.parseLong(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUsuario"));
		usuario = BuscarUsarioPorId(idUsuario);
		return "AlterarUsuario.xhtml?faces-redirect=true";
	}

	public String renderizar() {
		/*
		 * FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		 */
		return "CadastrarUsuario.jsf";
	}

	public List<Usuario> listarUsuario() {
		usuario = new Usuario();
		return usuarioDAO.listar();
	}

	public String excluir(Long cod) {

		usuario = usuarioDAO.buscarPorId(cod);
		usuarioDAO.excluir(cod);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Curso " + usuario.getNome() + " Removido"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		usuario = new Usuario();
		return "ListarUsuario.xhtml?faces-redirect=true";
	}

	public Usuario BuscarUsarioPorId(Long cod) {
		usuario = usuarioDAO.buscarPorId(cod);
		return usuario;
	}

	public Usuario BuscarUsarioPorCpf(String cpf) {
		return usuarioDAO.buscarPorCpf(cpf);
	}

}
