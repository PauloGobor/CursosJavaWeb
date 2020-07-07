package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Controller.ProfessorBean;
import Controller.UsuarioBean;
import Interfaces.IDAO;
import Model.Matricula;

public class MatriculaDAO extends GenericDAO implements IDAO<Matricula> {
	Matricula matricula = new Matricula();
	ProfessorBean pb = new ProfessorBean();
	UsuarioBean ub = new UsuarioBean();

	@Override
	public void salvar(Matricula matricula) {
		EntityManager em = getEntityManager();
		try {
			if (matricula.getId() == null) {
				em.getTransaction().begin();
				em.persist(matricula);
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
	public List<Matricula> listar() {
		EntityManager em = getEntityManager();
		Query q = em.createQuery("SELECT object(matricula)" + " FROM Matricula as matricula");
		List<Matricula> matriculas = q.getResultList();
		return matriculas;
	}

	public List<Matricula> listarMatriculasPorProfessor(Long cod) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(
				"SELECT object(matricula)" + " FROM Matricula as matricula" + " WHERE curso.professor.id = :cod");
		q.setParameter("cod", cod);
		List<Matricula> matriculas = q.getResultList();
		return matriculas;

	}

	public List<Matricula> listarMatriculasPorCurso(Long cod) {

		EntityManager em = getEntityManager();
		Query q = em
				.createQuery("SELECT object(matricula)" + " FROM Matricula as matricula" + " WHERE curso.id = :cod");
		q.setParameter("cod", cod);
		List<Matricula> matriculas = q.getResultList();
		return matriculas;
		/*
		 * } else { Query q = em.createQuery("SELECT object(matricula)" +
		 * " FROM Matricula as matricula" + " WHERE curso.id = :cod " +
		 * " AND status = :status"); q.setParameter("cod", cod);
		 * q.setParameter("status", status); List<Matricula> matriculas =
		 * q.getResultList(); return matriculas; }
		 */
	}

	@SuppressWarnings("finally")
	public Matricula validaUsuarioMatricula(Long usrcod, Long crscod) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT object(matricula)" + " FROM Matricula as matricula"
					+ " WHERE curso.id = :crscod " + " AND usuario.id = :usrcod");
			q.setParameter("usrcod", usrcod);
			q.setParameter("crscod", crscod);
			matricula = (Matricula) q.getSingleResult();

		} catch (Exception nre) {
			matricula = null;

		} finally {
			em.close();
			return matricula;
		}
	}

	// mostrar para o aluno os cursos que ele esta matriculado

	public List<Matricula> listarMatriculasPorAluno(Long cod) {

		EntityManager em = getEntityManager();

		Query q = em.createQuery(
				"SELECT object(matricula)" + " FROM Matricula as matricula " + " WHERE usuario.id = :cod ");
		q.setParameter("cod", cod);
		List<Matricula> matriculas = q.getResultList();
		return matriculas;
		/*
		 * Query q = em.createQuery("SELECT object(matricula)" +
		 * " FROM Matricula as matricula" + " WHERE usuario.id = :cod " +
		 * " AND status = :status"); q.setParameter("cod", cod);
		 * q.setParameter("status", status); List<Matricula> matriculas =
		 * q.getResultList(); return matriculas; }
		 */

	}

	@Override
	public Matricula buscarPorId(Long id) {
		if (id != null) {
			matricula = getEntityManager().find(Matricula.class, id);
		}
		return matricula;
	}

	@Override
	public Matricula buscarPorNome(String nome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void alterar(Matricula matricula) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(matricula);
		em.getTransaction().commit();
	}

	@Override
	public void excluir(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Matricula buscarPorCpf(String cpf) {
		throw new UnsupportedOperationException();
	}
}
