// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_9 {

    @Test
    @DisplayName("15.10.2.9_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("\\b(\\w+) \\1\\b");
        String[] actual = re.exec("do you listen the the band");
        String[] expected = {"the the", "the"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.9_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("([xu]\\d{2}([A-H]{2})?)\\1");
        String[] actual = re.exec("x09x12x01x01u00FFu00FFx04x04x23");
        String[] expected = {"x01x01", "x01", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.9_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("([xu]\\d{2}([A-H]{2})?)\\1");
        String[] actual = re.exec("x09x12x01x05u00FFu00FFx04x04x23");
        String[] expected = {"u00FFu00FF", "u00FF", "FF"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.9_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("(a*)b\\1+");
        String[] actual = re.exec("baaac");
        String[] expected = {"b", ""};
        assertThat(actual).isEqualTo(expected);
    }
}
