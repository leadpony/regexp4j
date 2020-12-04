// Copyright (c) 2012 Ecma International.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.

// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_2_15 {

    private static final Logger LOG = Logger.getLogger(Test15_10_2_15.class.getName());

    /*---
    es5id: 15.10.2.15-6-1
    description: >
        Pattern - SyntaxError was thrown when one character in CharSet 'A'
        greater than one character in CharSet 'B' (15.10.2.15
        CharacterRange step 6)
    ---*/
    @Test
    @DisplayName("15.10.2.15-6-1")
    public void test6_1() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("^[z-a]$");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    /*---
    info: |
        The internal helper function CharacterRange takes two CharSet parameters A and B and performs the
        following:
        2. Let a be the one character in CharSet A.
        3. Let b be the one character in CharSet B.
        4. Let i be the character value of character a.
        5. Let j be the character value of character b.
        6. If i > j, throw a SyntaxError exception.
    es5id: 15.10.2.15_A1_T1
    description: >
        Checking if execution of "/[b-ac-e]/.exec("a")" leads to throwing
        the correct exception
    ---*/
    @ParameterizedTest
    @ValueSource(strings = {
            "[b-ac-e]", // 15.10.2.15_A1_T1
            "[a-dc-b]",
            "[\\db-G]",
            "[\\Db-G]",
            "[\\sb-G]",
            "[\\Sb-G]",
            "[\\wb-G]",
            "[\\Wb-G]",
            "[\\0b-G]",
            "[\\10b-G]",
            "[\\bd-G]",
            "[\\Bd-G]",
            "[\\td-G]",
            "[\\nd-G]",
            "[\\vd-G]",
            "[\\fd-G]",
            "[\\rd-G]", // 15.10.2.15_A1_T17
    })
    public void A1_T1(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern).exec("a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[\\c0001d-G]", // 15.10.2.15_A1_T18
            "[\\x0061d-G]", // 15.10.2.15_A1_T19
    })
    public void A1_T18(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern).exec("1");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[\\u0061d-G]", // 15.10.2.15_A1_T20
            "[\\ad-G]",
            "[c-eb-a]",
            "[b-G\\d]",
            "[b-G\\D]",
            "[b-G\\s]",
            "[b-G\\S]",
            "[b-G\\w]",
            "[b-G\\W]",
            "[b-G\\0]",
            "[b-G\\10]",
            "[d-G\\b]",
            "[d-G\\B]",
            "[d-G\\t]",
            "[d-G\\n]",
            "[d-G\\v]",
            "[d-G\\f]",
            "[d-G\\r]", // 15.10.2.15_A1_T37
    })
    public void A1_T20(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern).exec("a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[d-G\\c0001]", // 15.10.2.15_A1_T38
            "[d-G\\x0061]", // 15.10.2.15_A1_T39
    })
    public void A1_T38(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern).exec("1");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[d-G\\u0061]", // 15.10.2.15_A1_T40
            "[d-G\\a]", // 15.10.2.15_A1_T41
    })
    public void A1_T40(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern).exec("a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }
}
