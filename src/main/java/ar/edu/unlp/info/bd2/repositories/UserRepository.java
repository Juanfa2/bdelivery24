package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.*;

public interface UserRepository extends CrudRepository<User, Long>{
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
}
