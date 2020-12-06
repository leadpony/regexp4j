// Copyright 2017 the V8 project authors. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp.dotall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leadpony.regexp4j.RegExp;

/*---
description: Without the dotAll flag, . does not match newlines
info: |
  21.2.2.8 Atom
  The production Atom::. evaluates as follows:
    1. If DotAll is true, then
      a. Let A be the set of all characters.
    2. Otherwise, let A be the set of all characters except LineTerminator.
    3. Call CharacterSetMatcher(A, false) and return its Matcher result.

esid: sec-atom
features: [u180e]
---*/
public class WithoutDotallTest {

    public static List<Arguments> testCases() {
        return Arrays.asList(
                arguments("a", true),
                arguments("3", true),
                arguments("π", true),
                arguments("\u2027", true),
                // NEXT LINE (NEL)
                // arguments("\u0085", true),
                arguments("\u000b", true),
                arguments("\f", true),
                arguments("\u180E", true),
                // OLD ITALIC LETTER A
                // arguments("\uD800\uDF00", false), // Supplementary plane not matched by a single .
                arguments("\n", false),
                arguments("\r", false),
                arguments("\u2028", false),
                arguments("\u2029", false),
                arguments("\uD800", true),
                arguments("\uDFFF", true)
                );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void test(String string, boolean expected) {
        testWithFlags("", string, expected);
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void testMultiline(String string, boolean expected) {
        testWithFlags("m", string, expected);
    }

    private void testWithFlags(String flags, String string, boolean expected) {
        RegExp re = new RegExp("^.$", flags);
        assertThat(re.test(string)).isEqualTo(expected);
    }
}
