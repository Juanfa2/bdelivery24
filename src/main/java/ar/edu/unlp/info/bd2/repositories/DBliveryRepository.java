package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;


public class DBliveryRepository{
	
	@Autowired
	private SessionFactory sessionFactory;


    public Optional<User> getUserById(long id) {
        Session session = sessionFactory.openSession();
    	Optional<User> u=Optional.ofNullable(session.get(User.class,id));
    	session.close();
    	return u;
    }

	public Optional<User> getUserByEmail(String email) {
        Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where email = :email ");
        query.setParameter("email", email);
		List<User> users = query.getResultList();
        Optional<User> u=Optional.ofNullable(users.get(0));
        session.close();
        return (users != null && !users.isEmpty()) ? u : null;
		
	}

	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        Session session = sessionFactory.openSession();

		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		session.persist(o);
		session.close();
		return o;
	}

	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Session session = sessionFactory.openSession();

		Product p = new Product(name, price, weight, supplier);
		session.persist(p);
		session.close();
		return p;
	}

	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Session session = sessionFactory.openSession();

		Supplier s = new Supplier(name,cuil,address,coordX,coordY);
        session.persist(s);
        session.close();
        return s;
	}

	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        Session session = sessionFactory.openSession();

		User u = new User(email, password,username,name,dateOfBirth);
        session.persist(u);
        session.close();
        return u;
	}

	public Optional<User> getUserByUsername(String username) {
        Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where username = :username ");
        query.setParameter("username", username);
		List<User> users = query.getResultList();
        Optional<User> u=Optional.ofNullable(users.get(0));
        session.close();
        return (users != null && !users.isEmpty()) ? u : null;
	}

	public Optional<Product> getProductById(Long id) {
        Session session = sessionFactory.openSession();

		Optional<Product> p=Optional.ofNullable(session.get(Product.class,id));
		session.close();
		return p;
	}

	public Optional<Order> getOrderById(Long id) {
        Session session = sessionFactory.openSession();

		Optional<Order> o=Optional.ofNullable(session.get(Order.class,id));
		session.close();
		return o;
	}

	public List<Product> getProductByName(String name) {
        Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Query<Product> query = session.createQuery("from Product p where  p.name like concat( '%',:name,'%')");
        query.setParameter("name",  name );
		List<Product> products = query.getResultList();
        //Optional<Product> u=Optional.ofNullable(products.get(0));
		session.close();
        return products;
	}


}

