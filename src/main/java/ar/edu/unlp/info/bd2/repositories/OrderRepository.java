package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.unlp.info.bd2.model.*;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	
	public List<Order> findByActualStatusStatus(String status);
	
	public List<Order> findByClientUsername(String username);
	
	
	/*
	@Query(value = "FROM Order o WHERE o.actualStatus.status = 'Delivered' AND o.actualStatus.startDate BETWEEN :start AND :end ")
	public List<Order> getDeliveredOrdersInPeriod(@Param("start")Date start, @Param("end") Date end);
	*/
	public List<Order> findByActualStatusStatusAndActualStatusStartDateBetween(String status, Date start, Date end);
}
