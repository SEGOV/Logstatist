package com.logstatist.stream.reader;

import com.logstatist.stream.util.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogFileReader {

    public List<String> read(Path filePath) {
        List<String> filteredLines = null;
        try (Stream<String> lines = Files.lines(filePath))
        {
            filteredLines = lines
                    .filter(row -> row.contains(Constants.LogLevel.ERROR))
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;
    }
}
