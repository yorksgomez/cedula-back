package eci.escuelaing.cedula2;

import com.fasterxml.jackson.databind.ObjectMapper;

import eci.escuelaing.cedula2.controller.UserController;
import eci.escuelaing.cedula2.model.User;
import eci.escuelaing.cedula2.model.dto.JwtResponseDTO;
import eci.escuelaing.cedula2.model.dto.SaltDTO;
import eci.escuelaing.cedula2.repository.HistoryRepository;
import eci.escuelaing.cedula2.repository.QRRepository;
import eci.escuelaing.cedula2.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QRRepository qrRepository;
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        qrRepository.deleteAll();
        historyRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User userDTO = User.builder().username("testuser").password("password").build();

        mockMvc.perform(post("/noauth/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created correctly"));
    }

    @Test
    public void shouldNotCreateDuplicatedUsers() throws Exception {
        User userDTO = User.builder().username("testuser").password("password").build();

        mockMvc.perform(post("/noauth/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created correctly"));

        mockMvc.perform(post("/noauth/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User already exists"));
    }

    @Test
    public void shouldShowHistoryWithQR() throws Exception {
        User userDTO = User.builder().username("testuser").password("password").build();
        mockMvc.perform(post("/noauth/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created correctly"));

        MvcResult userResult = mockMvc.perform(post("/noauth/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk()).andReturn();

        String responseContent = userResult.getResponse().getContentAsString();
        JwtResponseDTO jwtRes = objectMapper.readValue(responseContent, JwtResponseDTO.class);

        String jwt = jwtRes.getJwtToken();

        MvcResult saltResult  = mockMvc.perform(get("/qr/salt").header("Authorization", "Bearer " + jwt).accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();

        responseContent = saltResult.getResponse().getContentAsString();
        SaltDTO saltRes = objectMapper.readValue(responseContent, SaltDTO.class);
        mockMvc.perform(get("/noauth/history/testuser/" + saltRes.getSalt())).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.information").value("")).
                andExpect(jsonPath("$.diabetes").value(false));
    }

}