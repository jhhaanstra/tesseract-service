package com.jhhaanstra.tesseractserver.ocr;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TesseractServerTest {

    private static final TesseractClient TESSERACT_CLIENT = new TesseractClient();

    @Test
    void shouldDoOCR() throws URISyntaxException, TesseractException {
        File image = new File(Objects.requireNonNull(Main.class.getResource("images/mistborn.png")).toURI());
        shouldDoOCR(image, """
                For a thousand years the ash fell and no flowers bloomed. For a
                thousand years the Skaa slaved in misery and lived in fear. For 2
                thousand years the Lord Ruler, the "Sliver of Infinity," reigned
                with absolute power and ultimate terror, divinely invincible.
                """);
    }

    private void shouldDoOCR(File toAnalyze, String expected) throws TesseractException {
        assertThat(TESSERACT_CLIENT.doOCR(toAnalyze)).isEqualTo(expected);
    }


}
