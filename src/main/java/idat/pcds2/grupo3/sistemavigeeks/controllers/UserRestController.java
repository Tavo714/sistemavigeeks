package idat.pcds2.grupo3.sistemavigeeks.controllers;

import org.springframework.web.bind.annotation.RestController;

import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    
    @PostMapping()
    public User insert(@RequestBody User entity) throws Exception {
        return userService.insert(entity);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User entity) {
        entity.setId(id);
        return userService.update(entity);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean hasDeleted = userService.delete(id);
        return hasDeleted ? "El usuario ha sido eliminado correctamente" : "Ocurrio un problema al eliminar el usuario";
    }

}

