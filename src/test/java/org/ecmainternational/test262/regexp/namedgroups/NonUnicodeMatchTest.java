// Copyright 2017 the V8 project authors. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp.namedgroups;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leadpony.regexp4j.RegExp;

/*---
description: Basic matching cases with non-Unicode groups
esid: prod-GroupSpecifier
features: [regexp-named-groups]
includes: [compareArray.js]
---*/
public class NonUnicodeMatchTest {

    private static Arguments patternResults(String pattern, String input, String... expected) {
        return Arguments.of(pattern, input, expected);
    }

    public static List<Arguments> execShouldReturnExpectedArray() {
        return Arrays.asList(
                patternResults("(?<a>a)", "bab", "a", "a"),
                patternResults("(?<a42>a)", "bab", "a", "a"),
                patternResults("(?<_>a)", "bab", "a", "a"),
                patternResults("(?<$>a)", "bab", "a", "a"),
                patternResults(".(?<$>a).", "bab", "bab", "a"),
                patternResults(".(?<a>a)(.)", "bab", "bab", "a", "b"),
                patternResults(".(?<a>a)(?<b>.)", "bab", "bab", "a", "b"),
                patternResults(".(?<a>\\w\\w)", "bab", "bab", "ab"),
                patternResults("(?<a>\\w\\w\\w)", "bab", "bab", "bab"),
                patternResults("(?<a>\\w\\w)(?<b>\\w)", "bab", "bab", "ba", "b"),

                patternResults("(?<b>b).\\1", "bab", "bab", "b"),
                patternResults("(.)(?<a>a)\\1\\2", "baba", "baba", "b", "a"),
                patternResults("(.)(?<a>a)(?<b>\\1)(\\2)", "baba", "baba", "b", "a", "b", "a"),
                patternResults("(?<lt><)a", "<a", "<a", "<"),
                patternResults("(?<gt>>)a", ">a", ">a", ">")
                );
    }

    @ParameterizedTest
    @MethodSource
    public void execShouldReturnExpectedArray(String pattern, String input, String[] expected) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(input);
        assertThat(actual).isEqualTo(expected);
    }

    private static Arguments patternPair(String pattern1, String pattern2) {
        return Arguments.of(pattern1, pattern2);
    }

    public static List<Arguments> execShouldReturnSameResultWithName() {
        return Arrays.asList(
                patternPair("(a)", "(?<a>a)"),
                patternPair("(a)", "(?<a42>a)"),
                patternPair("(a)", "(?<_>a)"),
                patternPair("(a)", "(?<$>a)"),
                patternPair(".(a).", ".(?<$>a)."),
                patternPair(".(a)(.)", ".(?<a>a)(.)"),
                patternPair(".(a)(.)", ".(?<a>a)(?<b>.)"),
                patternPair(".(\\w\\w)", ".(?<a>\\w\\w)"),
                patternPair("(\\w\\w\\w)", "(?<a>\\w\\w\\w)"),
                patternPair("(\\w\\w)(\\w)", "(?<a>\\w\\w)(?<b>\\w)")
                );
    }

    @ParameterizedTest
    @MethodSource
    public void execShouldReturnSameResultWithName(String pattern1, String pattern2) {
        String string = "bab";
        String[] result1 = new RegExp(pattern1).exec(string);
        String[] result2 = new RegExp(pattern2).exec(string);
        assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void execForResultShouldReturnGroups() {
        RegExp re = new RegExp("(?<a>.)(?<b>.)(?<c>.)\\k<c>\\k<b>\\k<a>");
        Map<String, String> groups = re.execForResult("abccba").groups();
        assertThat(groups.get("a")).isEqualTo("a");
        assertThat(groups.get("b")).isEqualTo("b");
        assertThat(groups.get("c")).isEqualTo("c");
    }
}
