package ar.edu.unlp.info.bd2.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "cuantity")
	private Long cuantity;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	public OrderProduct() {
		
	}
	
	public OrderProduct(Order order, Long quantity, Product product) {
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
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
