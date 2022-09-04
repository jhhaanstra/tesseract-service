package com.jhhaanstra.tesseractserver.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        File image = new File(Objects.requireNonNull(Main.class.getResource("images/nos.png")).toURI());

        ITesseract instance = new Tesseract();
        instance.setDatapath(Objects.requireNonNull(Main.class.getResource("tessdata")).getPath());
        instance.setLanguage("nld");

        try {
            String result = instance.doOCR(image);
            System.out.println(result);
        } catch (TesseractException e) {
            System.out.println("Not able to apply OCR: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
