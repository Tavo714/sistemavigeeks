package idat.pcds2.grupo3.sistemavigeeks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idat.pcds2.grupo3.sistemavigeeks.models.Client;





@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	@Query(value = "Select*from client where user_id = ?1 ",nativeQuery = true)
	Client findClientbyUser(Long id);

}
