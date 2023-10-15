package com.example.damanhacker.utlities;

import android.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaximumNumberFind {

    public Pair<Integer, Integer> findMaxRepeatedNumber(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return null;
        }

        Map<Integer, Integer> countMap = new HashMap<>();
        int maxCount = 0;
        int maxRepeatedNumber = -1;

        for (int number : numbers) {
            int count = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                count = countMap.getOrDefault(number, 0) + 1;
            }
            countMap.put(number, count);

            if (count > maxCount) {
                maxCount = count;
                maxRepeatedNumber = number;
            }
        }

        return new Pair<>(maxRepeatedNumber, maxCount);
    }
}
