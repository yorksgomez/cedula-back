package eci.escuelaing.cedula2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eci.escuelaing.cedula2.model.QR;

public interface QRRepository extends MongoRepository<QR, String> {
    public QR findByUsername(String userId);
}
