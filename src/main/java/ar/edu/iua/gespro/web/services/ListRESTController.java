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

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SprintList>> getAll() {
		try {
			logger.trace("Getting all task lists");
			return new ResponseEntity<List<SprintList>>(listBusiness.getAll(), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
			return new ResponseEntity<List<SprintList>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SprintList> getId(@PathVariable("name") String name) {
		try {
			logger.debug("Trying to get the following list: " + name);
			return new ResponseEntity<SprintList>(listBusiness.getOne(name), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}
	}

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
			return new ResponseEntity<SprintList>(HttpStatus.NOT_ACCEPTABLE);
		} catch (BusinessException be) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		} catch (NotFoundException nfe) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<SprintList> update(@PathVariable("id") int id, @RequestBody SprintList sprintList) {
		try {
			sprintList.setId(id);
			listBusiness.update(sprintList);
			return new ResponseEntity<SprintList>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}
	}

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
				return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
			}
			logger.trace("List has been deleted");
			return new ResponseEntity<SprintList>(HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
			return new ResponseEntity<SprintList>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
			return new ResponseEntity<SprintList>(HttpStatus.NOT_FOUND);
		}

	}
}
