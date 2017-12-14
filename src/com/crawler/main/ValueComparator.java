package com.crawler.main;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {

    private Map<String, Integer> map;

    public ValueComparator(Map<String, Integer> map) {
        this.map = map;
    }

    public int compare(String a, String b) {
        return map.get(a).compareTo(map.get(b));
    }
}