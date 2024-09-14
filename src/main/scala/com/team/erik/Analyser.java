package com.team.erik;

import com.core.data.DataTable;

public class Analyser {
    public static String hello(){
        var data = DataTable.trigramFrequencies();
        return data.apply("THE").toString();
    }
}