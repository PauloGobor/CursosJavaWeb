package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.Chamado;
import Model.Funcionario;

public class FuncionarioDAO extends GenericDAO implements IDAO<Funcionario> {
	Funcionario funcionario = new Funcionario();

	@Override
	public void salvar(Funcionario funcionario) {
		EntityManager em = getEntityManager();
		try {
			if (funcionario.getId() == null) {
				em.getTransaction().begin();
				em.persist(funcionario);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				em.merge(funcionario);
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
	public List<Funcionario> listar() {
		EntityManager em = getEntityManager();
		Query q;
		q = em.createQuery("SELECT object(funcionario) FROM Funcionario as funcionario");
		List<Funcionario> funcionarios = q.getResultList();
		return funcionarios;
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		if (id != null) {
			funcionario = getEntityManager().find(Funcionario.class, id);
		}
		return funcionario;
	}

	@Override
	public Funcionario buscarPorNome(String nome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void alterar(Funcionario funcionario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Long id) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			Funcionario funcionario = em.find(Funcionario.class, id);
			em.remove(funcionario);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("erro");
			e.printStackTrace();
			em.getTransaction().rollback();
		}

	}

	@SuppressWarnings("finally")
	@Override
	public Funcionario buscarPorCpf(String cpf) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery(
					"SELECT object(funcionario)" + " FROM Funcionario as funcionario" + " WHERE cpf=:cpf ");
			q.setParameter("cpf", cpf);

			funcionario = (Funcionario) q.getSingleResult();
		} catch (Exception nre) {
			funcionario = null;

		} finally {
			em.close();
			return funcionario;
		}
	}

	public List<Chamado> listarChamadoPorFuncionario(Long cod, String status) {
//		final String status2 = "PROCESSANDO";
		List<Chamado> chamados;

		EntityManager em = getEntityManager();
		if (status.equals("TODOS")) {
			Query q = em.createQuery(
					"SELECT object(chamado)" + " FROM Chamado as chamado" + " WHERE funcionario.id = :cod ");
			q.setParameter("cod", cod);
			chamados = q.getResultList();
		} else {
			Query q = em.createQuery("SELECT object(chamado)" + " FROM Chamado as chamado"
					+ " WHERE funcionario.id = :cod " + "AND status = :status");
			q.setParameter("cod", cod);
			q.setParameter("status", status);
			chamados = q.getResultList();
		}
		return chamados;
	}

	/*
	 * public List<Chamado> listarChamadoPorFuncionarioStatus(Long id, String
	 * status) { EntityManager em = getEntityManager(); Query q =
	 * em.createQuery("SELECT object(chamado)" + " FROM Chamado as chamado " +
	 * "WHERE funcionario.id = :id AND status = :status"); q.setParameter("status",
	 * status); q.setParameter("id", id); List<Chamado> chamados =
	 * q.getResultList(); return chamados; }
	 */
}
