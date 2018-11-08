package ar.edu.iua.gespro.model.persistence;

import java.io.Serializable;
import java.util.List;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.model.exception.NotFoundException;

public interface IGenericDAO<T, ID extends Serializable>{
	public List<T> findAll();
	public T save(T object);
	public T update(T object);
	public T getOne(String name) throws BusinessException, NotFoundException;
	public T getOneId(int id) throws BusinessException, NotFoundException;
	public void delete(T object) throws NotFoundException;
}
