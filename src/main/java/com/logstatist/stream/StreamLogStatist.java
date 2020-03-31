package com.logstatist.stream;

import com.logstatist.stream.provider.LogFilesProvider;
import com.logstatist.stream.provider.SortedListByDateProvider;
import com.logstatist.stream.reader.LogFileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamLogStatist {
    private static String LOG_FILES_DIRECTORY_PATH = "c:\\tmp";

    public static void main(String[] args) {
        LogFilesProvider logFilesProvider = new LogFilesProvider();
        List<Path> logFilesNamesList = logFilesProvider.provide(LOG_FILES_DIRECTORY_PATH);

        List<List<String>> logRowsList = logFilesNamesList
                .parallelStream()
                .map(path -> new LogFileReader().read(path))
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());

        List<String> notSortedLogList = logRowsList.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        ArrayList<String> sortedLogList = new SortedListByDateProvider().getSortedList(notSortedLogList);
        sortedLogList.forEach(System.out::println);
    }
}
