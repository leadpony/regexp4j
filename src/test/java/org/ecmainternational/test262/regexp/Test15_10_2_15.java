// Copyright (c) 2012 Ecma International.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_2_15 {

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
}
