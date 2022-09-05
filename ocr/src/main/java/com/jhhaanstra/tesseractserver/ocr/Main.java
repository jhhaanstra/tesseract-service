package com.jhhaanstra.tesseractserver.ocr;

import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        File image = new File(Objects.requireNonNull(Main.class.getResource("images/nos.png")).toURI());
        TesseractClient client = new TesseractClient();

        try {
            String result = client.doOCR(image, "nld");
            System.out.println(result);
        } catch (TesseractException e) {
            System.out.println("Not able to apply OCR: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
