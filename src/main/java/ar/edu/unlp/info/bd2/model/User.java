package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;


@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="dateOfBirth")
	private Date dateOfBirth;
	
	public User() {
		
	}
	
	public User (String email, String password, String username, String name, Date dateOfBirth) {
		this.setEmail(email);
		this.setPassword(password);
		this.setUsername(username);
		this.setName(name);
		this.setDateOfBirth(dateOfBirth);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getUsername () {
		return this.username;
	}
	public String getEmail () {
		return this.email;
	}
	public String getPassword () {
		return this.password;
	}
	public String getName () {
		return this.name;
	}
	public Date getDateOfBirth () {
		return this.dateOfBirth;
	}
	
	public Long getId() {
		return this.id;
	}
	
	
}
