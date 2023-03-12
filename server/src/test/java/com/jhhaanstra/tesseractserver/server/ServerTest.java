package com.jhhaanstra.tesseractserver.server;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

class ServerTest extends ApplicationTestBase {

    @Test
    void shouldGetStatus() {
        AssertionsForClassTypes.assertThat(restTemplate.getForObject(getEndpoint("/status"), String.class))
                .contains("running");
    }
}