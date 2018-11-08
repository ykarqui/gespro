package ar.edu.iua.gespro.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.IListBusiness;
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.persistence.FactoryDAO;
import ar.edu.iua.gespro.model.persistence.SprintListDAO;

@Service
public class ListBusiness implements IListBusiness {

	private static ListBusiness instance;
	
	@Autowired
	private ITaskBusiness taskBusiness;

	private ListBusiness() {
	}
	
	
	public static ListBusiness getInstance() {
		if (instance == null) {
			instance = new ListBusiness();
		}
		return instance;
	}
	
	@Override
	public List<SprintList> getAll() throws BusinessException {
		try {
			List <SprintList> sl = new ArrayList<>();
			
			sl = FactoryDAO.getInstance().getListDAO().findAll();
			
			return sl;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public SprintList add(SprintList sprintList) throws BusinessException, InvalidListNameException, NotFoundException {
		String name = sprintList.getName();
		//(backlog, TO-DO, in progress, waiting, done)
		if(name.equalsIgnoreCase("backlog") 
		|| name.equalsIgnoreCase("TO-DO") 
		|| name.equalsIgnoreCase("in progress") 
		|| name.equalsIgnoreCase("waiting") 
		|| name.equalsIgnoreCase("done")) {
			SprintList spln = (SprintList) FactoryDAO.getInstance().getListDAO().getOne(name);
			if(spln == null) {
				SprintList sl = (SprintList) FactoryDAO.getInstance().getListDAO().save(sprintList);
				return sl;
			}else{
				throw new InvalidListNameException();
			}
		}else{
			throw new InvalidListNameException();
		}
	}

	@Override
	public SprintList getOne(String name) throws BusinessException, NotFoundException {
		try {
			SprintList spln = (SprintList) FactoryDAO.getInstance().getListDAO().getOne(name);
			return spln;
		} catch (NotFoundException nfe) {
			throw new NotFoundException();
		}
	}
	
	@Override
	public SprintList getOneId(Integer id) throws BusinessException, NotFoundException {
		try {
			SprintList spln = (SprintList) FactoryDAO.getInstance().getListDAO().getOneId(id);
			return spln;
		} catch (NotFoundException nfe) {
			throw new NotFoundException();
		} catch (BusinessException be) {
			throw new BusinessException();
		}
	}

	@Override
	public SprintList update(SprintList sprintList) throws BusinessException, NotFoundException {
		try {
			if (getOneId(sprintList.getId())!=null) {
				return (SprintList) FactoryDAO.getInstance().getListDAO().update(sprintList);
			}else{
				throw new NotFoundException();
			}
		}catch (NotFoundException nfe) {
			throw new NotFoundException(nfe);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(SprintList sprintList) throws BusinessException, NotFoundException {
		try {
			
			for (SprintTask st : sprintList.getTask()) {
				System.out.println("se borra la tarea "+st.getName());
				taskBusiness.delete(st);
			}
			
			System.out.println("Estoy por borrar la LISTA");
			FactoryDAO.getInstance().getListDAO().delete(sprintList);
				
		} catch(NotFoundException nfe) {
			throw new NotFoundException(nfe);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}

}
