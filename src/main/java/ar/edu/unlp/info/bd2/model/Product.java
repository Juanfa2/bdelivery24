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
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Price> prices = new ArrayList<>();
	
	

	@OneToMany(  mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderProduct> orderProduct = new ArrayList<>();
	
	@Column(name="price_actual")
	private Float price ;
	
	@Column(name="weight")
	private Float weight; 
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier")
	private Supplier supplier;
	
	
	
	
	public Product() {
		
	}

	public Product (String name, Float price, Float weight, Supplier supplier) {
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public String getName() {
		return this.name;
	}
	public Float getPrice() {
		return this.price;
	}
	public List<Price> getPrices(){
		return this.prices;
	}
	
	public Float getWeight() {
		return this.weight;
	}
	public Supplier getSupplier() {
		return this.supplier;
	}
	public Long getId(){
		return this.id;
	}
	public List<OrderProduct> getOrderProduct() {
		return this.orderProduct;
	}

	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
}
