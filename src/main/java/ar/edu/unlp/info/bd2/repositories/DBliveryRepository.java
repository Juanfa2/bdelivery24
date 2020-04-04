package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.User;


public class DBliveryRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

    private Session session = sessionFactory.openSession();

    public Optional<User> getUserById(long id) {
    	Optional<User> u=Optional.ofNullable(session.get(User.class,id));
    	return u;
    	
    }
}

