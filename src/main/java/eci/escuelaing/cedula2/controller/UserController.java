package eci.escuelaing.cedula2.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eci.escuelaing.cedula2.configuration.JwtConfig;
import eci.escuelaing.cedula2.model.History;
import eci.escuelaing.cedula2.model.QR;
import eci.escuelaing.cedula2.model.User;
import eci.escuelaing.cedula2.model.dto.JwtRequestDTO;
import eci.escuelaing.cedula2.model.dto.JwtResponseDTO;
import eci.escuelaing.cedula2.model.dto.MessageDTO;
import eci.escuelaing.cedula2.model.dto.RegisterDTO;
import eci.escuelaing.cedula2.repository.HistoryRepository;
import eci.escuelaing.cedula2.repository.QRRepository;
import eci.escuelaing.cedula2.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtConfig jwt;
    @Autowired
    private UserRepository users;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private QRRepository qrs;
    @Autowired
    private HistoryRepository histories;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/noauth/hola")
    public String hola() {
        return "Hola mundo";
    }

    @RequestMapping("/hola-protegido")
    public String holaProtegido() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hola mundo " + user.getUsername();
    }

    @PostMapping("/noauth/user/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwt.generateToken(userDetails);
        JwtResponseDTO response = JwtResponseDTO.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/noauth/user/register")
    public ResponseEntity<MessageDTO> register(@RequestBody RegisterDTO request) {

        if(users.existsByUsername(request.getUsername()))
            return new ResponseEntity<>(MessageDTO.builder().message("User already exists").build(), HttpStatus.BAD_REQUEST);

        User user = User.builder().username(request.getUsername()).password(encoder.encode(request.getPassword())).build();
        users.insert(user);

        //When creating an user it must create his medecal history and his qr associated
        History history = History.builder().cardiacIssues(false).diabetes(false).hypertension(false).information("").username(request.getUsername()).build();
        QR qr = QR.builder().salt(UtilController.generateSecureRandomPassword()).username(request.getUsername()).build();

        histories.insert(history);
        qrs.insert(qr);

        MessageDTO message = MessageDTO.builder().message("User created correctly").build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
