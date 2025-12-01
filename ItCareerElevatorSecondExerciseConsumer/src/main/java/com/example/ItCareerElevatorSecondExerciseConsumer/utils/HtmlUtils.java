package com.example.ItCareerElevatorSecondExerciseConsumer.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlUtils {

    private static final String HTML_REPLACE_SYMBOL = "※"; // Rare unicode char: avoid collisions and ensure cross-platform safety

    public static InputStream fillHtmlTemplate(String[] fillData, String htmlTemplateFilePath) throws IOException {
        int currentIndex = -1; // Using pre-incrementation: start from -1
        StringBuilder resultHtml = new StringBuilder();

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                Files.newInputStream(Paths.get(htmlTemplateFilePath)),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                StringBuilder lineBuilder = new StringBuilder(line);

                int index;
                while ((index = lineBuilder.indexOf(HTML_REPLACE_SYMBOL)) != -1)
                    lineBuilder.replace(index, index + HTML_REPLACE_SYMBOL.length(), fillData[++currentIndex]);

                resultHtml.append(lineBuilder).append(System.lineSeparator());
            }
        }

        if (currentIndex < (fillData.length - 1)) // Replace elements are less than the number of parameters
            throw new ArrayIndexOutOfBoundsException();

        return new ByteArrayInputStream(resultHtml.toString().getBytes(StandardCharsets.UTF_8));
    }
}
