// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.

package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_13 {

    /*---
    info: |
        The production CharacterClass :: [ [lookahead \notin {^}] ClassRanges ]
        evaluates by evaluating ClassRanges to obtain a CharSet and returning
        that CharSet and the boolean false
    es5id: 15.10.2.13_A1_T1
    description: Execute /[]a/.test("\0a\0a") and check results
    ---*/
    @Test
    @DisplayName("15.10.2.13_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("[]a");
        assertThat(re.test("\0a\0a")).isFalse();
    }

    @Test
    @DisplayName("15.10.2.13_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("a[]");
        assertThat(re.test("\0a\0a")).isFalse();
    }

    @Test
    @DisplayName("15.10.2.13_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("q[ax-zb](?=\\s+)");
        String[] actual = re.exec("qYqy ");
        String[] expected = {"qy"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_T4")
    public void A1_T4() {
        RegExp re = new RegExp("q[ax-zb](?=\\s+)");
        String[] actual = re.exec("tqaqy ");
        String[] expected = {"qy"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("q[ax-zb](?=\\s+)");
        String[] actual = re.exec("tqa\t  qy ");
        String[] expected = {"qa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_T6")
    public void A1_T6() {
        RegExp re = new RegExp("ab[ercst]de");
        String[] actual = re.exec("abcde");
        String[] expected = {"abcde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_T7")
    public void A1_T7() {
        RegExp re = new RegExp("ab[erst]de");
        assertThat(re.test("abcde")).isFalse();
    }

    @Test
    @DisplayName("15.10.2.13_A1_T8")
    public void A1_T8() {
        RegExp re = new RegExp("[d-h]+");
        String[] actual = re.exec("abcdefghijkl");
        String[] expected = {"defgh"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_T9")
    public void A1_T9() {
        RegExp re = new RegExp("[1234567].{2}");
        String[] actual = re.exec("abc6defghijkl");
        String[] expected = {"6de"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_10")
    public void A1_T10() {
        RegExp re = new RegExp("[a-c\\d]+");
        // original string is "\n\n\abc324234\n"
        String[] actual = re.exec("\n\nabc324234\n");
        String[] expected = {"abc324234"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_11")
    public void A1_T11() {
        RegExp re = new RegExp("ab[.]?c");
        String[] actual = re.exec("abc");
        String[] expected = {"abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_12")
    public void A1_T12() {
        RegExp re = new RegExp("a[b]c");
        String[] actual = re.exec("abc");
        String[] expected = {"abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_13")
    public void A1_T13() {
        RegExp re = new RegExp("[a-z][^1-9][a-z]");
        String[] actual = re.exec("a1b  b2c  c3d  def  f4g");
        String[] expected = {"def"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_14")
    public void A1_T14() {
        RegExp re = new RegExp("[*&$]{3}");
        String[] actual = re.exec("123*&$abc");
        String[] expected = {"*&$"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_15")
    public void A1_T15() {
        RegExp re = new RegExp("[\\d][\\n][^\\d]");
        String[] actual = re.exec("line1\nline2");
        String[] expected = {"1\nl"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A1_17")
    public void A1_T17() {
        RegExp re = new RegExp("[]");
        String[] actual = re.exec("a[b\n[]\tc]d");
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("15.10.2.13_A2_1")
    public void A2_T1() {
        RegExp re = new RegExp("[^]a", "m");
        String[] actual = re.exec("a\naba");
        String[] expected = {"\na"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A2_2")
    public void A2_T2() {
        RegExp re = new RegExp("a[^]");
        String[] actual = re.exec("   a\t\n");
        String[] expected = {"a\t"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.13_A2_3")
    public void A2_T3() {
        RegExp re = new RegExp("a[^b-z]\\s+");
        String[] actual = re.exec("ab an az aY n");
        String[] expected = {"aY "};
        assertThat(actual).isEqualTo(expected);
    }
}
