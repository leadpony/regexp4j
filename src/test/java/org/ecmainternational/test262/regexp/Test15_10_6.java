// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_6 {

    @Test
    @DisplayName("15.10.2.6_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("s$");
        boolean actual = re.test("pairs\nmakes\tdouble");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("e$");
        String[] actual = re.exec("pairs\nmakes\tdouble");
        String[] expected = {"e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("s$", "m");
        String[] actual = re.exec("pairs\nmakes\tdouble");
        String[] expected = {"s"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A1_T4")
    public void A1_T4() {
        RegExp re = new RegExp("[^e]$", "mg");
        String[] actual = re.exec("pairs\nmakes\tdouble");
        String[] expected = {"s"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("es$", "mg");
        String[] actual = re.exec("pairs\nmakes\tdoubl\u0065s");
        String[] expected = {"es"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T1")
    public void A2_T1() {
        RegExp re = new RegExp("^m");
        boolean actual = re.test("pairs\nmakes\tdouble");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A2_T2")
    public void A2_T2() {
        RegExp re = new RegExp("^m", "m");
        String[] actual = re.exec("pairs\nmakes\tdouble");
        String[] expected = {"m"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T3")
    public void A2_T3() {
        RegExp re = new RegExp("^p[a-z]");
        String[] actual = re.exec("pairs\nmakes\tdouble\npesos");
        String[] expected = {"pa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T4")
    public void A2_T4() {
        RegExp re = new RegExp("^p[b-z]", "m");
        String[] actual = re.exec("pairs\nmakes\tdouble\npesos");
        String[] expected = {"pe"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T5")
    public void A2_T5() {
        RegExp re = new RegExp("^[^p]", "m");
        String[] actual = re.exec("pairs\nmakes\tdouble\npesos");
        String[] expected = {"m"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T6")
    public void A2_T6() {
        RegExp re = new RegExp("^ab");
        String[] actual = re.exec("abcde");
        String[] expected = {"ab"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T7")
    public void A2_T7() {
        RegExp re = new RegExp("^..^e");
        boolean actual = re.test("ab\ncde");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A2_T8")
    public void A2_T8() {
        RegExp re = new RegExp("^xxx");
        boolean actual = re.test("yyyyy");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A2_T9")
    public void A2_T9() {
        RegExp re = new RegExp("^\\^+");
        String[] actual = re.exec("^^^x");
        String[] expected = {"^^^"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A2_T10")
    public void A2_T10() {
        RegExp re = new RegExp("^\\d+", "m");
        String[] actual = re.exec("abc\n123xyz");
        String[] expected = {"123"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T1")
    public void A3_T1() {
        RegExp re = new RegExp("\\bp");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"p"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T2")
    public void A3_T2() {
        RegExp re = new RegExp("ot\\b");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"ot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T3")
    public void A3_T3() {
        RegExp re = new RegExp("\\bot");
        boolean actual = re.test("pilot\nsoviet robot\topenoffice");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A3_T4")
    public void A3_T4() {
        RegExp re = new RegExp("\\bso");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"so"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T5")
    public void A3_T5() {
        RegExp re = new RegExp("so\\b");
        boolean actual = re.test("pilot\nsoviet robot\topenoffice");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A3_T6")
    public void A3_T6() {
        RegExp re = new RegExp("[^o]t\\b");
        String[] actual = re.exec("pilOt\nsoviet robot\topenoffice");
        String[] expected = {"Ot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T7")
    public void A3_T7() {
        RegExp re = new RegExp("[^o]t\\b", "i");
        String[] actual = re.exec("pilOt\nsoviet robot\topenoffice");
        String[] expected = {"et"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T8")
    public void A3_T8() {
        RegExp re = new RegExp("\\bro");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"ro"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T9")
    public void A3_T9() {
        RegExp re = new RegExp("r\\b");
        boolean actual = re.test("pilot\nsoviet robot\topenoffice");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A3_T10")
    public void A3_T10() {
        RegExp re = new RegExp("\\brobot\\b");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"robot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T11")
    public void A3_T11() {
        RegExp re = new RegExp("\\b\\w{5}\\b");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"pilot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T12")
    public void A3_T12() {
        RegExp re = new RegExp("\\bop");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffice");
        String[] expected = {"op"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T13")
    public void A3_T13() {
        RegExp re = new RegExp("op\\b");
        boolean actual = re.test("pilot\nsoviet robot\topenoffice");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A3_T14")
    public void A3_T14() {
        RegExp re = new RegExp("e\\b");
        String[] actual = re.exec("pilot\nsoviet robot\topenoffic\u0065");
        String[] expected = {"e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A3_T15")
    public void A3_T15() {
        RegExp re = new RegExp("\\be");
        boolean actual = re.test("pilot\nsoviet robot\topenoffic\u0065");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.6_A4_T1")
    public void A4_T1() {
        RegExp re = new RegExp("\\Bevil\\B");
        String[] actual = re.exec("devils arise\tfor\nevil");
        String[] expected = {"evil"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T2")
    public void A4_T2() {
        RegExp re = new RegExp("[f-z]e\\B");
        String[] actual = re.exec("devils arise\tfor\nrevil");
        String[] expected = {"re"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T3")
    public void A4_T3() {
        RegExp re = new RegExp("\\Bo\\B", "i");
        String[] actual = re.exec("devils arise\tfOr\nrevil");
        String[] expected = {"O"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T4")
    public void A4_T4() {
        RegExp re = new RegExp("\\B\\w\\B");
        String[] actual = re.exec("devils arise\tfor\nrevil");
        String[] expected = {"e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T5")
    public void A4_T5() {
        RegExp re = new RegExp("\\w\\B");
        String[] actual = re.exec("devils arise\tfor\nrevil");
        String[] expected = {"d"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T6")
    public void A4_T6() {
        RegExp re = new RegExp("\\B\\w");
        String[] actual = re.exec("devils arise\tfor\nrevil");
        String[] expected = {"e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T7")
    public void A4_T7() {
        RegExp re = new RegExp("\\B[^z]{4}\\B");
        String[] actual = re.exec("devil arise\tforzzx\nevils");
        String[] expected = {"il a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A4_T8")
    public void A4_T8() {
        RegExp re = new RegExp("\\B\\w{4}\\B");
        String[] actual = re.exec("devil arise\tforzzx\nevils");
        String[] expected = {"orzz"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A5_T1")
    public void A5_T1() {
        RegExp re = new RegExp("^^^^^^^robot$$$$");
        String[] actual = re.exec("robot");
        String[] expected = {"robot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A5_T2")
    public void A5_T2() {
        RegExp re = new RegExp("\\B\\B\\B\\B\\B\\Bbot\\b\\b\\b\\b\\b\\b\\b");
        String[] actual = re.exec("robot wall-e");
        String[] expected = {"bot"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A6_T1")
    public void A6_T1() {
        RegExp re = new RegExp("^.*?$");
        String[] actual = re.exec("Hello World");
        String[] expected = {"Hello World"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A6_T2")
    public void A6_T2() {
        RegExp re = new RegExp("^.*?");
        String[] actual = re.exec("Hello World");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A6_T3")
    public void A6_T3() {
        RegExp re = new RegExp("^.*?(:|$)");
        String[] actual = re.exec("Hello: World");
        String[] expected = {"Hello:", ":"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.6_A6_T4")
    public void A6_T4() {
        RegExp re = new RegExp("^.*(:|$)");
        String[] actual = re.exec("Hello: World");
        String[] expected = {"Hello: World", ""};
        assertThat(actual).isEqualTo(expected);
    }
}
