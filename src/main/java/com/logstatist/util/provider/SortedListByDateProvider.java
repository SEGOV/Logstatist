package com.logstatist.util.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.logstatist.util.Constants.DATE_MASK_FORMAT;

public class SortedListByDateProvider {
    public ArrayList<String> getSortedList(List<String> notSortedLogList) {
        ArrayList<Date> sortedLogList = new ArrayList<>();
        ArrayList<String> sortedAndFormattedLogList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(DATE_MASK_FORMAT);
        try {
            for (String logRow : notSortedLogList) {
                Date date = format.parse(logRow);
                sortedLogList.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(sortedLogList);
        sortedLogList.forEach(action -> sortedAndFormattedLogList.add(format.format(action)));
        return sortedAndFormattedLogList;
    }
}
