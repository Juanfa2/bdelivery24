package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Entity
@Table(name="suppliers")
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cuil")
	private String cuil;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	public Supplier() {		
	}
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		this.setName(name);
		this.setCuil(cuil);
		this.setAddress(address);
		this.setCoordY(coordY);
		this.setCoordX(coordX);
	}
	
	public void setName (String name) {
		this.name = name;
	}
	public void setCuil (String cuil) {
		this.cuil = cuil;
	}
	public void setAddress (String address) {
		this.address = address;
	}
	public void setCoordX (Float coordX) {
		this.coordX = coordX;
	}
	public void setCoordY (Float coordY) {
		this.coordY = coordY;
	}
	
	public Long getId() {
		return this.id; 
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getCuil() {
		return this.cuil;
	}
	public Float getCoordX() {
		return this.coordX;
	}
	public Float getCoordY() {
		return this.coordY;
	}
}
