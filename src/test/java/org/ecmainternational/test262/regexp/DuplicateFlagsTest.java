// Copyright 2017 the V8 project authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.

package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.logging.Logger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

/*---
info: |
    RegExpInitialize ( obj, pattern, flags )
      5. If F contains any code unit other than "g", "i", "m", "s", "u", or "y" or if it contains the same code unit more than once, throw a SyntaxError exception.
esid: sec-regexpinitialize
description: Check that duplicate RegExp flags are disallowed
features: [regexp-dotall]
---*/
public class DuplicateFlagsTest {

    private static final Logger LOG = Logger.getLogger(DuplicateFlagsTest.class.getName());

    @ParameterizedTest
    @ValueSource(strings = {
            "mig",
            "i",
            "m",
            "s",
            "u",
            "y"
    })
    public void shouldNotThrowSyntaxError(String flags) {
        new RegExp("", flags);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "migg",
            "ii",
            "mm",
            "ss",
            "uu",
            "yy"
    })
    public void shouldThrowSyntaxError(String flags) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("", flags);
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);

        LOG.info(thrown.getMessage());
    }
}
