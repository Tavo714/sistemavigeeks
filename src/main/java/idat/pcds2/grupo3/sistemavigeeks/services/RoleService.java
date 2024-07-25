package idat.pcds2.grupo3.sistemavigeeks.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.Role;
import idat.pcds2.grupo3.sistemavigeeks.repositories.RoleRepository;

@Service
public class RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void createRolesIfNotExist() {
        if (roleRepository.count() == 0) {
        	Role role = new Role();
            role.setNombre("ROLE_ADMIN");
            roleRepository.save(role);
            role = new Role();
            role.setNombre("ROLE_CLIENTE");
            roleRepository.save(role);
        }
    }
	
	
	public List<Role> gestAll(){
		return roleRepository.findAll();
	}
	
	public Role getbyId(Long id){
		return roleRepository.findById(id).orElse(null);
	}
	
	
	

}
