package br.com.meatapp.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.meatapp.domain.MenuItem;
import br.com.meatapp.service.MenuItemService;

@RestController
@RequestMapping(value="menuitens")
public class MenuItemResources {
	@Autowired
	private MenuItemService menuItemService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MenuItem>> findAll(){
		List<MenuItem> menuitem = menuItemService.findAll();
		
		return ResponseEntity.ok().body(menuitem);
	}
	
	@RequestMapping(value="id/{id}" , method=RequestMethod.GET)
	public ResponseEntity<MenuItem> findById(@PathVariable Integer id){
		MenuItem menuitem = menuItemService.findById(id);
		
		return ResponseEntity.ok().body(menuitem);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<MenuItem> insert(@Valid @RequestBody MenuItem menuitem){
		menuitem = menuItemService.insert(menuitem);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id/{id}")
				.buildAndExpand(menuitem.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(menuitem);
	}
	
	@RequestMapping(value="id/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody MenuItem menuitem, @PathVariable Integer id){
		menuitem = menuItemService.update(menuitem,id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value="id/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		menuItemService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
