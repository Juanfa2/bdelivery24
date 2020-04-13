package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/*import java.util.Optional;
import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;*/

@Entity
@Table(name = "orderStatus")
public class OrderStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status")
	private String status;

	@Column(name="startDate")
	private Date startDate;
	
	@ManyToOne
	@JoinColumn(name= "order_id")
	private Order order;
	
	public OrderStatus() {
		
	}
	
	public OrderStatus(Order order, String status) {
		Calendar cal = Calendar.getInstance();
		Date startDate = cal.getTime();

		this.setOrder(order);
		this.setStatus(status);
		this.setStartDate(startDate);
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	public OrderStatus(String status) {
		this.setStatus(status);
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public Long getId() {
		return this.id;
	}
}
