package com.jhhaanstra.tesseractserver.ocr;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TesseractClientTest {

    private static final Config CONFIG = ConfigFactory.load().getConfig("tesseract-server.ocr");

    private static final ITesseract CLIENT = spy(new Tesseract());

    private static final TesseractClient TESSERACT_CLIENT = new TesseractClient(CLIENT, CONFIG);

    @Test
    void shouldDoOCR() throws TesseractException {
        File image = getImage("images/mistborn.png");
        shouldDoOCR(image, """
                For a thousand years the ash fell and no flowers bloomed. For a
                thousand years the Skaa slaved in misery and lived in fear. For 2
                thousand years the Lord Ruler, the "Sliver of Infinity," reigned
                with absolute power and ultimate terror, divinely invincible.
                """);
    }

    @Test
    void shouldChangeLanguage() throws TesseractException {
        File image = getImage("images/piloot.png");
        shouldDoOCR(image, "nld", """
                Rutherford is namelijk ook de jongste piloot die de vliegreis volbracht in een
                zogeheten ultralicht vliegtuig. Dat is een klein, eenmotorig toestel. Het record
                stond op naam van zijn drie jaar oudere zus Zara, die de reis in januari
                voltooide. Zij is nog wel de jongste vrouwellijke piloot die dat presteerde.
                """);

        verify(CLIENT, times(1)).setLanguage("nld");
        verify(CLIENT, times(1)).setLanguage("eng");
    }

    /**
     * Apparently bufferedImages seem to lower the quality of results. Should look into this replacing this.
     */
    @Test
    void shouldHandleBufferedImages() throws IOException, TesseractException {
        File file = getImage("images/piloot.png");
        BufferedImage image = ImageIO.read(file);
        shouldDoOCR(image, """
                Rutherford is namelijk ook de jongste piloot die de viiegreis volbracht in een
                zogeheten ultralicht viiegtuig. Dat is een Kiein, eenmotorig toestel. Het record
                stond op naam van zijn drie jaar oudere zus Zara, die de reis in januari
                voltooide. Zij is nog wel de jongste vrouwelijke piloot die dat presteerde.
                """);
    }

    private File getImage(String imageName) {
        try {
            return new File(Objects.requireNonNull(Main.class.getResource(imageName)).toURI());
        } catch (URISyntaxException e) {
            throw new AssertionError("Image: " + imageName + " could not be found");
        }
    }

    private void shouldDoOCR(BufferedImage toAnalyze, String expected) throws TesseractException {
        assertThat(TESSERACT_CLIENT.doOCR(toAnalyze)).isEqualTo(expected);
    }

    private void shouldDoOCR(File toAnalyze, String expected) throws TesseractException {
        assertThat(TESSERACT_CLIENT.doOCR(toAnalyze)).isEqualTo(expected);
    }

    private void shouldDoOCR(File toAnalyze, String language, String expected) throws TesseractException {
        assertThat(TESSERACT_CLIENT.doOCR(toAnalyze, language)).isEqualTo(expected);
    }


}
