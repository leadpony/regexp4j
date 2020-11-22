// Copyright (c) 2012 Ecma International.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_2_5 {

    /*---
    es5id: 15.10.2.5-3-1
    description: >
        Term - SyntaxError was thrown when max is finite and less than min
        (15.10.2.5 step 3)
    ---*/
    @Test
    @DisplayName("15.10.2.5-3-1")
    public void test3_1() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("0{2,1}");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }
}
