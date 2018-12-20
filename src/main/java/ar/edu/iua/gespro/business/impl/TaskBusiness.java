package ar.edu.iua.gespro.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.IListBusiness;
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.EmptyTaskException;
import ar.edu.iua.gespro.model.exception.InvalidInputException;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.ListNotFoundException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.exception.TaskNameAlreadyExist;
import ar.edu.iua.gespro.model.persistence.FactoryDAO;
import ar.edu.iua.gespro.model.persistence.SprintTaskRepository;

@Service
public class TaskBusiness implements ITaskBusiness {

	private String initialList = "backlog";
	final static Logger logger = Logger.getLogger("TaskBusiness.class");

	@Autowired
	private SprintTaskRepository taskDAO;

	@Autowired
	private IListBusiness listBusiness;

	// Get all the task <Using JPA>
	@Override
	public List<SprintTask> getAll() throws BusinessException {
		try {
			return taskDAO.findAll();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<SprintTask> getAllByPriority() throws BusinessException {
		try {
			List<SprintTask> stl = taskDAO.findAll();
			return orderByPriority(stl);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<SprintTask> orderByPriority(List<SprintTask> tasks) {
		List<SprintTask> orList = new ArrayList<>();
		for (SprintTask task : tasks) {
			if (task.getPriority().equalsIgnoreCase("high")) {
				orList.add(task);
			}
		}
		for (SprintTask task : tasks) {
			if (task.getPriority().equalsIgnoreCase("medium")) {
				orList.add(task);
			}
		}
		for (SprintTask task : tasks) {
			if (task.getPriority().equalsIgnoreCase("low")) {
				orList.add(task);
			}
		}
		return orList;
	}

	@Override
	public List<SprintTask> getByCreated(Integer id) throws BusinessException {
		try {
			return taskDAO.findByCreated(id);
		} catch (Exception e) {
			logger.error("can't get all tasks (B_Ex)");
			logger.error(e);
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<SprintTask> getAllByCreated() throws BusinessException {
		try {
			return taskDAO.findAllByCreated();
		} catch (Exception e) {
			logger.error("can't get all tasks (B_Ex)");
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	// Save a task in backlog list <Using JPA>
	@Override
	public SprintTask add(SprintTask sprintTask) throws BusinessException, NotFoundException, ListNotFoundException,
			TaskNameAlreadyExist, EmptyTaskException, InvalidInputException {
		try {
			SprintList sl = (SprintList) FactoryDAO.getInstance().getListDAO().getOne(initialList);
			if (sl != null) {
				List<SprintTask> oldTasks = getAll();
				for (SprintTask task : oldTasks) {
					if (task.getName().equalsIgnoreCase(sprintTask.getName())) {
						throw new TaskNameAlreadyExist();
					}
				}

				if (sprintTask.getName() == null || sprintTask.getPriority() == null) {
					throw new EmptyTaskException();
				}

				if (sprintTask.getPriority().equalsIgnoreCase("high")
						|| sprintTask.getPriority().equalsIgnoreCase("medium")
						|| sprintTask.getPriority().equalsIgnoreCase("low")) {
					sprintTask.setListName(sl);
					java.sql.Timestamp currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
					sprintTask.setCreated(currentDate);
					sprintTask.setModified(currentDate);
					logger.debug("The task " + sprintTask.getName() + " has been asigned to " + sl.getName() + " list");
					return taskDAO.save(sprintTask);
				} else {
					throw new InvalidInputException();
				}

			} else {
				throw new ListNotFoundException();
			}

		} catch (TaskNameAlreadyExist be) {
			logger.error("The task " + sprintTask.getName() + " already exists");
			logger.error(be);
			throw new TaskNameAlreadyExist(be);
		} catch (EmptyTaskException be) {
			logger.error("The task can't have NULL values");
			logger.error(be);
			throw new EmptyTaskException(be);
		} catch (InvalidInputException be) {
			logger.error("The task can't have NULL values");
			logger.error(be);
			throw new EmptyTaskException(be);
		} catch (BusinessException be) {
			logger.error("The task " + sprintTask.getName() + " can't been saved (B_Ex)");
			logger.error(be);
			throw new BusinessException(be);
		} catch (NotFoundException nfe) {
			logger.error("The task " + sprintTask.getName() + " can't been saved (NF_Ex)");
			logger.error(nfe);
			throw new NotFoundException(nfe);
		} catch (ListNotFoundException e) {
			logger.error("The task " + sprintTask.getName() + " can't been saved (LNF_Ex)");
			logger.error(e);
			throw new ListNotFoundException(e);
		} catch (Exception e) {
			logger.error("The task " + sprintTask.getName() + " can't been saved (B_Ex)");
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	// Get one task
	@Override
	public SprintTask getOne(String name) throws BusinessException, NotFoundException {
		try {
			List<SprintTask> stl = taskDAO.findAll();
			SprintTask sprintTask = null;

			for (SprintTask st : stl) {
				if (st.getName().equalsIgnoreCase(name)) {
					sprintTask = st;
				}
			}
			if (sprintTask == null) {
				logger.error("We can't found the Task required");
				throw new NotFoundException();
			}

			logger.debug("Task found it! " + sprintTask.getName());
			return sprintTask;
		} catch (NotFoundException nfe) {
			logger.error(nfe);
			throw new NotFoundException(nfe);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	// Update a task
	@Override
	public SprintTask update(SprintTask sprintTask) throws BusinessException, NotFoundException, InvalidListNameException, InvalidInputException {
		Optional<SprintTask> st = taskDAO.findById(sprintTask.getId());
		if (!st.isPresent()) {
			logger.error(
					"Task with ID: " + sprintTask.getId() + " and name: " + sprintTask.getName() + " dindn't found it");
			throw new NotFoundException();
		}

		try {
			// Inicio checkeo
			java.sql.Timestamp modifiedDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			st.get().setModified(modifiedDate);
			
			//IF Checkea la estimación y la setea a la task
			if(sprintTask.getEstimate()!=null && sprintTask.getEstimate()>0) {
				st.get().setEstimate(sprintTask.getEstimate());
			}
			
			// Checkea si se modifica la lista a la que pertenece
			HashMap<String, String[]> sourceDestination = new HashMap<String, String[]>();
			sourceDestination.put("backlog", new String[]{"to-do", "backlog"});
			sourceDestination.put("to-do", new String[]{"in progress", "waiting", "done"});
			sourceDestination.put("in progress", new String[]{"waiting", "to-do", "done"});
			sourceDestination.put("waiting", new String[]{"in progress", "to-do", "done"});
			sourceDestination.put("done", new String[]{});

	        if (Arrays.asList(sourceDestination.get(st.get().getListName().getName().toLowerCase()))
	        		.contains(sprintTask.getListName().getName().toLowerCase())){
	        	if(st.get().getEstimate()!=null && st.get().getEstimate()>0) {
	        		st.get().setListName(listBusiness.getOne(sprintTask.getListName().getName()));
	        	} else {
	        		throw new InvalidInputException();
	        	}  
	        } else {
	        	throw new InvalidListNameException();
	        }
			
			logger.debug("Task with name: " + st.get().getName() + " has been updated");
			return taskDAO.save(st.get());

		} catch (InvalidListNameException ilne) {
			logger.error(ilne);
			throw new InvalidListNameException(ilne);
		} catch (InvalidInputException iie) {
			logger.error(iie);
			throw new InvalidInputException(iie);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	// Delete a task
	@Override
	public void delete(SprintTask sprintTask) throws BusinessException, NotFoundException {
		try {
			if (sprintTask.getId() != null) {
				Optional<SprintTask> st = taskDAO.findById(sprintTask.getId());
				if (st.isPresent()) {
					taskDAO.delete(sprintTask);
					logger.debug("Task with ID: " + sprintTask.getId() + " has been deleted");
				} else {
					logger.error("Task with ID: " + sprintTask.getId() + " hasn't been found it");
					throw new NotFoundException();
				}
			} else {
				sprintTask = getOne(sprintTask.getName());
				taskDAO.delete(sprintTask);
				logger.debug("Task with name: " + sprintTask.getName() + " has been deleted");
			}
		} catch (NotFoundException ne) {
			logger.error(
					"Task with ID: " + sprintTask.getId() + " and name: " + sprintTask.getName() + " can't be deleted");
			logger.error(ne);
			throw new NotFoundException(ne);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}
}
