package eci.escuelaing.cedula2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eci.escuelaing.cedula2.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
