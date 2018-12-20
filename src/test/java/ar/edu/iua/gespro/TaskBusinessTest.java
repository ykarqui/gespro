package ar.edu.iua.gespro;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.impl.TaskBusiness;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskBusinessTest {
	
	@Autowired
	private TaskBusiness tb;
	
	public TaskBusinessTest() {}
	
	
	@Test
	public void testRememberDelete() {
		assertEquals(1, 1);
	}
	
	@Test
	public void get_first_task() throws BusinessException {
		List<SprintTask> stl = new ArrayList<SprintTask>(); 
		stl = tb.getAll();
		assertEquals("Testing", stl.get(0).getName());
	}
	
	@Test
	public void givenTaskDoesNotExists_whenTaskInfoIsRetrieved_then404IsReceived()
	  throws NotFoundException, IOException {
	  
	   // Given
	   String name = RandomStringUtils.randomAlphabetic( 8 );
	   HttpUriRequest request = new HttpGet( "localhost:8080/api/v1/task/" + name );
	 
	   // When
	   HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
	   
	   assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
	   // Then
	   /*assertThat(httpResponse.getStatusLine().getStatusCode(),
	     equalTo(HttpStatus.SC_NOT_FOUND));*/
	}

}
