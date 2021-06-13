package com.ankit.modular;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringJUnitConfig
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class AbstractTest {

    protected static JSONObject jsonRequest;
    protected static JSONObject jsonMock;
    protected static JSONObject jsonResponse;
    protected static Gson gson = new Gson();


    @BeforeAll
    public static void init() throws JSONException, IOException {
        jsonRequest = parseJSONFile(ResourceUtils.getFile("classpath:request/input.json"));
        jsonMock = parseJSONFile(ResourceUtils.getFile("classpath:mock/mock.json"));
        jsonResponse = parseJSONFile(ResourceUtils.getFile("classpath:response/response.json"));
    }

    public static JSONObject parseJSONFile(File filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(filename.toPath()));
        return new JSONObject(content);
    }
}
