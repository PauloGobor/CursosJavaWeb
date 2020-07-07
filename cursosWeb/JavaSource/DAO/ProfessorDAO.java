package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.Curso;
import Model.Professor;

public class ProfessorDAO extends GenericDAO implements IDAO<Professor> {

	Professor professor = new Professor();

	@Override
	public void salvar(Professor professor) {
		EntityManager em = getEntityManager();
		try {
			if (professor.getId() == null) {
				em.getTransaction().begin();
				em.persist(professor);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				em.merge(professor);
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
	public List<Professor> listar() {
		EntityManager em = getEntityManager();
		Query q;

		q = em.createQuery("SELECT object(professor) FROM Professor as professor");
		List<Professor> professores = q.getResultList();
		return professores;
	}

	@Override
	public Professor buscarPorId(Long cod) {
		if (cod != null) {
			professor = getEntityManager().find(Professor.class, cod);
		}
		return professor;
	}

	@SuppressWarnings("finally")
	@Override
	public Professor buscarPorCpf(String cpf) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT object(professor)" + " FROM Professor as professor" + " WHERE cpf=:cpf ");
			q.setParameter("cpf", cpf);

			professor = (Professor) q.getSingleResult();
		} catch (Exception nre) {
			professor = null;

		} finally {
			em.close();
			return professor;
		}
	}

	public List<Curso> listarCursoPorProfessor(Long cod) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery("SELECT object(curso)" + " FROM Curso as curso" + " WHERE professor.id = :cod");
		q.setParameter("cod", cod);
		List<Curso> cursos = q.getResultList();
		return cursos;

	}

	@Override
	public Professor buscarPorNome(String nome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void alterar(Professor professor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void excluir(Long id) {
		throw new UnsupportedOperationException();
	}

}
