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
description: Exotic named group names in non-Unicode RegExps
esid: prod-GroupSpecifier
features: [regexp-named-groups]
---*/
public class NonUnicodePropertyNamesTest {

    public static List<Arguments> shouldReturnGroupAsExpected() {
        return Arrays.asList(
                Arguments.of("(?<π>a)", "π"),
                Arguments.of("(?<π>a)", "\u03C0"),
                Arguments.of("(?<$>a)", "$"),
                Arguments.of("(?<_>a)", "_"),
                Arguments.of("(?<_\\u200C>a)", "_\u200C"),
                Arguments.of("(?<_\\u200D>a)", "_\u200D"),
                Arguments.of("(?<ಠ_ಠ>a)", "ಠ_ಠ")
                );
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnGroupAsExpected(String pattern, String groupName) {
        RegExp re = new RegExp(pattern);
        Map<String, String> groups = re.execForResult("bab").groups();
        assertThat(groups.get(groupName)).isEqualTo("a");
    }

    // Unicode escapes in capture names.
    // 4-char escapes must be the proper ID_Start/ID_Continue
    @Test
    public void shouldHandleUnicodeEscapesInCaptureNames() {
        assertThat(new RegExp("(?<\u0041>.)").test("a"));
        assertThat(new RegExp("(?<\\u0041>.)").test("a"));
    }
}
