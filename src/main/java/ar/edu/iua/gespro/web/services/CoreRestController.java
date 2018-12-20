package ar.edu.iua.gespro.web.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreRestController extends BaseRestController {

	
	@GetMapping(Constants.URL_DENY)
	public ResponseEntity<String> deny() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	
	@GetMapping(Constants.URL_LOGOUTOK)
	public ResponseEntity<String> logoutok() {
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping(value = Constants.URL_LOGINOK)
	public ResponseEntity<String> loginok() {
		return new ResponseEntity<String>(userToJson(getUserPrincipal()).toString(), HttpStatus.OK);
	}
	@GetMapping(value = Constants.URL_AUTH_INFO)
	public ResponseEntity<String> authInfo() {
		return new ResponseEntity<String>(userToJson(getUserPrincipal()).toString(), HttpStatus.OK);
	}
}
