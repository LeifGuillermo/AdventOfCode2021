package com.guillermo.leif.challenges.dec14;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CharCounter {
    private final Map<String, Long> characterCounts = new HashMap<>();
    private Map<String, Long> pairCounts = new HashMap<>();

    public void initializeCounts(String template) {
        String[] splitTemplate = template.split("");

        // pairs
        for (int i = 0; i < splitTemplate.length - 1; i++) {
            String pair = template.substring(i, i + 2);
            Long currentPairCount = 1L;
            if (pairCounts.containsKey(pair)) {
                currentPairCount += pairCounts.get(pair);
            }
            pairCounts.put(pair, currentPairCount);
        }

        // chars
        for (String s : splitTemplate) {
            long count = 1;
            if (characterCounts.containsKey(s)) {
                count += characterCounts.get(s);
            }
            characterCounts.put(s, count);
        }
    }

    public void updatePairsAndChars(Map<String, String> instructions, int step) {
        log.info("Updating step: " + step);
        Map<String, Long> updatedPairCounts = new HashMap<>(pairCounts);

        for (String pairKey : pairCounts.keySet()) {
            // pair counts 1 & 2 should replace existing pairs and also be added to existing counts for the new pairs
            // that are created (two new pairs are created)
            Long pair1Count = pairCounts.get(pairKey);
            Long pair2Count = pair1Count;
            Long newCharCount = pair1Count; // We are only adding characters, never removing them.
            if (instructions.containsKey(pairKey)) {

                // add to the char count of the inserted character from the instructions equal to the number of pairs from the instructions that were found.
                String newChar = instructions.get(pairKey);
                if (characterCounts.containsKey(newChar)) {
                    newCharCount += characterCounts.get(newChar); // add to what's already there if there is something.
                }
                characterCounts.put(newChar, newCharCount);

                // Add to the two new pair counts;
                String newPair1 = "" + pairKey.charAt(0) + newChar;
                String newPair2 = "" + newChar + pairKey.charAt(1);

                // destroy remove all of the original found pairs since they are updated to the new pairs.
                updatedPairCounts.put(pairKey, updatedPairCounts.get(pairKey) - pair1Count);

                if (updatedPairCounts.containsKey(newPair1)) {
                    pair1Count += updatedPairCounts.get(newPair1);
                }
                if (updatedPairCounts.containsKey(newPair2)) {
                    pair2Count += updatedPairCounts.get(newPair2);
                }
                updatedPairCounts.put(newPair1, pair1Count);
                updatedPairCounts.put(newPair2, pair2Count);

            }
        }
        pairCounts = new HashMap<>(updatedPairCounts);
    }

    void logCharCounts() {
        for (String key : characterCounts.keySet()) {
            log.info(key + ": " + characterCounts.get(key));
        }
        log.info("Highest Count: " + getHighestCount());
        log.info("Lowest Count: " + getLowestCount());
        log.info("Difference: " + (getHighestCount() - getLowestCount()));
    }

    void logPairs() {
        for (String key : pairCounts.keySet()) {
            log.info(key + ": " + pairCounts.get(key));
        }
    }

    public long getHighestCount() {
        long count = 0;
        for (String key : characterCounts.keySet()) {
            long charCount = characterCounts.get(key);
            if (charCount > count) {
                count = charCount;
            }
        }
        return count;
    }

    public long getLowestCount() {
        long count = Long.MAX_VALUE;
        for (String key : characterCounts.keySet()) {
            long charCount = characterCounts.get(key);
            if (charCount < count) {
                count = charCount;
            }
        }
        return count;
    }
}
