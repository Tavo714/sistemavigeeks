package idat.pcds2.grupo3.sistemavigeeks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idat.pcds2.grupo3.sistemavigeeks.models.Category;


@Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
