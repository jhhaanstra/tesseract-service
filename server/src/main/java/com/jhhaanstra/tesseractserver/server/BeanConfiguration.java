package com.jhhaanstra.tesseractserver.server;

import com.jhhaanstra.tesseractserver.ocr.TesseractClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Config getConfig() {
        return ConfigFactory.load();
    }

    @Bean
    public TesseractClient getTesseractClient() {
        return new TesseractClient(new Tesseract(), getConfig().getConfig("tesseract-server.ocr"));
    }
}
