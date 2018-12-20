package ar.edu.iua.gespro.web.services;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URL_TEST)
public class TestRestController extends BaseRestController{

	@GetMapping("/leader")
	@PreAuthorize("hasRole('ROLE_LEADER')")
	public ResponseEntity<String> onlyLeader() {
		return new ResponseEntity<String>("Service for leader", HttpStatus.OK);
	}

	@GetMapping("/dev")
	@PreAuthorize("hasRole('ROLE_DEV')")
	public ResponseEntity<String> onlyDev() {
		return new ResponseEntity<String>("Service for dev", HttpStatus.OK);
	}
	
	@GetMapping("/leaderordev")
	@PreAuthorize("hasRole('ROLE_LEADER') or hasRole('ROLE_DEV')")
	public ResponseEntity<String> leaderOrdev() {
		return new ResponseEntity<String>("Service for leader or dev", HttpStatus.OK);
	}
	
	@PreAuthorize("#username == authentication.principal.username")
	@GetMapping("/myroles")
	public ResponseEntity<String> getMyRoles(@RequestParam("username")  String username) {
		return new ResponseEntity<String>(getUserPrincipal().getAuthorities().toString(), HttpStatus.OK);
	}

	@GetMapping("/var")
	public ResponseEntity<String> onlyLeaderPro(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_LEADER")) {
			return new ResponseEntity<String>("Dinamic Service for LEADER", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Dinamic Service for DEV", HttpStatus.OK);
		}
		
	}
	
	
	@GetMapping("/secured")
	//@Secured no soporta Expression Language (SpEL).
	//https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions
	@Secured({ "ROLE_VIEWER", "ROLE_EDITOR" }) //El usuario debe tener al menos un rol
	public ResponseEntity<String> secured() {
		return new ResponseEntity<String>("Servicio con @Secured", HttpStatus.OK);
	}
	
	@GetMapping("/rolesalowed")
	//@RolesAllowed idem @Secured
	//https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions
	@RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" }) //El usuario debe tener al menos un rol
	public ResponseEntity<String> rolesAllowed() {
		return new ResponseEntity<String>("Servicio con @RolesAllowed", HttpStatus.OK);
	}
	
}
