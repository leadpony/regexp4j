// Copyright (C) 2020 Apple Inc. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp.namedgroups;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leadpony.regexp4j.ExecResult;
import org.leadpony.regexp4j.RegExp;

/*---
author: Michael Saboff
description: Exotic named group names in non-Unicode RegExps
esid: prod-GroupSpecifier
features: [regexp-named-groups]
---*/
public class NonUnicodePropertyNamesValidTest {

    private static final String STRING1 = "The quick brown fox jumped over the lazy dog's back";
    private static final String STRING2 = "It is a dog eat dog world.";

    public static List<Arguments> shouldMatchString1() {
        return Arrays.asList(
                Arguments.of("(?<animal>fox|dog)", "animal", "fox"),

                Arguments.of("(?<𝓑𝓻𝓸𝔀𝓷>brown)", "𝓑𝓻𝓸𝔀𝓷", "brown"),
                Arguments.of("(?<\ud835\udcd1\ud835\udcfb\ud835\udcf8\ud835\udd00\ud835\udcf7>brown)", "𝓑𝓻𝓸𝔀𝓷", "brown"),

                Arguments.of("(?<𝖰𝖡𝖥>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<𝖰𝖡\ud835\udda5>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<𝖰\ud835\udda1𝖥>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<𝖰\ud835\udda1\ud835\udda5>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<\ud835\uddb0𝖡𝖥>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<\ud835\uddb0𝖡\ud835\udda5>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<\ud835\uddb0\ud835\udda1𝖥>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),
                Arguments.of("(?<\ud835\uddb0\ud835\udda1\ud835\udda5>q\\w*\\W\\w*\\W\\w*)", "𝖰𝖡𝖥", "quick brown fox"),

                Arguments.of("(?<the𝟚>the)", "the𝟚", "the"),
                Arguments.of("(?<the\ud835\udfda>the)", "the𝟚", "the")
                );
    }

    @ParameterizedTest
    @MethodSource
    public void shouldMatchString1(String pattern, String groupName, String expected) {
        match(STRING1, pattern, groupName, expected);
    }

    @Test
    public void string1_1() {
        ExecResult result = new RegExp("(?<𝑓𝑜𝑥>fox).*(?<𝓓𝓸𝓰>dog)").execForResult(STRING1);
        assertThat(result.groups().get("𝑓𝑜𝑥")).isEqualTo("fox");
        assertThat(result.groups().get("𝓓𝓸𝓰")).isEqualTo("dog");
        assertThat(result.asArray()[1]).isEqualTo("fox");
        assertThat(result.asArray()[2]).isEqualTo("dog");
    }

    @Test
    public void string1_2() {
        ExecResult result = new RegExp("(?<狸>fox).*(?<狗>dog)").execForResult(STRING1);
        assertThat(result.groups().get("狸")).isEqualTo("fox");
        assertThat(result.groups().get("狗")).isEqualTo("dog");
        assertThat(result.asArray()[1]).isEqualTo("fox");
        assertThat(result.asArray()[2]).isEqualTo("dog");
    }

    @Test
    public void string2_1() {
        ExecResult result = new RegExp("(?<dog>dog)(.*?)(\\k<dog>)").execForResult(STRING2);
        assertThat(result.groups().get("dog")).isEqualTo("dog");
        assertThat(result.asArray()[1]).isEqualTo("dog");
        assertThat(result.asArray()[2]).isEqualTo(" eat ");
        assertThat(result.asArray()[3]).isEqualTo("dog");
    }

    @Test
    public void string2_2() {
        ExecResult result = new RegExp("(?<𝓓𝓸𝓰>dog)(.*?)(\\k<𝓓𝓸𝓰>)").execForResult(STRING2);
        assertThat(result.groups().get("𝓓𝓸𝓰")).isEqualTo("dog");
        assertThat(result.asArray()[1]).isEqualTo("dog");
        assertThat(result.asArray()[2]).isEqualTo(" eat ");
        assertThat(result.asArray()[3]).isEqualTo("dog");
    }

    @Test
    public void string2_3() {
        ExecResult result = new RegExp("(?<狗>dog)(.*?)(\\k<狗>)").execForResult(STRING2);
        assertThat(result.groups().get("狗")).isEqualTo("dog");
        assertThat(result.asArray()[1]).isEqualTo("dog");
        assertThat(result.asArray()[2]).isEqualTo(" eat ");
        assertThat(result.asArray()[3]).isEqualTo("dog");
    }

    private static void match(String string, String pattern, String groupName, String expected) {
        ExecResult result = new RegExp(pattern).execForResult(string);
        assertThat(result).isNotNull();
        assertThat(result.groups().get(groupName)).isEqualTo(expected);
    }
}
