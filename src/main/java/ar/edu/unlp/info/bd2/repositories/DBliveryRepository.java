package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderProduct;
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
        Transaction tx = null;
        Order p;
        
        try {
            tx = session.beginTransaction();
            
            p = new Order(dateOfOrder, address, coordX, coordY, client);
            session.save(p);
            
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }

		
		//session.persist(p);
		//session.close();
		return p;
	}

	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Product p;
        
        try {
            tx = session.beginTransaction();
            
            p = new Product(name, price, weight, supplier);
            session.save(p);
            
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }

		
		//session.persist(p);
		//session.close();
		return p;
	}

	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Supplier s;
        
        try {
            tx = session.beginTransaction();
            
            s = new Supplier(name,cuil,address,coordX,coordY);
            session.save(s);
            
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        
        
        return s;
	}

	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        User u;
		
        
        try {
            tx = session.beginTransaction();
            
            u = new User(email, password,username,name,dateOfBirth);
            session.save(u);
            
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        return u;
	}

	public Optional<User> getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        //Optional<User> u;
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where username = :username ");
        query.setParameter("username", username);
		List<User> users = query.getResultList();
		
        Optional<User> u=Optional.of(users.get(0));
        //if (!users.isEmpty()) {
        //	u=Optional.ofNullable(users.get(0));
        //}
        //Optional<User> u=users.isEmpty() ? null : Optional.ofNullable(users.get(0));
        session.close();
        return u; //(users != null && !users.isEmpty()) ? u : null;
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
        List<Product> products = null;

		@SuppressWarnings("unchecked")

		Query<Product> query = session.createQuery("FROM Product WHERE name LIKE :name", Product.class);
        query.setParameter("name", "%" + name + "%");
		products = query.getResultList();

		session.close();
        return products;
	}

	public Optional <Order> addProductToOrder(Long order, Long quantity, Product product) {
		/*
		Optional <Order> o = this.getOrderById(order);
		Order or = o.get();
		OrderProduct op = new OrderProduct(or, quantity, product);
		
		return o;
		*/
		return null;
	}


}

