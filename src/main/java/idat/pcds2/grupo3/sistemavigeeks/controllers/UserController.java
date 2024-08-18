package idat.pcds2.grupo3.sistemavigeeks.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idat.pcds2.grupo3.sistemavigeeks.DTO.ClientDTO;
import idat.pcds2.grupo3.sistemavigeeks.models.Role;
import idat.pcds2.grupo3.sistemavigeeks.models.User;
import idat.pcds2.grupo3.sistemavigeeks.services.ClientService;
import idat.pcds2.grupo3.sistemavigeeks.services.CustomeFieldValidationException;
import idat.pcds2.grupo3.sistemavigeeks.services.RoleService;
import idat.pcds2.grupo3.sistemavigeeks.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private RoleService roleService;
	
	
    private UserService userService;
    
    @Autowired
    private ClientService clientService;
    
    
    private User userCreated;
    private User userModified;
    private boolean userDeleted;

    public UserController(UserService userService){
        this.userService = userService;
        userService = null;
        userModified = null;
        userDeleted = false;
    }

    @GetMapping()
    public String goToUserIndexView(Model model) {
        model.addAttribute("title", "Usuarios");
        model.addAttribute("headers", populateHeaders());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("userHasCreated", userCreated != null);
        model.addAttribute("userHasModified", userModified != null);
        model.addAttribute("userHasDeleted", userDeleted);

        String userCreatedMessage = userCreated != null ? "El usuario " +userCreated.getNombres() + " "+ userCreated.getApellidos()+" ha sido registrado correctamente con codigo " + userCreated.getId() : "";
        model.addAttribute("userCreatedMessage", userCreatedMessage);

        String userModifiedMessage = userModified != null ? "Los datos del usuario " +userModified.getNombres() + " "+ userModified.getApellidos()+" han sido actualizados correctamente" : "";
        model.addAttribute("userModifiedMessage", userModifiedMessage);

        userCreated = null;
        userModified = null;
        userDeleted = false;
        return "user/user";
    }

    @GetMapping("/new")
    public String goToUserCreateView(Model model, User user) {
    	
    	List<Role> roles = roleService.gestAll();
    	
        model.addAttribute("title", "Nuevo Usuario");
        model.addAttribute("roles", roles);
        model.addAttribute("currentUser", user);
        return "user/user-create";
    }
    
    @GetMapping("/newclient")
    public String goToClientCreateView(Model model, User user) {
    	
    	
        model.addAttribute("title", "Nuevo Cliente");
        model.addAttribute("client", new ClientDTO());
        return "login/login-create-cliente";
    }

    @PostMapping("/save")
    public String save( @ModelAttribute("currentUser") User entity,BindingResult result, Model model, RedirectAttributes redirectAttributes) {
    	
    	try {
    		userCreated = userService.insert(entity);
    	} catch (CustomeFieldValidationException cfve) {
    		List<Role> roles = roleService.gestAll();
        	
            model.addAttribute("title", "Nuevo Usuario");
            model.addAttribute("roles", roles);
            model.addAttribute("currentUser", entity);
    		result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
    		redirectAttributes.addFlashAttribute("error", "No Registrado");
    		System.out.println("validacion usuario");
            return "user/user-create";
    	}catch (Exception e) {
    		redirectAttributes.addFlashAttribute("error", "No Registrado");
    		System.out.println("error sin saber"+ e.getMessage());
    		return "user/user-create";
    	}
    	redirectAttributes.addFlashAttribute("success", "Registrado");
    	model.addAttribute("userCreated", userCreated);
        return "redirect:/users";
    }

    @PostMapping("/saveclient")
    public String saveClient( @ModelAttribute("client") ClientDTO entity,BindingResult result, Model model, RedirectAttributes redirectAttributes) {
    	
    	try {
    		userCreated = userService.insertClient(entity);
    		redirectAttributes.addFlashAttribute("success", "Registrado");
        	model.addAttribute("userCreated", userCreated);
        	return "redirect:/login";
    	} catch (CustomeFieldValidationException cfve) {
        	
            model.addAttribute("title", "Nuevo Usuario");
            model.addAttribute("currentUser", new ClientDTO());
            result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
            return "login/login-create-cliente";
    	}catch (Exception e) {
    		System.out.println("error sin saber");
    		redirectAttributes.addFlashAttribute("error", "No Registrado");
            return "redirect:/newclient"; 
    	}
    	
    }
    
    @PostMapping("/cli/updatecli")
    @ResponseBody
    public String saveClientAct(@RequestBody ClientDTO entity, Model model) {
    	
    	clientService.updateClient(entity);
    	
    	
    	return "hecho";
    }
    
    
    @GetMapping("/edit/{id}")
    public String goToUserEditView(@PathVariable Long id, Model model) {
        User toUpdate = userService.getById(id);
        if(toUpdate == null) return "redirect:/users";

        model.addAttribute("title", "Editar Usuario");
        model.addAttribute("currentUser", toUpdate);
        return "user/user-edit";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute User entity, Model model) {
        userModified = userService.update(entity);
        model.addAttribute("userModified", userModified);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userDeleted = userService.delete(id);
        if(!userDeleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok("El usuario ha sido eliminado correctamente");
    }
    

    private List<String> populateHeaders(){
        List<String> headers = new ArrayList<>();
        headers.add("Id");
        headers.add("Nombres");
        headers.add("Apellidos");
        headers.add("Password");
        headers.add("Activo");

        headers.add("Acciones");

        return headers;
    }
    

}
