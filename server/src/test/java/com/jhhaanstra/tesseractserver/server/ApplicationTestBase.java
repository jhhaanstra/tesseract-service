package com.jhhaanstra.tesseractserver.server;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestBase {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getServerUrl() {
        return "http://localhost:" + port;
    }

    protected String getEndpoint(String endpoint) {
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }

        return getServerUrl() + endpoint;
    }
}
