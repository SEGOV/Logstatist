package com.logstatist.util.provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.logstatist.util.Constants.TXT;

public class LogFilesProvider {
    public List<Path> provide(String logFilesDirectoryPath) {
        List<Path> logFilesNamesList = null;
        Path path = Paths.get(logFilesDirectoryPath);

        try {
            logFilesNamesList = Files.find(path, 1, (p, a) -> a.isRegularFile() && p.getFileName()
                    .toString()
                    .endsWith(TXT))
                    .collect(Collectors.toList()); // TODO: change into .log
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logFilesNamesList;
    }
}
