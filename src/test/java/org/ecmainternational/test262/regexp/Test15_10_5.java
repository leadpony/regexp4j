// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_5 {

    @Test
    @DisplayName("15.10.2.5_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("a[a-z]{2,4}");
        String[] actual = re.exec("abcdefghi");
        String[] expected = {"abcde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.5_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("a[a-z]{2,4}?");
        String[] actual = re.exec("abcdefghi");
        String[] expected = {"abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.5_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("(aa|aabaac|ba|b|c)*");
        String[] actual = re.exec("aabaac");
        String[] expected = {"aaba", "ba"};
        assertThat(actual).isEqualTo(expected);
    }

    /*---
    info: |
        An Atom followed by a Quantifier is repeated the number of times
        specified by the Quantifier
    es5id: 15.10.2.5_A1_T4
    description: Execute /(z)((a+)?(b+)?(c))* /.exec("zaacbbbcac") and check results
    ---*/
    @Test
    @DisplayName("15.10.2.5_A1_T4")
    @Disabled
    public void A1_T4() {
        RegExp re = new RegExp("(z)((a+)?(b+)?(c))*");
        String[] actual = re.exec("zaacbbbcac");
        String[] expected = {"zaacbbbcac", "z", "ac", "a", null, "c"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.5_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("(a*)b\\1+");
        String[] actual = re.exec("baaaac");
        String[] expected = {"b", ""};
        assertThat(actual).isEqualTo(expected);
    }
}
