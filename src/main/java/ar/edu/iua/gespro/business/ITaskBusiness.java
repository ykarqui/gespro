package ar.edu.iua.gespro.business;

import java.util.List;

import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.NotFoundException;

public interface ITaskBusiness {
	
	public List<SprintTask> getAll() throws BusinessException;
	public SprintTask add(SprintTask sprintTask) throws BusinessException;
	public SprintTask getOne(String name) throws BusinessException, NotFoundException;
	public SprintTask update(SprintTask sprintTask) throws BusinessException, NotFoundException;
	public void delete(SprintTask sprintTask) throws BusinessException, NotFoundException;

}
