package com.guillermo.leif.inputReaders.signalPatternNotes;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SignalPatternNoteReader extends AllLineReader {
    public Integer getFourDigitValuesSum(String fileName) throws Exception {
        List<String> allLines = readAllLinesFromFile(fileName);
        List<Integer> fourDigitValues = new ArrayList<>();
        for (String line : allLines) {
            String[] splitLine = line.split(" \\| ");
            List<String> patterns = List.of(splitLine[0].split(" "));
            List<String> outputsToDecode = List.of(splitLine[1].split(" "));

            Map<List<Character>, String> decodedPatterns = decodePatterns(patterns);

            Integer fourDigitValue = getFourDigitValueFromLine(decodedPatterns, outputsToDecode);
            fourDigitValues.add(fourDigitValue);
        }


        return fourDigitValues.stream()
                .reduce(0, Integer::sum);

    }

    public <K, V> K getKeyFromValueInMap(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst().get();
    }

    // 1, 6,
    private Map<List<Character>, String> decodePatterns(List<String> patterns) throws Exception {
        Map<List<Character>, String> patternMap = mapSimplePatterns(patterns);
        List<Character> onePattern = getKeyFromValueInMap(patternMap, "1");

        List<String> patternsWithSixSegments = patterns.stream().filter(pattern -> pattern.length() == 6).collect(Collectors.toList());
        List<Character> sixPattern = get6Pattern(onePattern, patternsWithSixSegments);
        patternMap.put(sixPattern, "6");

        Character cSegment = getCSegmentFromPatterns(onePattern, sixPattern);
        Character fSegment = getFSegmentFromPatterns(onePattern, sixPattern);

        List<String> patternsWithFiveSegments = patterns.stream().filter(pattern -> pattern.length() == 5).collect(Collectors.toList());
        String fivePattern = get5Pattern(patternsWithFiveSegments, cSegment, fSegment);
        String threePattern = get3Pattern(patternsWithFiveSegments, cSegment, fSegment);
        String twoPattern = get2Pattern(patternsWithFiveSegments, cSegment, fSegment);
        patternMap.put(convertStringToCharacterList(twoPattern), "2");
        patternMap.put(convertStringToCharacterList(threePattern), "3");
        patternMap.put(convertStringToCharacterList(fivePattern), "5");

        List<Character> eightPattern = getKeyFromValueInMap(patternMap, "8");
        List<Character> fivePatternCharacterList = convertStringToCharacterList(fivePattern);
        Character eSegment = getESegmentFromPatterns(fivePatternCharacterList, eightPattern, cSegment);

        List<Character> zeroPattern = get0Pattern(patternsWithSixSegments, eSegment, cSegment);
        List<Character> ninePattern = get9Pattern(patternsWithSixSegments, eSegment);

        patternMap.put(zeroPattern, "0");
        patternMap.put(ninePattern, "9");

        return patternMap;
    }

    private Character getCSegmentFromPatterns(List<Character> onePattern, List<Character> sixPattern) throws Exception {
        for (Character onePatternCharacter : onePattern) {
            if (!sixPattern.contains(onePatternCharacter)) {
                return onePatternCharacter;
            }
        }
        throw new Exception("No C-segment character found");
    }

    private Character getFSegmentFromPatterns(List<Character> onePattern, List<Character> sixPattern) throws Exception {
        for (Character onePatternCharacter : onePattern) {
            if (sixPattern.contains(onePatternCharacter)) {
                return onePatternCharacter;
            }
        }
        throw new Exception("No F-segment character found");
    }

    private Character getESegmentFromPatterns(List<Character> fivePattern, List<Character> eightPattern, Character cSegment) throws Exception {
        for (Character character : eightPattern) {
            if (!fivePattern.contains(character) && !character.equals(cSegment)) {
                return character;
            }
        }
        throw new Exception("No E-segment character found");
    }

    private String get2Pattern(List<String> fiveSegmentPatterns, Character cCharacter, Character fCharacter) throws Exception {
        for (String pattern : fiveSegmentPatterns) {
            List<Character> characters = convertStringToCharacterList(pattern);
            if (characters.contains(cCharacter) && !characters.contains(fCharacter)) {
                return pattern;
            }
        }
        throw new Exception("No 2-Pattern found");
    }

    private String get3Pattern(List<String> fiveSegmentPatterns, Character cCharacter, Character fCharacter) throws Exception {
        for (String pattern : fiveSegmentPatterns) {
            List<Character> characters = convertStringToCharacterList(pattern);
            if (characters.contains(cCharacter) && characters.contains(fCharacter)) {
                return pattern;
            }
        }
        throw new Exception("No 3-Pattern found");
    }


    private String get5Pattern(List<String> fiveSegmentPatterns, Character cCharacter, Character fCharacter) throws Exception {
        for (String pattern : fiveSegmentPatterns) {
            List<Character> characters = convertStringToCharacterList(pattern);
            if (!characters.contains(cCharacter) && characters.contains(fCharacter)) {
                return pattern;
            }
        }
        throw new Exception("No 5-Pattern found");
    }

    private List<Character> get6Pattern(List<Character> onePattern, List<String> patternsWithSixSegments) throws Exception {
        for (String pattern : patternsWithSixSegments) {
            if ((pattern.contains(onePattern.get(0).toString()) && !pattern.contains(onePattern.get(1).toString()))
                    || (!pattern.contains(onePattern.get(0).toString()) && pattern.contains(onePattern.get(1).toString()))
            ) {
                return convertStringToCharacterList(pattern);
            }
        }
        throw new Exception("Six pattern not found...");
    }

    private List<Character> get9Pattern(List<String> patternsWithSixSegments, Character eSegment) throws Exception {
        for (String pattern : patternsWithSixSegments) {
            if (!pattern.contains(eSegment.toString())) {
                return convertStringToCharacterList(pattern);
            }
        }
        throw new Exception("Nine pattern not found...");
    }

    private List<Character> get0Pattern(List<String> patternsWithSixSegments, Character eSegment, Character cSegment) throws Exception {
        for (String pattern : patternsWithSixSegments) {
            if (pattern.contains(eSegment.toString()) && pattern.contains(cSegment.toString())) {
                return convertStringToCharacterList(pattern);
            }
        }
        throw new Exception("Zero pattern not found...");
    }

    private List<Character> convertStringToCharacterList(String string) {
        return List.of(new String(string.toCharArray()).chars()
                .mapToObj(c -> (char) c)
                .toArray(Character[]::new));
    }

    private Map<List<Character>, String> mapSimplePatterns(List<String> patterns) {
        Map<List<Character>, String> patternMap = new HashMap<>();
        for (String pattern : patterns) {
            List<Character> patternList = convertStringToCharacterList(pattern);
            switch (pattern.length()) {
                case 2:
                    patternMap.put(patternList, "1");
                    break;
                case 4:
                    patternMap.put(patternList, "4");
                    break;
                case 3:
                    patternMap.put(patternList, "7");
                    break;
                case 7:
                    patternMap.put(patternList, "8");
                    break;
            }
        }
        return patternMap;
    }

    public List<UniquePatternOutputCouple> getAllPatternOutputCouples(String fileName) throws IOException {
        List<String> allLines = readAllLinesFromFile(fileName);

        List<UniquePatternOutputCouple> outputs = new ArrayList<>();

        for (String line : allLines) {
            String[] splitLine = line.split(" \\| ");
            List<String> individualPatterns = List.of(splitLine[0].split(" "));
            List<String> individualOutputs = List.of(splitLine[1].split(" "));

            UniquePatternOutputCouple patternOutputCouple = new UniquePatternOutputCouple();
            patternOutputCouple.setPatterns(individualPatterns);
            patternOutputCouple.setOutputs(individualOutputs);
            outputs.add(patternOutputCouple);
        }
        return outputs;
    }

    public List<String> getAlLOutputs(String fileName) throws IOException {
        List<String> allLines = readAllLinesFromFile(fileName);

        List<String> outputs = new ArrayList<>();
        for (String line : allLines) {
            String[] splitLine = line.split(" \\| ");
            List<String> individualOutputs = List.of(splitLine[1].split(" "));
            outputs.addAll(individualOutputs);
        }

        return outputs;
    }

    private Integer getFourDigitValueFromLine(Map<List<Character>, String> decodedPatterns, List<String> outputsToDecode) {
        StringBuilder stringValue = new StringBuilder();

        for (String output : outputsToDecode) {
            List<Character> characterListVersionOfOutput = convertStringToCharacterList(output);

            for (List<Character> decodedPattern : decodedPatterns.keySet()) {
                if (patternContainsAllCharactersInString(decodedPattern, characterListVersionOfOutput)) {
                    stringValue.append(decodedPatterns.get(decodedPattern));
                    break;
                }
            }
        }

        return Integer.parseInt(stringValue.toString());
    }

    private Boolean patternContainsAllCharactersInString(List<Character> pattern, List<Character> string) {
        for (Character stringChar : string) {
            if ((string.size() != pattern.size()) || !pattern.contains(stringChar)) {
                return false;
            }
        }
        return true;
    }


}
