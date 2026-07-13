package ru.itis.shop.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public Properties read(String fileName) {
        Properties properties = new Properties();

        try (InputStream inputStream =
                     PropertiesReader.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Файл не найден: " + fileName
                );
            }

            properties.load(inputStream);

            return properties;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}