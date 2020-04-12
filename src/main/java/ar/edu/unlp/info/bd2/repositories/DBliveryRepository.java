package ar.edu.unlp.info.bd2.repositories;

import java.util.Calendar;
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
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;


public class DBliveryRepository{
	
	@Autowired
	private SessionFactory sessionFactory;


    public Optional<User> getUserById(long id) {

        String queryStr = "FROM User WHERE id = :id";
        Query<User> query=this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<User> u= query.uniqueResultOptional();

    	return u;
    }

	public Optional<User> getUserByEmail(String email) {

        String queryStr = "from User where email = :email";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("email", email);
        Optional<User> u=query.uniqueResultOptional();
        return u;
    }

	public Optional<User> getUserByUsername(String username) {

        String queryStr = "from User where username = :username";
        Query<User> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("username", username);
        Optional<User>  u = query.uniqueResultOptional();

        return u;
	}

	public Optional<Product> getProductById(Long id) {

        String queryStr = "FROM Product WHERE id Like :id";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<Product> p = query.uniqueResultOptional();

        return p;
    }

	public Optional<Order> getOrderById(Long id) {

        String queryStr = "FROM Order WHERE id Like :id";
        Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("id", id);
        Optional<Order> o = query.uniqueResultOptional();

		return o;
	}

	public List<Product> getProductByName(String name) {

        String queryStr = "FROM Product WHERE name LIKE :name";
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("name", "%" + name + "%");
        List<Product> products = query.getResultList();

        return products;
	}


    public void save(Object object) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.save(object);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Object object) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.update(object);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

/*    public String getCurrentStatus(Long order) {
        //List<Product> products = null;
        String queryStr = "FROM orderStatus WHERE order_id LIKE :order"
        Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameter("order", order);
        status = query.getResultList();

        return status;



    }*/
}

