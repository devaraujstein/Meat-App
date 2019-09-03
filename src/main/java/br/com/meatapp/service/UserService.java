package br.com.meatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.meatapp.domain.User;
import br.com.meatapp.repositories.UserRepository;
import br.com.meatapp.service.exception.DataIntegrityException;
import br.com.meatapp.service.exception.ObjectNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> 
			new ObjectNotFoundException("Usuario não encontrado ! ID :" + id)) ;
	}
	
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> 
				new ObjectNotFoundException("Email não encontrado !"));
	}
	
	public User insert(User user) {
		user.setId(null);
		return userRepository.save(user);
	}
	
	public User update(User user, Integer id) {
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void delete(Integer id) {
		this.findById(id);
		try {
			userRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ocorreu um erro de integridade. Este Usuário ja possui pedidos!");
		}
		
		
	}
}
