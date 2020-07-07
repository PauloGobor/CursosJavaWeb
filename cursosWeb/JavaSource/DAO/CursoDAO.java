package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.Curso;

public class CursoDAO extends GenericDAO implements IDAO<Curso> {
	Curso curso = new Curso();

	@Override
	public void salvar(Curso curso) {
		EntityManager em = getEntityManager();
		try {
			if (curso.getId() == null) {
				em.getTransaction().begin();
				em.persist(curso);
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
	public List<Curso> listar() {
		EntityManager em = getEntityManager();
		Query q;
		q = em.createQuery("SELECT object(curso) FROM Curso as curso");
		List<Curso> cursos = q.getResultList();
		return cursos;
	}

	@Override
	public void alterar(Curso curso) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(curso);
		em.getTransaction().commit();
	}

	@Override
	public void excluir(Long id) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			Curso curso = em.find(Curso.class, id);
			em.remove(curso);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("erro");
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Override
	public Curso buscarPorId(Long cod) {
		if (cod != null) {
			curso = getEntityManager().find(Curso.class, cod);
		}
		return curso;
	}

	@SuppressWarnings("finally")
	@Override
	public Curso buscarPorNome(String nome) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT object(curso)" + " FROM Curso as curso" + " WHERE nome=:nome ");
			q.setParameter("nome", nome);

			curso = (Curso) q.getSingleResult();
		} catch (Exception nre) {
			curso = null;

		} finally {
			em.close();
			return curso;
		}
	}

	@Override
	public Curso buscarPorCpf(String cpf) {
		throw new UnsupportedOperationException();
	}

}
