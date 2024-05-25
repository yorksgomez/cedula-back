package eci.escuelaing.cedula2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eci.escuelaing.cedula2.model.History;

public interface HistoryRepository extends MongoRepository<History, String> {
    public History findByUsername(String username);
}
