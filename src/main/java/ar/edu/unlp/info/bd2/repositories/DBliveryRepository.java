package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

	public Optional<User> getUserByEmail(String email) {
		//Query query = session.createQuery("from User where email = :email ");
		//query.setParameter("email", email);
		//Optional<User> u=Optional.ofNullable(session.get(User.class,id));
		//return null;
		
        @SuppressWarnings("rawtypes")
		Query query = session.createQuery("from User where email = :email ");
        query.setParameter("email", email);
        @SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
        Optional<User> u=Optional.ofNullable(users.get(0));
        return (users != null && !users.isEmpty()) ? u : null;
		
	}
}

