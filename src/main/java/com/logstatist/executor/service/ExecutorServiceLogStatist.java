package com.logstatist.executor.service;

import com.logstatist.util.provider.LogFilesProvider;
import com.logstatist.util.provider.SortedListByDateProvider;
import com.logstatist.util.file.LogFileIO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.logstatist.util.Constants.LOG_FILES_DIRECTORY_PATH;

public class ExecutorServiceLogStatist {
    public static void main(String args[]) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Path> logFilesNamesList = new LogFilesProvider().provide(LOG_FILES_DIRECTORY_PATH);
        ArrayList<String> nonSortedLogList = new ArrayList<>();

        for (Path filePath : logFilesNamesList) {
            Runnable worker = new MyRunnable(filePath, nonSortedLogList);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        ArrayList<String> sortedLogList = new SortedListByDateProvider().getSortedList(nonSortedLogList);
        new LogFileIO().write(sortedLogList);
        sortedLogList.forEach(System.out::println);
    }
}

class MyRunnable implements Runnable {
    private final Path filePath;
    private ArrayList<String> nonSortedLogList;

    MyRunnable(Path filePath, ArrayList<String> nonSortedLogList) {
        this.filePath = filePath;
        this.nonSortedLogList = nonSortedLogList;
    }

    @Override
    public void run() {
        List<String> logList = new LogFileIO().read(filePath);
        nonSortedLogList.addAll(logList);
    }
}


