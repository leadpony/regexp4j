// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_11 {

    /*---
    info: "DecimalEscape :: DecimalIntegerLiteral [lookahead not in DecimalDigit]"
    es5id: 15.10.2.11_A1_T1
    description: >
        DecimalEscape :: 0. If i is zero, return the EscapeValue
        consisting of a <NUL> character (Unicodevalue0000)
    ---*/
    @Test
    @DisplayName("15.10.2.11_A1_T1")
    public void A1_T1() {
        String[] actual = new RegExp("\\0").exec("\u0000");
        String[] expected = {"\u0000"};
        assertThat(actual).isEqualTo(expected);
    }

    /*---
    info: "DecimalEscape :: DecimalIntegerLiteral [lookahead not in DecimalDigit]"
    es5id: 15.10.2.11_A1_T4
    description: DecimalIntegerLiteral is not 0
    ---*/
    @Test
    @DisplayName("15.10.2.11_A1_T4")
    public void A1_T4() {
        String[] actual = new RegExp("(A)\\1").exec("AA");
        String[] expected = {"AA", "A"};
        assertThat(actual).isEqualTo(expected);
    }

    /*---
    info: "DecimalEscape :: DecimalIntegerLiteral [lookahead not in DecimalDigit]"
    es5id: 15.10.2.11_A1_T5
    description: DecimalIntegerLiteral is not 0
    ---*/
    @Test
    @DisplayName("15.10.2.11_A1_T5")
    @Disabled
    public void A1_T5() {
        RegExp re = new RegExp("\\1(A)");
        String[] actual = re.exec("AA");
        String[] expected = {"A", "A"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.11_A1_T6")
    public void A1_T6() {
        RegExp re = new RegExp("(A)\\1(B)\\2");
        String[] actual = re.exec("AABB");
        String[] expected = {"AABB", "A", "B"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.11_A1_T7")
    @Disabled
    public void A1_T7() {
        RegExp re = new RegExp("\\1(A)(B)\\2");
        String[] actual = re.exec("AABB");
        String[] expected = {"ABB", "A", "B"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.11_A1_T8")
    public void A1_T8() {
        RegExp re = new RegExp("((((((((((A))))))))))\\1\\2\\3\\4\\5\\6\\7\\8\\9\\10");
        String[] actual = re.exec("AAAAAAAAAAA");
        String[] expected = {"AAAAAAAAAAA", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.11_A1_T9")
    public void A1_T9() {
        RegExp re = new RegExp("((((((((((A))))))))))\\10\\9\\8\\7\\6\\5\\4\\3\\2\\1");
        String[] actual = re.exec("AAAAAAAAAAA");
        String[] expected = {"AAAAAAAAAAA", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A"};
        assertThat(actual).isEqualTo(expected);
    }
}
