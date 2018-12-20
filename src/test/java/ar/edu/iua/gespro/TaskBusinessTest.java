package ar.edu.iua.gespro;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.IListBusiness;
import ar.edu.iua.gespro.business.ITaskBusiness;
import ar.edu.iua.gespro.business.impl.TaskBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskBusinessTest {

	@Autowired
	ITaskBusiness t;
	@Autowired
	IListBusiness l;

	public TaskBusinessTest() {
	}

	static HashMap<String, String> simulatedValues;

	//Expected values
	@Before
	public void initialValues() {
		simulatedValues = new HashMap<String, String>();
		simulatedValues.put("task1", "Testing");
		simulatedValues.put("priority_1", "Low");
		simulatedValues.put("list_1", "TODO");

		simulatedValues.put("task2", "Prueba");
		simulatedValues.put("priority_2", "High");
		simulatedValues.put("list_2", "TODO");

		simulatedValues.put("task3", "Documentation");
		simulatedValues.put("priority_3", "Low");
		simulatedValues.put("list_3", "Backlog");

		simulatedValues.put("task4", "Test");
		simulatedValues.put("priority_4", "Medium");
		simulatedValues.put("list_4", "TODO");

		simulatedValues.put("task5", "Add css");
		simulatedValues.put("priority_5", "High");
		simulatedValues.put("list_5", "TODO");

		simulatedValues.put("name_list_1", "backlog");
		simulatedValues.put("sprint_name_1", "sprint 1");

		simulatedValues.put("name_list_2", "to-do");
		simulatedValues.put("sprint_name_2", "sprint 1");

		simulatedValues.put("name_list_3", "in progress");
		simulatedValues.put("sprint_name_3", "sprint 1");
	}

	// Compare the first task
	@Test
	public void get_first_task() throws BusinessException {
		List<SprintTask> stl = new ArrayList<SprintTask>();
		stl = t.getAll();
		assertEquals(stl.get(0).getName(), simulatedValues.get("task1"));
	}

	// Compare de last list [in progress]
	@Test
	public void get_the_last_list() throws BusinessException {
		List<SprintList> compareList = l.getAll();

		assertEquals(compareList.get(2).getName(), simulatedValues.get("name_list_3"));
	}

	// Method for 3 list saved. [Backlog, to-do, in progress]
	@Test
	public void get_all_lists() throws BusinessException {
		List<SprintList> compareList = l.getAll();

		assertEquals(compareList.get(0).getName(), simulatedValues.get("name_list_1"));
		assertEquals(compareList.get(0).getSprintName(), simulatedValues.get("sprint_name_1"));

		assertEquals(compareList.get(1).getName(), simulatedValues.get("name_list_2"));
		assertEquals(compareList.get(1).getSprintName(), simulatedValues.get("sprint_name_2"));

		assertEquals(compareList.get(2).getName(), simulatedValues.get("name_list_3"));
		assertEquals(compareList.get(2).getSprintName(), simulatedValues.get("sprint_name_3"));
	}
	
	
	//Get all the tasks
	@Test
	public void get_all_tasks() throws BusinessException {
		List<SprintTask> tasks = t.getAll();

		assertEquals(tasks.get(0).getName(), simulatedValues.get("task1"));
		assertEquals(tasks.get(0).getPriority(), simulatedValues.get("priority_1"));
		assertEquals(tasks.get(0).getListName().getName(), simulatedValues.get("list_1"));
		
		assertEquals(tasks.get(1).getName(), simulatedValues.get("task2"));
		assertEquals(tasks.get(1).getPriority(), simulatedValues.get("priority_2"));
		assertEquals(tasks.get(1).getListName().getName(), simulatedValues.get("list_2"));
		
		assertEquals(tasks.get(2).getName(), simulatedValues.get("task3"));
		assertEquals(tasks.get(2).getPriority(), simulatedValues.get("priority_3"));
		assertEquals(tasks.get(2).getListName().getName(), simulatedValues.get("list_3"));
		
		assertEquals(tasks.get(3).getName(), simulatedValues.get("task4"));
		assertEquals(tasks.get(3).getPriority(), simulatedValues.get("priority_4"));
		assertEquals(tasks.get(3).getListName().getName(), simulatedValues.get("list_4"));
		
		assertEquals(tasks.get(4).getName(), simulatedValues.get("task5"));
		assertEquals(tasks.get(4).getPriority(), simulatedValues.get("priority_5"));
		assertEquals(tasks.get(4).getListName().getName(), simulatedValues.get("list_5"));
		
		
	}
	
	//Dummy test
	@Test
	public void testRememberDelete() {
		assertEquals(1, 1);
	}

}
