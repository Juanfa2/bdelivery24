package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

@Entity
@Table(name="Products")
public class Product {
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private Float price;
	
	@Column(name="weight")
	private Float weight; 
	
	private Supplier supplier;
	
	@Column(name="supplier_id")
	private Long supplierId;
	
	public Product (String name, Float price, Float weight, Supplier supplier) {
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.setSupplierID(this.supplier.getId());
	}
	public void setSupplierID(Long id) {
		this.supplierId = id;
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
	public Float getWeight() {
		return this.weight;
	}
	public Supplier getSupplier() {
		return this.supplier;
	}
	public Long getId(){
		return this.getId();
	}
}