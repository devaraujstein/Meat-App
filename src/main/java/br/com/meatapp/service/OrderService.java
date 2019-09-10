package br.com.meatapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.meatapp.domain.OrderItem;
import br.com.meatapp.domain.Orders;
import br.com.meatapp.repositories.OrderItemRepository;
import br.com.meatapp.repositories.OrderRepository;
import br.com.meatapp.service.exception.DataIntegrityException;
import br.com.meatapp.service.exception.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository ordemItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private MenuItemService menuItemService;
	
	public List<Orders> findAll(){
			return orderRepository.findAll();
	}
	
	public Orders findById(Integer id) {
		Optional<Orders> order = orderRepository.findById(id);
		return order.orElseThrow(() -> 
			 	new ObjectNotFoundException("Pedido não encontrado! ID : " + id));
	}
	
	public Orders insert(Orders order) {
		order.setId(null);
		order.setData(LocalDateTime.now());
		order.setUser(userService.findById(order.getUser().getId()));
		order.setRestaurant(restaurantService.findById(order.getRestaurant().getId()));
		order = orderRepository.save(order);
		
		List<OrderItem> items = order.getOrderItems();
		int item = 1;
		for(OrderItem it : items) {
			it.setOrders(order);
			it.setOrderItemId(item);
			it.setMenuItem(menuItemService.findById(it.getMenuItem().getId()));
			
			ordemItemRepository.save(it);
			item++;
		}
		return order;
	}
	
	public Orders update(Orders order, Integer id) {
		order.setId(id);
		order.setData(LocalDateTime.now());
		order.setUser(userService.findById(order.getUser().getId()));
		order.setRestaurant(restaurantService.findById(order.getRestaurant().getId()));
		order = orderRepository.save(order);
		
		List<OrderItem> items = order.getOrderItems();
		int item = 1;
		for(OrderItem it : items) {
			it.setOrders(order);
			it.setOrderItemId(item);
			it.setMenuItem(menuItemService.findById(it.getMenuItem().getId()));
			
			ordemItemRepository.save(it);
			item++;
		}
		return order;
	}
	
	public void delete(Integer id) {
		this.findById(id);
		try {
			orderRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ocorreu um erro de integridade. Esta ordem ja possui pedidos!");
		}
	}
}
