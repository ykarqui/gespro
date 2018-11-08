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
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.NotFoundException;

@RestController
@RequestMapping(Constants.URL_SPRINT_TASK)
public class TaskRESTController {

	@Autowired
	private ITaskBusiness taskBusiness;
	final static Logger logger = Logger.getLogger("TaskRESTController.class");
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SprintTask>> getAll() {
		try {
			logger.trace("Getting all tasks");
			return new ResponseEntity<List<SprintTask>>(taskBusiness.getAll(), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getAll()");
			return new ResponseEntity<List<SprintTask>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SprintTask> getId(@PathVariable("name") String name) {
		try {
			logger.debug("Trying to get the following task: " + name);
			return new ResponseEntity<SprintTask>(taskBusiness.getOne(name), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in getId()");
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in getId()");
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = {"","/"}, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SprintTask> add(@RequestBody SprintTask sprintTask){
		try {
			SprintTask st = taskBusiness.add(sprintTask);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", "/task/" + sprintTask.getId());
			logger.debug("Adding the following task: \n" + st);
			return new ResponseEntity<SprintTask>(st, responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException be) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in add()");
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<SprintTask> update(@PathVariable("id") int id, @RequestBody SprintTask sprintTask) {
		try {
			sprintTask.setId(id);
			taskBusiness.update(sprintTask);
			logger.debug("Updated the following task: \n" + sprintTask);
			return new ResponseEntity<SprintTask>(HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in update()");
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in update()");
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<SprintTask> delete(
			@RequestParam(required = false, value = "id", defaultValue = "0") Integer id,
			@RequestParam(required = false, value = "name", defaultValue = "") String name) {
		try {
			SprintTask st = new SprintTask();
			if (id!=0) {
				st.setId(id);
				taskBusiness.delete(st);
			}else if (!name.equalsIgnoreCase("") && name.length()>0) {
				st.setName(name);
				taskBusiness.delete(st);
			}else {
				logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
				return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
			}
			logger.trace("Task has been deleted");
			return new ResponseEntity<SprintTask>(HttpStatus.OK);
		} catch (BusinessException e) {
			logger.error("Http status:" + HttpStatus.INTERNAL_SERVER_ERROR + " in delete()");
			return new ResponseEntity<SprintTask>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error("Http status:" + HttpStatus.NOT_FOUND + " in delete()");
			return new ResponseEntity<SprintTask>(HttpStatus.NOT_FOUND);
		}
	}
}