package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.unlp.info.bd2.model.*;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	@Query(value = "FROM Product WHERE name LIKE %:name%")
	public List<Product> findAllByName(@Param("name") String name);
}
