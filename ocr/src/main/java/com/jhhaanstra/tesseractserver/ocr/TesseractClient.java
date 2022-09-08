package com.jhhaanstra.tesseractserver.ocr;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TesseractClient {

    private final List<String> supportedLanguages;

    private final ITesseract instance;

    private final String defaultLanguage;

    public TesseractClient() {
        this(new Tesseract(), ConfigFactory.load().getConfig("tesseract-server.ocr"));
    }

    public TesseractClient(ITesseract instance, Config config) {
        this.instance = instance;
        this.supportedLanguages = config.getStringList("languages");
        this.defaultLanguage = config.getString("defaultLanguage");

        String dataDirectory = config.getString("data-directory");
        instance.setDatapath(Objects.requireNonNull(getClass().getResource(dataDirectory)).getPath());

        validateConfig();
    }

    private void validateConfig() {
        if (!supportedLanguages.contains(defaultLanguage)) {
            throw new IllegalArgumentException("Default language: " + defaultLanguage + " is not supported. Supported" +
                    " languages are: " + String.join(", ", supportedLanguages));
        }
    }

    public String doOCR(File imageFile) throws TesseractException {
        return instance.doOCR(imageFile);
    }

    public String doOCR(File imageFile, String language) throws TesseractException {
        return doOCRInLanguage(() -> instance.doOCR(imageFile), language);
    }

    private String doOCRInLanguage(ThrowingSupplier<String> supplier, String language) throws TesseractException {
        validateLanguage(language);
        changeLanguage(language);
        String result = supplier.get();
        restoreLanguageToDefault();
        return result;
    }

    private void validateLanguage(String language) {
        if (!supportedLanguages.contains(language)) {
            throw new IllegalArgumentException("Language: " + language + " is not supported. Supported languages " +
                    "are: " + String.join(", ", supportedLanguages));
        }
    }

    private void changeLanguage(String language) {
        instance.setLanguage(language);
    }

    private void restoreLanguageToDefault() {
        changeLanguage(defaultLanguage);
    }

    private interface ThrowingSupplier<T> {
        T get() throws TesseractException;
    }
}
