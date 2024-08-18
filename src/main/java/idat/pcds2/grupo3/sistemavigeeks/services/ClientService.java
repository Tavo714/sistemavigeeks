package idat.pcds2.grupo3.sistemavigeeks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.DTO.ClientDTO;
import idat.pcds2.grupo3.sistemavigeeks.models.Client;
import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserService userService;
	
	public Client findByUser(Long id) {
		
		Client c = clientRepository.findClientbyUser(id);
		
		return c;
	}
	
	public Client updateClient(ClientDTO clientdto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    int num =0;
		String username = authentication.getName();
		User user =  userService.findByUsername(username);
		if (user == null) {
			return null;
		}
		if (!user.getNombres().equals(clientdto.getNombres())) {
			user.setNombres(clientdto.getNombres());
			num += 1;
		}
		if (!user.getApellidos().equals(clientdto.getApellidos())) {
			user.setApellidos(clientdto.getApellidos());
			num += 1;
		}
		if (num >0) {
			user = userService.update(user);
		}
		num = 0;
		Client c = clientRepository.findClientbyUser(user.getId());
		
		if (!c.getTelefono().equals(clientdto.getTelefono())) {
			c.setTelefono(clientdto.getTelefono());
			num +=1;
		}
		if (num >0) {
			c = clientRepository.save(c);
		}
		
		return c;
	}
	
	

}
