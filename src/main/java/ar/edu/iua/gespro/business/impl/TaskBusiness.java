package ar.edu.iua.gespro.business.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.ListNotFoundException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.persistence.FactoryDAO;
import ar.edu.iua.gespro.model.persistence.SprintTaskRepository;

@Service
public class TaskBusiness implements ITaskBusiness {
	
	private String initialList = "backlog";
	final static Logger logger = Logger.getLogger("TaskBusiness.class");
	
	@Autowired
	private SprintTaskRepository taskDAO;

	@Override
	public List<SprintTask> getAll() throws BusinessException {
		try {
			return taskDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public SprintTask add(SprintTask sprintTask) throws BusinessException {
		try {
			SprintList sl = (SprintList) FactoryDAO.getInstance().getListDAO().getOne(initialList);
			if (sl!=null) {
				sprintTask.setListName(sl);
				logger.debug("Task with name: "+ sprintTask.getName() + " has been created" + " in "+ sprintTask.getListName());
				return taskDAO.save(sprintTask);
			}else {
				logger.error("Task with name: "+ sprintTask.getName() + " couldn't been created, because the List is null");
				throw new ListNotFoundException();
			}
			
		} catch (Exception e) {
			logger.trace("Business Exception in method 'add' :( ");
			throw new BusinessException(e);
		}
	}
	
	@Override
	public SprintTask getOne(String name) throws BusinessException, NotFoundException {
		List<SprintTask> stl = taskDAO.findAll();
		SprintTask sprintTask = null;
		
		for (SprintTask st : stl) {
			if (st.getName().equalsIgnoreCase(name)) {
				sprintTask = st;
			}
		}
		if (sprintTask==null) {
			logger.error("Task is null, so we can't found the Task required");
			throw new NotFoundException();
		}
		try {
			logger.info("Task found it! "+ sprintTask.getName());
			return sprintTask;
		} catch(Exception e) {
			throw new BusinessException(e);
		} 
	}

	@Override
	public SprintTask update(SprintTask sprintTask) throws BusinessException, NotFoundException {
		Optional<SprintTask> st = taskDAO.findById(sprintTask.getId());
		if (!st.isPresent()) {
			logger.error("Task with ID: "+ sprintTask.getId()+ " and name: " + sprintTask.getName() + " dindn't found it");
			throw new NotFoundException();
		}	
		try {
			logger.debug("Task with name: "+ sprintTask.getName() + " has been updated");
			return taskDAO.save(sprintTask);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(SprintTask sprintTask) throws BusinessException, NotFoundException {
		try {
			if(sprintTask.getId()!=null) {
				Optional<SprintTask> st = taskDAO.findById(sprintTask.getId());
				if (st.isPresent()) {
					taskDAO.delete(sprintTask);
					logger.debug("Task with ID: "+ sprintTask.getId() + " has been deleted");
				}else {
					logger.error("Task with ID: "+ sprintTask.getId() + " hasn't been found it");
					throw new NotFoundException();
				}
			}else {
				sprintTask = getOne(sprintTask.getName());
				taskDAO.delete(sprintTask);
				logger.debug("Task with name: "+ sprintTask.getName() + " has been deleted");
			}
		} catch(NotFoundException ne) {
			logger.error("Task with ID: "+ sprintTask.getId()+ " and name: " + sprintTask.getName() + " can't be deleted");
			throw new NotFoundException(ne);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}
