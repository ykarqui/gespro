package ar.edu.iua.gespro;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.iua.gespro.model.persistence.UsuariosRespository;


@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class GesproApplication implements CommandLineRunner{

	final static Logger logger = Logger.getLogger("GesproApplication.class");
	
	public static void main(String[] args) {
		
		logger.info("Start the app");
		SpringApplication.run(GesproApplication.class, args);
	}

	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private UsuariosRespository uDAO;
	
	@Override
	public void run(String... args) throws Exception {
		uDAO.setPassword(pe.encode("password"), "admin", "");
	}
}
