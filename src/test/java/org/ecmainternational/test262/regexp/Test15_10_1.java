// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

public class Test15_10_1 {

    @Test
    @DisplayName("15.10.1_A1_T1")
    public void A1_T1() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a**");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T2")
    public void A1_T2() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a***");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T3")
    public void A1_T3() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a++");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T4")
    public void A1_T4() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a+++");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T5")
    public void A1_T5() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a???");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T6")
    public void A1_T6() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("a????");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T7")
    public void A1_T7() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("*a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T8")
    public void A1_T8() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("**a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T9")
    public void A1_T9() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("+a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T10")
    public void A1_T10() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("++a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T11")
    public void A1_T11() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("?a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T12")
    public void A1_T12() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("??a");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T13")
    public void A1_T13() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("x{1}{1,}");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T14")
    public void A1_T14() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("x{1,2}{1}");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T15")
    public void A1_T15() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("x{1,}{1}");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }

    @Test
    @DisplayName("15.10.1_A1_T16")
    public void A1_T16() {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("x{0,1}{1,}");
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }
}
