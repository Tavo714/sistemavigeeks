package idat.pcds2.grupo3.sistemavigeeks.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import idat.pcds2.grupo3.sistemavigeeks.models.Role;
import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private RoleService roleService;
	
	private UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void createAdminUserIfNotExist() {
        if (userRepository.count() == 0) {
        	Role role = roleService.getbyId(1L);
            User admin = new User();
            admin.setNombres("Jhon");
            admin.setNombres("Alvarez");
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setRoles(Collections.singleton(role));
            userRepository.save(admin);
        }
    }
    
    private boolean checkUsernameAvailable(User user) throws Exception {
		User userFound = userRepository.findByUsername(user.getUsername().trim());
		if (userFound != null) {
			throw new CustomeFieldValidationException("Username no disponible","username");
		}
		return true;
	}

    public User insert(User entity) throws Exception {
    	if(checkUsernameAvailable(entity)) {
    		String encodedPassword = bCryptPasswordEncoder.encode(entity.getPassword());
    		entity.setPassword(encodedPassword);
    		entity = userRepository.save(entity);
    	}
    	
    	
        return userRepository.saveAndFlush(entity);
    }

    public User update(User entity){
        Optional<User> response = userRepository.findById(entity.getId());
        if(!response.isPresent()) {
            return null;
        }
        User toUpdate = response.get();
        toUpdate.setNombres(entity.getNombres());
        toUpdate.setApellidos(entity.getApellidos());
        toUpdate.setPassword(entity.getPassword());
        
        return userRepository.saveAndFlush(entity);
    }

    public boolean delete(Long id){
        userRepository.deleteById(id);
        return true;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(Long id){
        Optional<User> response = userRepository.findById(id);
        if(!response.isPresent()){
            return null;
        }
        return response.get();
    }


}
