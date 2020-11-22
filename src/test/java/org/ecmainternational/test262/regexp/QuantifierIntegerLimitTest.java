// Copyright (C) 2019 Mike Pennisi. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

/*---
esid: sec-quantifier
description: >
  MV of DecimalDigits evaluates to 2 ** 53 - 1.
  (although DecimalDigits could be arbitrary large integer)
info: |
  Quantifier

  The production QuantifierPrefix :: { DecimalDigits } evaluates as follows:

  1. Let i be the MV of DecimalDigits (see 11.8.3).
  2. Return the two results i and i.

  The production QuantifierPrefix :: { DecimalDigits, } evaluates as follows:

  1. Let i be the MV of DecimalDigits.
  2. Return the two results i and ∞.

  The production QuantifierPrefix :: { DecimalDigits, DecimalDigits } evaluates as follows:

  1. Let i be the MV of the first DecimalDigits.
  2. Let j be the MV of the second DecimalDigits.
  3. Return the two results i and j.
---*/
@Disabled
public class QuantifierIntegerLimitTest {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;

    @Test
    public void test1() {
        RegExp re = new RegExp("b{" + MAX_SAFE_INTEGER + "}", "u");
        assertThat(re.test("")).isFalse();
    }

    @Test
    public void test2() {
        RegExp re = new RegExp("b{" + MAX_SAFE_INTEGER + ",}?");
        assertThat(re.test("a")).isFalse();
    }

    @Test
    public void test3() {
        RegExp re = new RegExp("b{" + MAX_SAFE_INTEGER + "," + MAX_SAFE_INTEGER + "}");
        assertThat(re.test("b")).isFalse();
    }
}
