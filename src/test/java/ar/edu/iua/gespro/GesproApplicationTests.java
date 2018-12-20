package ar.edu.iua.gespro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureTask;

import ar.edu.iua.gespro.business.BusinessException;
import ar.edu.iua.gespro.business.IListBusiness;
import ar.edu.iua.gespro.business.impl.ListBusiness;
import ar.edu.iua.gespro.business.impl.TaskBusiness;
import ar.edu.iua.gespro.model.SprintList;
import ar.edu.iua.gespro.model.SprintTask;
import ar.edu.iua.gespro.model.exception.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GesproApplicationTests {
	
	@Autowired
	private TaskBusiness tb;
	
	
	@Test
	void testRememberDelete() {
		assertEquals(1, 1);
	}
	//CHEK IT! https://www.baeldung.com/integration-testing-a-rest-api

}
