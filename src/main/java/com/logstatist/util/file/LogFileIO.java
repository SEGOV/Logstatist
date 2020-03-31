package com.logstatist.util.file;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.logstatist.util.Constants.*;

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
        writer.close();
    }

    public void getLogFilesFromResourcesAndWriteThemToSystem() throws IOException {
        List<String> logFilesNamesList = Arrays.asList("log1.log", "log2.log", "log3.log", "log4.log");
        ClassLoader classLoader = getClass().getClassLoader();

        for(String logFileName : logFilesNamesList) {
            URL resource = classLoader.getResource(logFileName);
            File logFile = new File(resource.getFile());
            String logFilePathName = LOG_FILES_DIRECTORY_PATH + "\\" + logFile.getName();
            File fileInTheSystem = new File(logFilePathName);
            if(!fileInTheSystem.exists()) {
                fileInTheSystem.createNewFile();
                byte[] fileContent = Files.readAllBytes(logFile.toPath());
                FileOutputStream outputStream = new FileOutputStream(logFilePathName, true);
                outputStream.write(fileContent);
                outputStream.close();
            }
        }
    }
}
