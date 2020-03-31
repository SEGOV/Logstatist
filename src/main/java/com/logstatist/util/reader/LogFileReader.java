package com.logstatist.util.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.logstatist.util.Constants.ERROR;

public class LogFileReader {

    public List<String> read(Path filePath) {
        List<String> filteredLines = null;
        try (Stream<String> lines = Files.lines(filePath))
        {
            filteredLines = lines
                    .filter(row -> row.contains(ERROR))
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;
    }
}
