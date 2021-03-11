package com.efimchick.ifmo.streams.countwords;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.efimchick.ifmo.streams.countwords.WordsConstants.MIN_COUNTER;
import static com.efimchick.ifmo.streams.countwords.WordsConstants.MIN_LENGTH;
import static com.efimchick.ifmo.streams.countwords.WordsConstants.NOT_ALPHABETIC_CHAR;
import static com.efimchick.ifmo.streams.countwords.WordsConstants.SPACE;
import static com.efimchick.ifmo.streams.countwords.WordsConstants.SPACES;
import static com.efimchick.ifmo.streams.countwords.WordsConstants.STRING_FORMAT;

public class Words {

    public String countWords(List<String> lines) {
        String result = lines.stream()
                .map(line -> line.replaceAll(NOT_ALPHABETIC_CHAR, SPACE))
                .flatMap(line -> Arrays.stream(line.toLowerCase().split(SPACES)))
                .filter(s -> s.length() >= MIN_LENGTH)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= MIN_COUNTER)
                .sorted(Map.Entry.comparingByKey())
                .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
                .map(e -> String.format(STRING_FORMAT, e.getKey(), e.getValue()))
                .collect(Collectors.joining());

        return deleteLastChar(result);
    }

    private String deleteLastChar(String result) {
        StringBuilder stringBuilder = new StringBuilder(result);

        return String.valueOf(stringBuilder.deleteCharAt(stringBuilder.length() - 1));
    }
}