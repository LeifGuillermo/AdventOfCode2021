package com.guillermo.leif.inputReaders.signalPatternNotes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.guillermo.leif.inputReaders.AllLineReader;

@Component
public class SignalPatternNoteReader extends AllLineReader {
	private Character cSegment = 'c';

	Integer getFourDigitValuesSum(String fileName) throws IOException {
		List<String> allLines = readAllLinesFromFile(fileName);
		List<Integer> fourDigitValues = new ArrayList<>();
		for (String line : allLines) {
			String[] splitLine = line.split(" \\| ");
			List<String> patterns = List.of(splitLine[0].split(" "));
			List<String> outputs = List.of(splitLine[1].split(" "));

			Map<List<Character>, String> decodedPatterns = decodePatterns(patterns);

			Integer fourDigitValue = getFourDigitValueFromLine();
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

	private Map<List<Character>, String> decodePatterns(List<String> patterns) {
		Map<List<Character>, String> patternMap = mapSimplePatterns(patterns);
		List<Character> onePattern = getKeyFromValueInMap(patternMap, "1");
		List<Character> sixPattern = findSixPattern(onePattern, patterns);
		patternMap.put(sixPattern, "6");

		return patternMap;
	}

	private List<Character> findSixPattern(List<Character> onePattern, List<String> patterns) {
		for (String pattern : patterns) {
			if(pattern.contains(onePattern.get(0).toString()) && pattern.contains(onePattern.get(1).toString()));

		}
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

	private Integer getFourDigitValueFromLine() {
		return null;// todo
	}


}
