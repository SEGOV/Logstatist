package com.logstatist.stream;

import com.logstatist.util.provider.LogFilesProvider;
import com.logstatist.util.provider.SortedListByDateProvider;
import com.logstatist.util.reader.LogFileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.logstatist.util.Constants.LOG_FILES_DIRECTORY_PATH;

public class StreamLogStatist {
    public static void main(String[] args) {
        LogFilesProvider logFilesProvider = new LogFilesProvider();
        List<Path> logFilesNamesList = logFilesProvider.provide(LOG_FILES_DIRECTORY_PATH);

        List<List<String>> logRowsList = logFilesNamesList
                .parallelStream()
                .map(path -> new LogFileReader().read(path))
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());

        List<String> notSortedLogList = logRowsList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        ArrayList<String> sortedLogList = new SortedListByDateProvider().getSortedList(notSortedLogList);
        sortedLogList.forEach(System.out::println);
    }
}
