package br.com.meatapp.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meatapp.domain.MenuItem;
import br.com.meatapp.domain.OrderItem;
import br.com.meatapp.domain.OrderItemPK;
import br.com.meatapp.domain.Orders;
import br.com.meatapp.domain.Restaurant;
import br.com.meatapp.domain.User;
import br.com.meatapp.repositories.MenuItemRepository;
import br.com.meatapp.repositories.OrderItemRepository;
import br.com.meatapp.repositories.OrderRepository;
import br.com.meatapp.repositories.RestaurantRepository;
import br.com.meatapp.repositories.UserRepository;

@Service
public class DBService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public void instanciaDatabase() {
		
		User user1 = new User(1,"Andre","andremelo123@gmail.com","123");
		User user2 = new User(2,"Saulo","saulogrego@gmail.com","123");
		userRepository.saveAll(Arrays.asList(user1, user2));
		
		Restaurant r1 = new Restaurant(1,"Tasty Treats","Padaria", "40",4.2,"/images/padaria.png","1 hora","Muito Bom");
		restaurantRepository.saveAll(Arrays.asList(r1));
		
		MenuItem m1 = new MenuItem(1,"Cup Cake","Cup Cake recheado de leuite condensado",5.50,"/images/cupcake.png",r1);
		MenuItem m2 = new MenuItem(1,"Sorvete de Doce de Leite","Sorvete de leuite condensado",4.05,"/images/sorvete.png",r1);
		MenuItem m3 = new MenuItem(1,"Cachorro","Cachorro Quente Gourmet",2.50,"/images/cachorro.png",r1);
		menuItemRepository.saveAll(Arrays.asList(m1,m2,m3));
		
		Orders o1 = new Orders(1,LocalDateTime.now(), user1,r1,"Rua Bagaceira Doida","25",null,"Money Bitch");
		Orders o2 = new Orders(2,LocalDateTime.now(), user2,r1,"Rua Lokão Doida do Cão","25",null,"Cartão de Credito");
		orderRepository.saveAll(Arrays.asList(o1,o2));
		
		OrderItem oi1 = new OrderItem(new OrderItemPK(o1,1),1,4.5,m2);
		OrderItem oi2 = new OrderItem(new OrderItemPK(o2,1),1,4.5,m2);
		OrderItem oi3 = new OrderItem(new OrderItemPK(o1,1),1,4.5,m2);
		orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3));
	
	}
}
