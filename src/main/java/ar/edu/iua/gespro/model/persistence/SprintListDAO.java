package ar.edu.iua.gespro.model.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
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
	final static Logger logger = Logger.getLogger("SprintListDAO.class");
	
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
			logger.error("Hibernate fail in findAll()");
			logger.error(e);
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
		logger.debug("saving the list: "+object.getName());
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
		logger.debug("updating the list: "+object.getName());
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
				logger.error("List not found. getOne(name)");
				throw new NotFoundException();
			}
			return sprintList.get(0);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e);
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
	}
	
	@Override
	public boolean checkIfExists(String name) throws BusinessException{
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
				return false;
			}else {
				return true;
			}

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e);
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		throw new BusinessException();
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
			logger.error(e);
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
			logger.debug("the list: "+object.getName()+ " has been deleted");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e);
			e.printStackTrace();
			logger.error("Hibernate failure");
		} finally {
			session.close();
		}
	}
	
}
