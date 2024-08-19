package idat.pcds2.grupo3.sistemavigeeks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import idat.pcds2.grupo3.sistemavigeeks.DTO.ClientDTO;
import idat.pcds2.grupo3.sistemavigeeks.models.Client;
import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.services.ClientService;
import idat.pcds2.grupo3.sistemavigeeks.services.UserService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private UserService userService;
	
	@Autowired ClientService clientService;
	
	@GetMapping()
	public String goToClientIndexView(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
		String username = authentication.getName();
		User user =  userService.findByUsername(username);
		if (user == null) {
			return "redirect:/login";
		}
		
		Client cli = clientService.findByUser(user.getId());
		
		ClientDTO cdto = new ClientDTO();
		
		cdto.setApellidos(user.getApellidos());
		cdto.setEmail(cli.getEmail());
		cdto.setEmpresa(cli.getEmpresa());
		cdto.setNombres(user.getNombres());
		cdto.setRuc(cli.getRuc());
		cdto.setTelefono(cli.getTelefono());
		
		model.addAttribute("currentClient", cdto);
		return "client/client";
	}
	
	
	

}
