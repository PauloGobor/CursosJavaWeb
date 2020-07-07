package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.Usuario;

public class UsuarioDAO extends GenericDAO implements IDAO<Usuario> {

	Usuario usuario = new Usuario();

	@Override
	public void salvar(Usuario usuario) {
		EntityManager em = getEntityManager();
		try {
			if (usuario.getId() == null) {
				em.getTransaction().begin();
				em.persist(usuario);
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			e.getStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();

		}
	}

	@Override
	public List<Usuario> listar() {
		EntityManager em = getEntityManager();
		Query q;
		q = em.createQuery("SELECT object(usuario) FROM Usuario as usuario");
		List<Usuario> usuarios = q.getResultList();
		return usuarios;
	}

	@Override
	public void alterar(Usuario usuario) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
	}

	@Override
	public void excluir(Long cod) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, cod);
			em.remove(em.merge(usuario));
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("erro");
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Override
	public Usuario buscarPorId(Long cod) {
		if (cod != null) {
			usuario = getEntityManager().find(Usuario.class, cod);
		}
		return usuario;
	}

	@SuppressWarnings("finally")
	@Override
	public Usuario buscarPorCpf(String cpf) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT object(usuario)" + " FROM Usuario as usuario" + " WHERE cpf=:cpf ");
			q.setParameter("cpf", cpf);

			usuario = (Usuario) q.getSingleResult();
		} catch (Exception nre) {
			usuario = null;

		} finally {
			em.close();
			return usuario;
		}
	}

	@Override
	public Usuario buscarPorNome(String nome) {
		throw new UnsupportedOperationException();
	}

}
