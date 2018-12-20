package ar.edu.iua.gespro.business;

import java.util.List;

import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.NotFoundException;

public interface IListBusiness {
	
	public List<SprintList> getAll() throws BusinessException;
	public SprintList add(SprintList sprintList) throws BusinessException, InvalidListNameException, NotFoundException;
	public SprintList getOne(String name) throws BusinessException, NotFoundException;
	public SprintList getOneByPriority(String name) throws BusinessException, NotFoundException;
	public SprintList getOneByCreated(String name) throws BusinessException, NotFoundException;
	public SprintList getOneId(Integer id) throws BusinessException, NotFoundException;
	public SprintList update(SprintList sprintList) throws BusinessException, NotFoundException;
	public void delete(SprintList sprintList) throws BusinessException, NotFoundException;
}
