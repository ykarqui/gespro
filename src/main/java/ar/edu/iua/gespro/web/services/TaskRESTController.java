package ar.edu.iua.gespro.web.services;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
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
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.EmptyTaskException;
import ar.edu.iua.gespro.model.exception.InvalidInputException;
import ar.edu.iua.gespro.model.exception.InvalidListNameException;
import ar.edu.iua.gespro.model.exception.ListNotFoundException;
import ar.edu.iua.gespro.model.exception.NotFoundException;
import ar.edu.iua.gespro.model.exception.TaskNameAlreadyExist;

@RestController
@RequestMapping(Constants.URL_SPRINT_TASK)
public class TaskRESTController {

	@Autowired
	private ITaskBusiness taskBusiness;
	final static Logger logger = Logger.getLogger("TaskRESTController.class");

	// Get all tasks
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SprintTask>> getAll(
			@RequestParam(required = false, value = "ob", defaultValue = "*") String ob) {
		try {

			if (ob.equals("*")) {
				logger.trace("Getting all Tasks");
				return new ResponseEntity<List<SprintTask>>(taskBusiness.getAll(), HttpStatus.OK);
			} else if (ob.equalsIgnoreCase("priority")) {
				logger.trace("Getting all Tasks, Order By Priority");
				return new ResponseEntity<List<SprintTask>>(taskBusiness.getAllByPriority(), HttpStatus.OK);
			} else if (ob.equalsIgnoreCase("created")) {
				logger.debug("Getting all Tasks Order By Created Date");
				return new ResponseEntity<List<SprintTask>>(taskBusiness.getAllByCreated(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<SprintTask>>(HttpStatus.BAD_REQUEST);
			}

		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
			logger.error(e);
			return new ResponseEntity<List<SprintTask>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get one task with the name
	@RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SprintTask> getId(@PathVariable("name") String name) {
		try {
			logger.debug("Trying to get the following task: " + name);
			return new ResponseEntity<SprintTask>(taskBusiness.getOne(name), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}

	// Save a task
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SprintTask> add(@RequestBody SprintTask sprintTask) {
		try {
			SprintTask st = taskBusiness.add(sprintTask);
			logger.debug("A task has been saved");
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", "/task/" + sprintTask.getId());
			return new ResponseEntity<SprintTask>(st, responseHeaders, HttpStatus.CREATED);
		} catch (TaskNameAlreadyExist e) {
			logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in add()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_ACCEPTABLE);
		} catch (NotFoundException nfe) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			logger.error(nfe);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		} catch (ListNotFoundException lnfe) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			logger.error(lnfe);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in add()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EmptyTaskException e) {
			logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in add()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_ACCEPTABLE);
		} catch (InvalidInputException e) {
			logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in add()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// Update a task
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<SprintTask> update(@PathVariable("id") int id, @RequestBody SprintTask sprintTask) {
		try {
			sprintTask.setId(id);
			taskBusiness.update(sprintTask);
			logger.debug("Updated the following task: \n" + sprintTask);
			return new ResponseEntity<SprintTask>(HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in update()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in update()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		} catch (InvalidListNameException ilne) {
			logger.error("Http status:" + HttpStatus.NOT_ACCEPTABLE + " in update()");
			logger.error(ilne);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_ACCEPTABLE);
		} catch (InvalidInputException iie) {
			logger.error("Http status:" + HttpStatus.METHOD_NOT_ALLOWED + " in update()");
			logger.error(iie);
			return new ResponseEntity<SprintTask>(HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	// Delete a task with the ID or name
	@RequestMapping(value = { "", "/" }, method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<SprintTask> delete(
			@RequestParam(required = false, value = "id", defaultValue = "0") Integer id,
			@RequestParam(required = false, value = "name", defaultValue = "") String name) {
		try {
			SprintTask st = new SprintTask();
			if (id != 0) {
				st.setId(id);
				taskBusiness.delete(st);
			} else if (!name.equalsIgnoreCase("") && name.length() > 0) {
				st.setName(name);
				taskBusiness.delete(st);
			} else {
				logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
				logger.error(st);
				return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
			}
			logger.trace("Task has been deleted");
			return new ResponseEntity<SprintTask>(HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
			logger.error(e);
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}
}
