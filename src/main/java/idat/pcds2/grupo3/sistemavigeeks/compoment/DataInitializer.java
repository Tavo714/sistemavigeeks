package idat.pcds2.grupo3.sistemavigeeks.compoment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import idat.pcds2.grupo3.sistemavigeeks.services.RoleService;
import idat.pcds2.grupo3.sistemavigeeks.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
    	roleService.createRolesIfNotExist();
        userService.createAdminUserIfNotExist();
        
    }
}