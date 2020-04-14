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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class OrderStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	
	@Column(name = "status")
	protected String status;

	@Column(name="startDate")
	protected Date startDate;
	
	@ManyToOne
	@JoinColumn(name= "order_id")
	protected Order order;
	
	public OrderStatus() {
		
	}
	
	public OrderStatus(Order order, String status) {
		Calendar cal = Calendar.getInstance();
		Date startDate = cal.getTime();
		this.setOrder(order);
		this.setStartDate(startDate);
		this.setStatus(status);
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

	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	
	public Long getId() {
		return this.id;
	}

	abstract void entregarOrder();
	abstract void cancelarOrder();
	abstract void enviarOrder();

}
