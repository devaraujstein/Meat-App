package br.com.meatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.meatapp.domain.MenuItem;
import br.com.meatapp.repositories.MenuItemRepository;
import br.com.meatapp.service.exception.DataIntegrityException;
import br.com.meatapp.service.exception.ObjectNotFoundException;

@Service
public class MenuItemService {
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	public List<MenuItem> findAll(){
		return menuItemRepository.findAll();
	}
	
	public MenuItem findById(Integer id) {
		Optional<MenuItem> menuitem = menuItemRepository.findById(id);
		return menuitem.orElseThrow(() -> 
			new ObjectNotFoundException("Item de menu não encontrado ! ID :" + id)) ;
	}
	

	public MenuItem insert(MenuItem menuitem) {
		menuitem.setId(null);
		return menuItemRepository.save(menuitem);
	}
	
	public MenuItem update(MenuItem menuitem, Integer id) {
		menuitem.setId(id);
		return menuItemRepository.save(menuitem);
	}
	
	public void delete(Integer id) {
		this.findById(id);
		try {
			menuItemRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ocorreu um erro de integridade. Este Usuário ja possui pedidos!");
		}
	}
}
