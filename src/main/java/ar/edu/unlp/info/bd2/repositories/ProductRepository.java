package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.unlp.info.bd2.model.*;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	public List<Product> findByNameContaining(String name);

	@Query(value = "SELECT op.producto " +
			"FROM OrderStatus os , OrderProduct op "+ 
			"WHERE os.order = op.orderP AND " +
			"os.startDate = :day AND os.status = Pending")
	public List<Product> findSoldOn(@Param("day") Date day);

	public Product findFirstByOrderByWeightDesc();

	@Query(value = "select p.products from Price p  group by p.products having count (p.products) = 1")
	List<Product> findByOnlyOnePrice();
}
