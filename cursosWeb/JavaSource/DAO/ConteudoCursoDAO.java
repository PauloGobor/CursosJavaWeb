package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.ConteudoCurso;

public class ConteudoCursoDAO extends GenericDAO implements IDAO<ConteudoCurso> {

	ConteudoCurso conteudo = new ConteudoCurso();

	@Override
	public void salvar(ConteudoCurso conteudo) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		try {
			if (conteudo.getId() == null) {
				em.getTransaction().begin();
				em.persist(conteudo);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				em.merge(conteudo);
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
	public List<ConteudoCurso> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConteudoCurso> listarConteudoPorCurso(Long cod) {
		EntityManager em = getEntityManager();
		Query q = em
				.createQuery("SELECT object(conteudo)" + " FROM ConteudoCurso as conteudo" + " WHERE curso.id = :cod");
		q.setParameter("cod", cod);
		List<ConteudoCurso> conteudos = q.getResultList();
		return conteudos;

	}

	@Override
	public ConteudoCurso buscarPorId(Long id) {
		if (id != null) {
			conteudo = getEntityManager().find(ConteudoCurso.class, id);
		}
		return conteudo;
	}

	@Override
	public ConteudoCurso buscarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(ConteudoCurso conteudo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ConteudoCurso buscarPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

}
