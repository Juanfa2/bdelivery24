package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

@Entity
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cuantity")
	private Long cuantity;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product producto;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order orderP;
	
	public OrderProduct() {
		
	}
	
	public OrderProduct(Order order, Long cuantity, Product product) {
		this.setOrder(order);
		this.setCuantity(cuantity);
		this.setProduct(product);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCuantity() {
		return cuantity;
	}

	public void setCuantity(Long cuantity) {
		this.cuantity = cuantity;
	}

	public Product getProduct() {
		return producto;
	}

	public void setProduct(Product product) {
		this.producto = product;
	}

	public Order getOrder() {
		return orderP;
	}

	public void setOrder(Order order) {
		this.orderP = order;
	}
	
	
}
