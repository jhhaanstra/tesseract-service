package com.jhhaanstra.tesseractserver.server.ocr;

import com.jhhaanstra.tesseractserver.ocr.TesseractClient;
import org.springframework.beans.factory.annotation.Autowired;

public class OcrService {

    @Autowired
    private TesseractClient tesseractClient;

}
