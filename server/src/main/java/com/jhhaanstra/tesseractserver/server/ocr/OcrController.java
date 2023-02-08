package com.jhhaanstra.tesseractserver.server.ocr;

import com.jhhaanstra.tesseractserver.ocr.TesseractClient;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping(path = "/ocr")
public class OcrController {

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private final TesseractClient client;

    @Autowired
    public OcrController(TesseractClient client) {
        this.client = client;
    }

    @PostMapping(value = "/extract", consumes = TEXT_PLAIN_VALUE, produces = TEXT_PLAIN_VALUE)
    public String doOcr(@RequestBody String image) throws TesseractException, IOException {
        byte[] decoded = DECODER.decode(image);
        ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
        BufferedImage input = ImageIO.read(bis);
        return client.doOCR(input);
    }
}
