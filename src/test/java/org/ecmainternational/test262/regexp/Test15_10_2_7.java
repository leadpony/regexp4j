// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_7 {

    @Test
    @DisplayName("15.10.2.7_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("the answer is 42");
        String[] expected = {"42"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("\\d{2,4}");
        boolean actual = re.test("the 7 movie");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("the 20000 Leagues Under the Sea book");
        String[] expected = {"2000"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T4")
    public void A1_T4() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("the Fahrenheit 451 book");
        String[] expected = {"451"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("the 1984 novel");
        String[] expected = {"1984"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T6")
    public void A1_T6() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("0a0\u0031\u0031b");
        String[] expected = {"011"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T7")
    public void A1_T7() {
        RegExp re = new RegExp("\\d{2,4}");
        String[] actual = re.exec("0a0\u0031\u003122b");
        String[] expected = {"0112"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T8")
    public void A1_T8() {
        RegExp re = new RegExp("b{2,3}c");
        String[] actual = re.exec("aaabbbbcccddeeeefffff");
        String[] expected = {"bbbc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T9")
    public void A1_T9() {
        RegExp re = new RegExp("b{42,93}c");
        boolean actual = re.test("aaabbbbcccddeeeefffff");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A1_T10")
    public void A1_T10() {
        RegExp re = new RegExp("b{0,93}c");
        String[] actual = re.exec("aaabbbbcccddeeeefffff");
        String[] expected = {"bbbbc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T11")
    public void A1_T11() {
        RegExp re = new RegExp("bx{0,93}c");
        String[] actual = re.exec("aaabbbbcccddeeeefffff");
        String[] expected = {"bc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A1_T12")
    public void A1_T12() {
        RegExp re = new RegExp(".{0,93}");
        String[] actual = re.exec("weirwerdf");
        String[] expected = {"weirwerdf"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A2_T1")
    public void A2_T1() {
        RegExp re = new RegExp("\\w{3}\\d?");
        String[] actual = re.exec("CE\uFFFFL\uFFDDbox127");
        String[] expected = {"box1"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A2_T2")
    public void A2_T2() {
        RegExp re = new RegExp("\\w{3}\\d?");
        String[] actual = re.exec("CELL\uFFDDbox127");
        String[] expected = {"CEL"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A2_T3")
    public void A2_T3() {
        RegExp re = new RegExp("b{2}c");
        String[] actual = re.exec("aaabbbbcccddeeeefffff");
        String[] expected = {"bbc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A2_T4")
    public void A2_T4() {
        RegExp re = new RegExp("b{8}");
        boolean actual = re.test("aaabbbbcccddeeeefffff");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A3_T1")
    public void A3_T1() {
        RegExp re = new RegExp("\\s+java\\s+");
        String[] actual = re.exec("language  java\n");
        String[] expected = {"  java\n"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T2")
    public void A3_T2() {
        RegExp re = new RegExp("\\s+java\\s+");
        String[] actual = re.exec("\t java object");
        String[] expected = {"\t java "};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T3")
    public void A3_T3() {
        RegExp re = new RegExp("\\s+java\\s+");
        boolean actual = re.test("\t javax package");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A3_T4")
    public void A3_T4() {
        RegExp re = new RegExp("\\s+java\\s+");
        boolean actual = re.test("java\n\nobject");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A3_T5")
    public void A3_T5() {
        RegExp re = new RegExp("[a-z]+\\d+");
        String[] actual = re.exec("x 2 ff 55 x2 as1 z12 abc12.0");
        String[] expected = {"x2"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T6")
    public void A3_T6() {
        RegExp re = new RegExp("[a-z]+\\d+");
        String[] actual = re.exec("__abc123.0");
        String[] expected = {"abc123"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T7")
    public void A3_T7() {
        RegExp re = new RegExp("[a-z]+(\\d+)");
        String[] actual = re.exec("x 2 ff 55 x2 as1 z12 abc12.0");
        String[] expected = {"x2", "2"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T8")
    public void A3_T8() {
        RegExp re = new RegExp("[a-z]+(\\d+)");
        String[] actual = re.exec("__abc123.0");
        String[] expected = {"abc123", "123"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T9")
    public void A3_T9() {
        RegExp re = new RegExp("d+");
        String[] actual = re.exec("abcdddddefg");
        String[] expected = {"ddddd"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T10")
    public void A3_T10() {
        RegExp re = new RegExp("o+");
        boolean actual = re.test("abcdefg");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A3_T11")
    public void A3_T11() {
        RegExp re = new RegExp("d+");
        String[] actual = re.exec("abcdefg");
        String[] expected = {"d"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T12")
    public void A3_T12() {
        RegExp re = new RegExp("(b+)(b+)(b+)");
        String[] actual = re.exec("abbbbbbbc");
        String[] expected = {"bbbbbbb", "bbbbb", "b", "b"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T13")
    public void A3_T13() {
        RegExp re = new RegExp("(b+)(b*)");
        String[] actual = re.exec("abbbbbbbc");
        String[] expected = {"bbbbbbb", "bbbbbbb", ""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A3_T14")
    public void A3_T14() {
        RegExp re = new RegExp("b*b+");
        String[] actual = re.exec("abbbbbbbc");
        String[] expected = {"bbbbbbb"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T1")
    public void A4_T1() {
        RegExp re = new RegExp("[^\"]*");
        String[] actual = re.exec("\"beast\"-nickname");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T2")
    public void A4_T2() {
        RegExp re = new RegExp("[^\"]*");
        String[] actual = re.exec("alice said: \"don't\"");
        String[] expected = {"alice said: "};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T3")
    public void A4_T3() {
        RegExp re = new RegExp("[^\"]*");
        String[] actual = re.exec("before i start");
        String[] expected = {"before i start"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T4")
    public void A4_T4() {
        RegExp re = new RegExp("[^\"]*");
        String[] actual = re.exec("alice \"sweep\": \"don't\"");
        String[] expected = {"alice "};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T5")
    @Disabled
    public void A4_T5() {
    }

    @Test
    @DisplayName("15.10.2.7_A4_T6")
    @Disabled
    public void A4_T6() {
    }

    @Test
    @DisplayName("15.10.2.7_A4_T7")
    public void A4_T7() {
        RegExp re = new RegExp("[\"'][^\"']*[\"']");
        String[] actual = re.exec("alice cries out: 'don't'");
        String[] expected = {"'don'"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T8")
    public void A4_T8() {
        RegExp re = new RegExp("[\"'][^\"']*[\"']");
        boolean actual = re.test("alice cries out: don't");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A4_T9")
    public void A4_T9() {
        RegExp re = new RegExp("[\"'][^\"']*[\"']");
        String[] actual = re.exec("alice cries out:\"\"");
        String[] expected = {"\"\""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T10")
    public void A4_T10() {
        RegExp re = new RegExp("d*");
        String[] actual = re.exec("abcddddefg");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T11")
    public void A4_T11() {
        RegExp re = new RegExp("cd*");
        String[] actual = re.exec("abcddddefg");
        String[] expected = {"cdddd"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T12")
    public void A4_T12() {
        RegExp re = new RegExp("cx*d");
        String[] actual = re.exec("abcdefg");
        String[] expected = {"cd"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T13")
    public void A4_T13() {
        RegExp re = new RegExp("(x*)(x+)");
        String[] actual = re.exec("xxxxxxx");
        String[] expected = {"xxxxxxx","xxxxxx","x"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T14")
    public void A4_T14() {
        RegExp re = new RegExp("(\\d*)(\\d+)");
        String[] actual = re.exec("1234567890");
        String[] expected = {"1234567890","123456789","0"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T15")
    public void A4_T15() {
        RegExp re = new RegExp("(\\d*)\\d(\\d+)");
        String[] actual = re.exec("1234567890");
        String[] expected = {"1234567890","12345678","0"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T16")
    public void A4_T16() {
        RegExp re = new RegExp("(x+)(x*)");
        String[] actual = re.exec("xxxxxxx");
        String[] expected = {"xxxxxxx","xxxxxxx",""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T17")
    public void A4_T17() {
        RegExp re = new RegExp("x*y+$");
        String[] actual = re.exec("xxxxxxyyyyyy");
        String[] expected = {"xxxxxxyyyyyy"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T18")
    public void A4_T18() {
        RegExp re = new RegExp("[\\d]*[\\s]*bc.");
        String[] actual = re.exec("abcdef");
        String[] expected = {"bcd"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T19")
    public void A4_T19() {
        RegExp re = new RegExp("bc..[\\d]*[\\s]*");
        String[] actual = re.exec("abcdef");
        String[] expected = {"bcde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T20")
    public void A4_T20() {
        RegExp re = new RegExp(".*");
        String[] actual = re.exec("a1b2c3");
        String[] expected = {"a1b2c3"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A4_T21")
    public void A4_T21() {
        RegExp re = new RegExp("[xyz]*1");
        boolean actual = re.test("a0.b2.c3");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A5_T1")
    public void A5_T1() {
        RegExp re = new RegExp("java(script)?");
        String[] actual = re.exec("state: javascript is extension of ecma script");
        String[] expected = {"javascript", "script"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T2")
    public void A5_T2() {
        RegExp re = new RegExp("java(script)?");
        String[] actual = re.exec("state: java and javascript are vastly different");
        String[] expected = {"java", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T3")
    public void A5_T3() {
        RegExp re = new RegExp("java(script)?");
        boolean actual = re.test("state: both Java and JavaScript used in web development");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A5_T4")
    public void A5_T4() {
        RegExp re = new RegExp("cd?e");
        String[] actual = re.exec("abcdef");
        String[] expected = {"cde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T5")
    public void A5_T5() {
        RegExp re = new RegExp("cdx?e");
        String[] actual = re.exec("abcdef");
        String[] expected = {"cde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T6")
    public void A5_T6() {
        RegExp re = new RegExp("o?pqrst");
        String[] actual = re.exec("pqrstuvw");
        String[] expected = {"pqrst"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T7")
    public void A5_T7() {
        RegExp re = new RegExp("x?y?z?");
        String[] actual = re.exec("abcd");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T8")
    public void A5_T8() {
        RegExp re = new RegExp("x?ay?bz?c");
        String[] actual = re.exec("abcd");
        String[] expected = {"abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T9")
    public void A5_T9() {
        RegExp re = new RegExp("b?b?b?b");
        String[] actual = re.exec("abbbbc");
        String[] expected = {"bbbb"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T10")
    public void A5_T10() {
        RegExp re = new RegExp("ab?c?d?x?y?z");
        String[] actual = re.exec("123az789");
        String[] expected = {"az"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T11")
    public void A5_T11() {
        RegExp re = new RegExp("\\??\\??\\??\\??\\??");
        String[] actual = re.exec("?????");
        String[] expected = {"?????"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A5_T12")
    public void A5_T12() {
        RegExp re = new RegExp(".?.?.?.?.?.?.?");
        String[] actual = re.exec("test");
        String[] expected = {"test"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A6_T1")
    public void A6_T1() {
        RegExp re = new RegExp("b{2,}c");
        String[] actual = re.exec("aaabbbbcccddeeeefffff");
        String[] expected = {"bbbbc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A6_T2")
    public void A6_T2() {
        RegExp re = new RegExp("b{8,}c");
        boolean actual = re.test("aaabbbbcccddeeeefffff");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.7_A6_T3")
    public void A6_T3() {
        RegExp re = new RegExp("\\d{1,}");
        String[] actual = re.exec("wqe456646dsff");
        String[] expected = {"456646"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A6_T4")
    public void A6_T4() {
        RegExp re = new RegExp("(123){1,}");
        String[] actual = re.exec("123123");
        String[] expected = {"123123","123"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A6_T5")
    public void A6_T5() {
        RegExp re = new RegExp("(123){1,}x\\1");
        String[] actual = re.exec("123123x123");
        String[] expected = {"123123x123","123"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.7_A6_T6")
    public void A6_T6() {
        RegExp re = new RegExp("x{1,2}x{1,}");
        String[] actual = re.exec("xxxxxxx");
        String[] expected = {"xxxxxxx"};
        assertThat(actual).isEqualTo(expected);
    }
}
