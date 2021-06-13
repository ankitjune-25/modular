package com.ankit.modular.it;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {IntegrationTest.Initializer.class})
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .waitingFor(Wait.defaultWaitStrategy());


    @Container
    public static RabbitMQContainer rabbitMqContainer = new RabbitMQContainer("rabbitmq")
            .withUser("ankit", "admin123")
            .withExposedPorts(5672)
            .waitingFor(Wait.defaultWaitStrategy());


    @Autowired
    private MockMvc mockMvc;

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Order(1)
    public void createAccountTest() throws Exception {
        JSONObject jsonRequest = parseJSONFile(ResourceUtils.getFile("classpath:request/input.json"));
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.getString("createAccount")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Create Account IT: "+mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void getAccountTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/account/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Get Account IT: "+mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    public void createTransactionTest() throws Exception {
        JSONObject jsonRequest = parseJSONFile(ResourceUtils.getFile("classpath:request/input.json"));
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.getString("createTransaction")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Create Transaction IT: "+mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(4)
    public void getTransactionTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/transaction/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Get Transaction IT: "+mvcResult.getResponse().getContentAsString());
    }

    public static JSONObject parseJSONFile(File filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(filename.toPath()));
        return new JSONObject(content);
    }
}
