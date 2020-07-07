package DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Interfaces.IDAO;
import Model.Chamado;

public class ChamadoDAO extends GenericDAO implements IDAO<Chamado> {
	Chamado chamado = new Chamado();

	@Override
	public void salvar(Chamado chamado) {
		EntityManager em = getEntityManager();
		try {
			if (chamado.getId() == null) {
				chamado.setStatus("ABERTO");
				chamado.setDataCriacao(new Date());
				em.getTransaction().begin();
				em.persist(chamado);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				em.merge(chamado);
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
	public List<Chamado> listar() {
		EntityManager em = getEntityManager();
		Query q;
		q = em.createQuery("SELECT object(chamado) FROM Chamado as chamado");
		List<Chamado> chamados = q.getResultList();
		return chamados;
	}

	@Override
	public Chamado buscarPorId(Long id) {
		if (id != null) {
			chamado = getEntityManager().find(Chamado.class, id);
		}
		return chamado;
	}

	@Override
	public Chamado buscarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Chamado chamado) {

	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		try {

			em.getTransaction().begin();
			Chamado chamado = em.find(Chamado.class, id);
			em.remove(chamado);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("erro");
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Override
	public Chamado buscarPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Chamado> listarChamadoPorStatus(String status) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery("SELECT object(chamado)" + " FROM Chamado as chamado" + " WHERE status = :status");
		q.setParameter("status", status);
		List<Chamado> chamados = q.getResultList();
		return chamados;
	}



}
