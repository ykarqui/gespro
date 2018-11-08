package ar.edu.iua.gespro.model.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.NotFoundException;

@Component
public class SprintListDAO implements IGenericDAO<SprintList, Serializable> {
	private static SprintListDAO instance;
	
	@Autowired
	private EntityManagerFactory emf;
	
	public SprintListDAO() {
	}
	
	@Bean
	public static SprintListDAO getInstance() {
		if (instance == null) {
			instance = new SprintListDAO();
		}
		return instance;
	}

	@Override
	public List<SprintList> findAll() {
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.flush();
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SprintList> query = builder.createQuery(SprintList.class);
			Root<SprintList> from = query.from(SprintList.class);

			query.select(from);

			List<SprintList> resultSprintList = session.createQuery(query).getResultList();

			tx.commit();

			return resultSprintList;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public SprintList save(SprintList object) {
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(object);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

		return object;
	}
	
	@Override
	public SprintList update(SprintList object) {
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(object);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

		return object;
	}

	@Override
	public SprintList getOne(String name) throws NotFoundException {
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.flush();
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SprintList> query = builder.createQuery(SprintList.class);
			Root<SprintList> from = query.from(SprintList.class);

			query.select(from).where(builder.equal(from.get("name"), name));
			
			List<SprintList> sprintList = session.createQuery(query).getResultList();
			
			tx.commit();
			if (sprintList.isEmpty()) {
				return null;
			}
			return sprintList.get(0);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
	}
	
	@Override
	public SprintList getOneId(int id) throws BusinessException, NotFoundException{
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			
			SprintList sprintList = session.get(SprintList.class, id);
			
			tx.commit();
			
			return sprintList;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
	}

	@Override
	public void delete(SprintList object) throws NotFoundException {
		Session session = emf.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			SprintList sprintList = session.get(SprintList.class, object.getId());
			if (sprintList != null) {
				session.delete(sprintList);
			}else {
				throw new NotFoundException();
			}

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
