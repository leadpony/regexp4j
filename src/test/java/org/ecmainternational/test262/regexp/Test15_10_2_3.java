// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_3 {

    @Test
    @DisplayName("15.10.2.3_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("a|ab");
        String[] actual = re.exec("abc");
        String[] expected = {"a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("((a)|(ab))((c)|(bc))");
        String[] actual = re.exec("abc");
        String[] expected = {"abc", "a", "a", null, "bc", null, "bc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("\\d{3}|[a-z]{4}");
        String[] actual = re.exec("2, 12 and of course repeat 12");
        String[] expected = {"cour"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T4")
    public void A1_T4() {
        RegExp re = new RegExp("\\d{3}|[a-z]{4}");
        String[] actual = re.exec("2, 12 and 234 AND of course repeat 12");
        String[] expected = {"234"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T6")
    public void A1_T6() {
        RegExp re = new RegExp("ab|cd|ef", "i");
        String[] actual = re.exec("AEKFCD");
        String[] expected = {"CD"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T7")
    public void A1_T7() {
        RegExp re = new RegExp("ab|cd|ef");
        boolean actual = re.test("AEKFCD");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.3_A1_T8")
    public void A1_T8() {
        RegExp re = new RegExp("(?:ab|cd)+|ef", "i");
        String[] actual = re.exec("AEKFCD");
        String[] expected = {"CD"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T9")
    public void A1_T9() {
        RegExp re = new RegExp("(?:ab|cd)+|ef", "i");
        String[] actual = re.exec("AEKFCDab");
        String[] expected = {"CDab"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T10")
    public void A1_T10() {
        RegExp re = new RegExp("(?:ab|cd)+|ef", "i");
        String[] actual = re.exec("AEKeFCDab");
        String[] expected = {"eF"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T11")
    public void A1_T11() {
        RegExp re = new RegExp("11111|111");
        String[] actual = re.exec("1111111111111111");
        String[] expected = {"11111"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T12")
    public void A1_T12() {
        RegExp re = new RegExp("xyz|...");
        String[] actual = re.exec("abc");
        String[] expected = {"abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T13")
    public void A1_T13() {
        RegExp re = new RegExp("(.)..|abc");
        String[] actual = re.exec("abc");
        String[] expected = {"abc", "a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T14")
    public void A1_T14() {
        RegExp re = new RegExp(".+: gr(a|e)y");
        String[] actual = re.exec("color: grey");
        String[] expected = {"color: grey", "e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T15")
    public void A1_T15() {
        RegExp re = new RegExp("(Rob)|(Bob)|(Robert)|(Bobby)");
        String[] actual = re.exec("Hi Bob");
        String[] expected = {"Bob", null, "Bob", null, null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T16")
    public void A1_T16() {
        RegExp re = new RegExp("()|");
        String[] actual = re.exec("");
        String[] expected = {"", ""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.3_A1_T17")
    public void A1_T17() {
        RegExp re = new RegExp("|()");
        String[] actual = re.exec("");
        String[] expected = {"", null};
        assertThat(actual).isEqualTo(expected);
    }
}
