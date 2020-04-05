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
@Table(name="Products")
public class Product {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Price> prices = new ArrayList<>();
	
	@Column(name="price_actual")
	private Float price ;
	
	@Column(name="weight")
	private Float weight; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToMany(mappedBy = "products")
	private List<Order> orders = new ArrayList<Order>();
	
	
	

	public Product (String name, Float price, Float weight, Supplier supplier) {
		this.setName(name);
		Calendar cal = Calendar.getInstance();
    	Date startDate = cal.getTime();
		this.setPrice(price,startDate);
		this.setWeight(weight);
		this.setSupplier(supplier);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Float price, Date startDate) {
		Price pr = new Price(price, startDate);
		this.prices.add(pr);
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
		return this.getId();
	}
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
