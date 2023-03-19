package com.jhhaanstra.tesseractserver.server.ocr;

import com.jhhaanstra.tesseractserver.server.ApplicationTestBase;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OcrControllerTest extends ApplicationTestBase {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Test
    void shouldGetStatus() throws URISyntaxException, IOException {
        File image = new File(Objects.requireNonNull(getClass().getResource("mistborn.png")).toURI());

        byte[] encode = ENCODER.encode(FileUtils.readFileToByteArray(image));

        RequestEntity<byte[]> body = RequestEntity.post(getEndpoint("/ocr/extract"))
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .body(encode);

        ResponseEntity<String> exchange = restTemplate.exchange(body, String.class);
        assertThat(exchange.getBody()).isEqualTo("""
                For a thousand years the ash fell and no flowers bloomed. For a
                thousand years the Skaa slaved in misery and lived in fear. For 2
                thousand years the Lord Ruler, the "Sliver of Infinity," reigned
                with absolute power and ultimate terror, divinely invincible.
                """);
    }
}