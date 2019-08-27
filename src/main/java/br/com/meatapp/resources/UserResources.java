package br.com.meatapp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.meatapp.domain.User;
import br.com.meatapp.service.UserService;

@RestController
@RequestMapping(value="users")
public class UserResources {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<User>> findAll(){
		List<User> users = userService.findAll();
		
		return ResponseEntity.ok().body(users);
	}
	
	@RequestMapping(value="{id}" ,method=RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable Integer id){
		User user = userService.findById(id);
		
		return ResponseEntity.ok().body(user);
	}
}
