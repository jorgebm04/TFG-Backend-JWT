package com.jorgetfg.backend.integrationTests;

import com.jorgetfg.backend.repositories.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserIntegrationTests {

    @Autowired
    private IUserRepository userRepository;

    //Permite llamadas http
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ServiceConnection
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.2");

    static {
        mySQLContainer.withUrlParam("serverTimezone", "UTC")
                .withReuse(true)
                .start();
    }

    public void setUp() throws  Exception {
        userRepository.deleteAll();
    }

    public void createUser() throws Exception {
        String url = "/users";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {"name":"admin",
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":"1q2w3E**",
                                "messages":false
                                }"""));
    }

    public String setUpToken() throws Exception {
        createUser();
        String url = "/token";
        String response = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"admin@email.com",
                                "password":"1q2w3E**"
                                }"""))
                .andReturn().getResponse().getContentAsString();
        String[] dividedResponse = response.split("\"");
        String token = dividedResponse[9];
        return token;
    }

    //CREATE TESTS
    @Test
    public void shouldNotCreateWithoutName() throws Exception {
        setUp();
        String url = "/users";
        Assertions.assertThat(userRepository.findAll()).isEmpty();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": null,
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":"1q2w3E**",
                                "messages":false
                                }"""))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(userRepository.findAll().stream().toList().size()).isEqualTo(0);
    }

    @Test
    public void shouldNotCreateWithoutEmail() throws Exception {
        setUp();
        String url = "/users";
        Assertions.assertThat(userRepository.findAll()).isEmpty();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "admin",
                                "surname":"root",
                                "email":null,
                                "password":"1q2w3E**",
                                "messages":false
                                }"""))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(userRepository.findAll().stream().toList().size()).isEqualTo(0);
    }

    @Test
    public void shouldNotCreateWithoutPassword() throws Exception {
        setUp();
        String url = "/users";
        Assertions.assertThat(userRepository.findAll()).isEmpty();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "admin",
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":null,
                                "messages":false
                                }"""))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(userRepository.findAll().stream().toList().size()).isEqualTo(0);
    }

    @Test
    public void shouldNotCreateWithoutMessages() throws Exception {
        setUp();
        String url = "/users";
        Assertions.assertThat(userRepository.findAll()).isEmpty();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "admin",
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":"1q2w3E**",
                                "messages":null
                                }"""))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(userRepository.findAll().stream().toList().size()).isEqualTo(0);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        setUp();
        String url = "/users";
        Assertions.assertThat(userRepository.findAll()).isEmpty();
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"admin",
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":"1q2w3E**",
                                "messages":false
                                }"""))
                .andExpect(status().isCreated());
        Assertions.assertThat(userRepository.findByEmail("admin@email.com")).isNotNull();
        Assertions.assertThat(userRepository.findAll().stream().toList().size()).isEqualTo(1);

    }

    // UPDATE TESTS
    @Test
    public void shouldNotUpdateWithInvalidName() throws Exception {
        String token = setUpToken();
        String url = "/user/1";
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":null,
                                "surname":"root",
                                "email":"admin@email.com",
                                "password":"1q2w3E**",
                                "messages":false
                                }""")
                        .header("AUTHORIZATION","Bearer " + token))

                .andExpect(status().isBadRequest());
        Assertions.assertThat(userRepository.findById(1L).get().getName()).isEqualTo("admin");
    }
}
