package ar.edu.iua.gespro.model.persistence;

import org.springframework.context.annotation.Bean;

public class FactoryDAO {
	private static FactoryDAO instance;
	
	private FactoryDAO() {
		
	}
	
	@Bean
	public static FactoryDAO getInstance() {
		if (instance == null) {
			instance = new FactoryDAO();
		}
		return instance;
	}
	
	private static String dataBaseActive = "MYSQL";
	
	public static IGenericDAO getListDAO() {
		if(dataBaseActive == "MYSQL") {
			return SprintListDAO.getInstance();
		}
		return null;
	}
}
