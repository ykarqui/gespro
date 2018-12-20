package ar.edu.iua.gespro.web.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.IListBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.persistence.FactoryDAO;

@RestController
@RequestMapping(Constants.URL_SPRINT_LIST)
public class ListRESTController {

	@Autowired
	private IListBusiness listBusiness;
	final static Logger logger = Logger.getLogger("TaskRESTController.class");

	// Get all lists --> backlog, to-do, ...
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SprintList>> getAll() {
		try {
			logger.trace("Getting all task lists");
			return new ResponseEntity<List<SprintList>>(listBusiness.getAll(), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
			logger.error(e);
			return new ResponseEntity<List<SprintList>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get one list by name
	@RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SprintList> getId(@PathVariable("name") String name,
			@RequestParam(required = false, value = "ob", defaultValue = "*") String ob) {
		try {
			
			if (ob.equals("*")) {
				logger.debug("Trying to get the following list: " + name);
				return new ResponseEntity<SprintList>(listBusiness.getOne(name), HttpStatus.OK);
			} else if (ob.equalsIgnoreCase("priority")) {
				logger.debug("Trying to get the following list: " + name + "; Tasks Order By Priority");
				return new ResponseEntity<SprintList>(listBusiness.getOneByPriority(name), HttpStatus.OK);
			} else if (ob.equalsIgnoreCase("created")) {
				logger.debug("Trying to get the following list: " + name + "; Tasks Order By Created Date");
				return new ResponseEntity<SprintList>(listBusiness.getOneByCreated(name), HttpStatus.OK);
			}else {
				return new ResponseEntity<SprintList>(HttpStatus.BAD_REQUEST);
			}

		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}
	}

	// Add a list
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SprintList> add(@RequestBody SprintList sprintList) {
		try {
			SprintList sl = listBusiness.add(sprintList);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", "/list/" + sprintList.getId());
			logger.debug("Adding the following list: \n" + sl);
			return new ResponseEntity<SprintList>(sl, responseHeaders, HttpStatus.CREATED);
		} catch (InvalidListNameException iln) {
			logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in add(), the list name is invalid");
			logger.error(iln);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_ACCEPTABLE);
		} catch (BusinessException be) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			logger.error(be);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		} catch (NotFoundException nfe) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			logger.error(nfe);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	// Delete a list with the name or ID
	@RequestMapping(value = { "", "/" }, method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<SprintList> delete(
			@RequestParam(required = false, value = "id", defaultValue = "0") Integer id,
			@RequestParam(required = false, value = "name", defaultValue = "") String name) {
		try {
			SprintList sl = new SprintList();
			if (id != 0) {
				sl = listBusiness.getOneId(id);
				listBusiness.delete(sl);
			} else if (!name.equalsIgnoreCase("") && name.length() > 0) {
				sl = listBusiness.getOne(name);
				listBusiness.delete(sl);
			} else {
				logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
				logger.error(sl);
				return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
			}
			logger.trace("List has been deleted");
			return new ResponseEntity<SprintList>(HttpStatus.OK);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	/*
	// Update a list with the ID list
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<SprintList> update(@PathVariable("id") int id, @RequestBody SprintList sprintList) {
		try {
			sprintList.setId(id);
			listBusiness.update(sprintList);
			return new ResponseEntity<SprintList>(HttpStatus.OK);
		} catch (NotFoundException e) {
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		} catch (BusinessException e) {
			logger.error(e);
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
}
