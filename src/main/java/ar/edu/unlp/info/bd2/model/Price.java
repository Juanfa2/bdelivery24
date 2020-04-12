package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

@Entity
@Table(name="prices")
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="price")
	private Float price;
	
	@Column(name="startDate")
	private Date startDate;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product products;
	
	
	public Price() {
		
	}

	public Price (Product product, Float price, Date startDate) {
		this.setPrice(price);
		this.setStartDate(startDate);
		this.setProduct(product);
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Float getPrice() {
		return this.price;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Long getId() {
		return this.id;
	}
	public Product getProduct() {
		return products;
	}

	public void setProduct(Product product) {
		this.products = product;
	}
	
}
