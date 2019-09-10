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

import br.com.meatapp.domain.Orders;
import br.com.meatapp.service.OrderService;

@RestController
@RequestMapping(value="orders")
public class OrderResources {
	
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Orders>> findAll(){
		List<Orders> orders = orderService.findAll();
		
		return ResponseEntity.ok().body(orders);
	}
	
	@RequestMapping(value="id/{id}" , method=RequestMethod.GET)
	public ResponseEntity<Orders> findById(@PathVariable Integer id){
		Orders order = orderService.findById(id);
		
		return ResponseEntity.ok().body(order);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Orders> insert(@Valid @RequestBody Orders order){
		order = orderService.insert(order);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id/{id}")
				.buildAndExpand(order.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(order);
	}
	
	@RequestMapping(value="id/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Orders order, @PathVariable Integer id){
		order = orderService.update(order,id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value="id/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
