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

import br.com.meatapp.domain.Restaurant;
import br.com.meatapp.service.RestaurantService;

@RestController
@RequestMapping(value="restaurants")
public class RestaurantResources {
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> findAll(){
		List<Restaurant> restaurants = restaurantService.findAll();
		
		return ResponseEntity.ok().body(restaurants);
	}
	
	@RequestMapping(value="id/{id}" , method=RequestMethod.GET)
	public ResponseEntity<Restaurant> findById(@PathVariable Integer id){
		Restaurant restaurant = restaurantService.findById(id);
		
		return ResponseEntity.ok().body(restaurant);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Restaurant> insert(@Valid @RequestBody Restaurant restaurant){
		restaurant = restaurantService.insert(restaurant);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id/{id}")
				.buildAndExpand(restaurant.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(restaurant);
	}
	
	@RequestMapping(value="id/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id){
		restaurant = restaurantService.update(restaurant,id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value="id/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		restaurantService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
