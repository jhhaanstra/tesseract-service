package com.jhhaanstra.tesseractserver.server;

import com.jhhaanstra.tesseractserver.server.ocr.OcrController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServerTest {

    @Autowired
    private OcrController controller;

    @Test
    void shouldBootServer() {
        assertThat(controller).isNotNull();
    }
}