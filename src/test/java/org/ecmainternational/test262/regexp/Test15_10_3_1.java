// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_3_1 {

    /*---
    info: |
        If pattern is an object R whose [[Class]] property is "RegExp" and flags is defined, then
        call the RegExp constructor (15.10.4.1), passing it the pattern and flags arguments and return the object constructed by that constructor
    es5id: 15.10.3.1_A2_T1
    description: >
        Checking if using "1" as flags leads to throwing the correct
        exception
    ---*/
    @Test
    @DisplayName("15.10.3.1_A2_T1")
    public void A2_T1() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("\\d", "1");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.3.1_A3_T1")
    public void A3_T1() {
        String pattern = "d+";
        RegExp re = new RegExp(pattern, "i");
        assertThat(re.getSource()).isEqualTo(pattern);
    }
}
