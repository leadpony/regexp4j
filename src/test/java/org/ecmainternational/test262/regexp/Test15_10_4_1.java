// Copyright (c) 2012 Ecma International.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.

// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_4_1 {

    /*---
    es5id: 15.10.4.1-2
    description: >
        RegExp - the thrown error is SyntaxError instead of RegExpError
        when the characters of 'P' do not have the syntactic form Pattern
    ---*/
    @Test
    @DisplayName("15.10.4.1-2")
    public void test2() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("\\");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    /*---
    es5id: 15.10.4.1-3
    description: >
        RegExp - the thrown error is SyntaxError instead of RegExpError
        when 'F' contains any character other than 'g', 'i', or 'm'
    ---*/
    @Test
    @DisplayName("15.10.4.1-3")
    public void test3() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("abc", "a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    /*---
    es5id: 15.10.4.1-4
    description: RegExp - the SyntaxError is not thrown when flags is 'gim'
    ---*/
    @Test
    @DisplayName("15.10.4.1-4")
    public void test4() {
        new RegExp("abc", "gim");
    }

    /*---
    info: |
        pattern is an object R whose [[Class]] property is "RegExp" and flags
        is not undefined
    es5id: 15.10.4.1_A2_T1
    description: >
        Checking if execution of "new RegExp(pattern, "i")", where the
        pattern is "/\u0042/i", does not fail
    ---*/
    @Test
    @DisplayName("15.10.4.1_A2_T1")
    public void A2_T1() {
        RegExp re = new RegExp("\\u0042", "i");
        assertThat(re.isIgnoreCase()).isTrue();
    }
}
