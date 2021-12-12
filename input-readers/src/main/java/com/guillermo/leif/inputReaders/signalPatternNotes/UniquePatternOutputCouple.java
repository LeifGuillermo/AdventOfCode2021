package com.guillermo.leif.inputReaders.signalPatternNotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UniquePatternOutputCouple {
    private List<String> patterns;
    private List<String> outputs;

    @Getter(AccessLevel.NONE)
    private Map<Integer, Integer> patternSizeCounts = new HashMap<>();
    @Getter(AccessLevel.NONE)
    private List<Integer> uniquePatternSizes = new ArrayList<>();

    public void setUniquePatternSizes() {
        for(String pattern : patterns) {
            int size = pattern.length();
            int count = 0;
            if(patternSizeCounts.containsKey(size)) {
                count = patternSizeCounts.get(size);
            }
            patternSizeCounts.put(size, count+1);
        }

        for(Integer patternSize : patternSizeCounts.keySet()) {
            if(patternSizeCounts.get(patternSize) == 1) {
                uniquePatternSizes.add(patternSize);
            }
        }
    }

    public int countUniquePatternSizesInOutput() {
        int count = 0;
        for (String output : outputs) {
            if (uniquePatternSizes.contains(output.length())) {
                count++;
            }
        }
        return count;
    }
}
