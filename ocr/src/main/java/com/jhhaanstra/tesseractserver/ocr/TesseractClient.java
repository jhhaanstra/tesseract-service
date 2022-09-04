package com.jhhaanstra.tesseractserver.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.Objects;

public class TesseractClient {

    private final ITesseract instance;

    public TesseractClient() {
        this(new Tesseract());
    }

    public TesseractClient(ITesseract instance) {
        this.instance = instance;
        instance.setDatapath(Objects.requireNonNull(TesseractClient.class.getResource("tessdata")).getPath());
    }

    public String doOCR(File imageFile) throws TesseractException {
        return instance.doOCR(imageFile);
    }
}
