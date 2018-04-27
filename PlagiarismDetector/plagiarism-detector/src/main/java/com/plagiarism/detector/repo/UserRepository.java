package com.plagiarism.detector.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.plagiarism.detector.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
/**
 * @author Monica interface for crud operations
 */
@Component
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * @param name
	 * @param password
	 * @param id
	 * @return find user by name, password or id
	 */
	List<User> findByNameAndPassword(String name, String password);

	List<User> findByName(String name);

	User findById(Integer id);

}