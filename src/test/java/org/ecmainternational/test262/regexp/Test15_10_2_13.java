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
}
