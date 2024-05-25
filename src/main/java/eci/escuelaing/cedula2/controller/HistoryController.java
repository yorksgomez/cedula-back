package eci.escuelaing.cedula2.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eci.escuelaing.cedula2.model.History;
import eci.escuelaing.cedula2.model.QR;
import eci.escuelaing.cedula2.model.dto.HistoryUpdateDTO;
import eci.escuelaing.cedula2.model.dto.MessageDTO;
import eci.escuelaing.cedula2.repository.HistoryRepository;
import eci.escuelaing.cedula2.repository.QRRepository;

@RestController
public class HistoryController {
    @Autowired
    private HistoryRepository histories;
    @Autowired
    private QRRepository qrs;

    @GetMapping("/history/mine")
    public ResponseEntity<History> mine() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        History history = histories.findByUsername(user.getUsername());
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/noauth/history/{username}/{salt}")
    public ResponseEntity<Optional<History>> qrHistory(@PathVariable String username, @PathVariable String salt) {
        QR qr = qrs.findByUsername(username);

        if(qr.getSalt().equals(salt)) {
            History history = histories.findByUsername(username);
            return new ResponseEntity<>(Optional.of(history), HttpStatus.OK);
        }

        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/history")
    public ResponseEntity<MessageDTO> updateMine(@RequestBody HistoryUpdateDTO request) {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        History history = histories.findByUsername(user.getUsername());
        history.setCardiacIssues(request.isCardiacIssues());
        history.setDiabetes(request.isDiabetes());
        history.setHypertension(request.isHypertension());
        history.setInformation(request.getInformation());
        histories.save(history);
        MessageDTO message = MessageDTO.builder().message("History updated correctly").build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
