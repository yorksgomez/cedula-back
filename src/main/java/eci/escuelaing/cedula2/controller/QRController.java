package eci.escuelaing.cedula2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eci.escuelaing.cedula2.model.dto.SaltDTO;
import eci.escuelaing.cedula2.repository.QRRepository;

@RestController
public class QRController {

    @Autowired
    private QRRepository qrs;

    @GetMapping("/qr/salt")
    public ResponseEntity<SaltDTO> salt() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String salt = qrs.findByUsername(user.getUsername()).getSalt();
        SaltDTO result = SaltDTO.builder().salt(salt).build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
