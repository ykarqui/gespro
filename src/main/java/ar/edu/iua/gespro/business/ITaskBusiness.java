package ar.edu.iua.gespro.business;

import java.util.List;

import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.EmptyTaskException;
import ar.edu.iua.gespro.model.exception.InvalidInputException;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.ListNotFoundException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.exception.TaskNameAlreadyExist;

public interface ITaskBusiness {
	
	public List<SprintTask> getAll() throws BusinessException;
	public List<SprintTask> getAllByPriority() throws BusinessException;
	public List<SprintTask> getByCreated(Integer id) throws BusinessException;
	public List<SprintTask> getAllByCreated() throws BusinessException;
	public List<SprintTask> orderByPriority(List<SprintTask> tasks);
	public SprintTask add(SprintTask sprintTask) throws BusinessException, NotFoundException, ListNotFoundException, TaskNameAlreadyExist,EmptyTaskException, InvalidInputException;
	public SprintTask getOne(String name) throws BusinessException, NotFoundException;
	public SprintTask update(SprintTask sprintTask, boolean leader) throws BusinessException, NotFoundException, InvalidListNameException, InvalidInputException;
	public void delete(SprintTask sprintTask) throws BusinessException, NotFoundException;

}
