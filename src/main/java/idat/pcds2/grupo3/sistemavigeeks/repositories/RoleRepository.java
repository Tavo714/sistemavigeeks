package idat.pcds2.grupo3.sistemavigeeks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idat.pcds2.grupo3.sistemavigeeks.models.Role;
import java.util.Set;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Set<Role> findByNombre(String nombre);
	
	
}
