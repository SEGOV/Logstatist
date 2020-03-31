package com.logstatist.util.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.logstatist.util.Constants.ERROR;
import static com.logstatist.util.Constants.LOG_FILE_TO_WRITE_PATH;

public class LogFileIO {
    public List<String> read(Path filePath) {
        List<String> filteredLines = null;
        try (Stream<String> lines = Files.lines(filePath)) {
            filteredLines = lines
                    .filter(row -> row.contains(ERROR))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;
    }

    public void write(ArrayList<String> sortedLogList) throws IOException {
        FileWriter writer = new FileWriter(LOG_FILE_TO_WRITE_PATH);
        for (String row : sortedLogList) {
            writer.write(row + System.lineSeparator());
        }
    }
}
