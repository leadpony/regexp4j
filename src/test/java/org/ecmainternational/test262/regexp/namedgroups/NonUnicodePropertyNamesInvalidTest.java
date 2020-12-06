// Copyright (C) 2020 Apple Inc. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp.namedgroups;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.leadpony.regexp4j.RegExp;
import org.leadpony.regexp4j.SyntaxError;

/*---
author: Michael Saboff
description: Invalid exotic named group names in non-Unicode RegExps
esid: prod-GroupSpecifier
features: [regexp-named-groups]
---*/
/*
Valid ID_Continue Unicode characters (Can't be first identifier character.)

𝟚  \\u{1d7da}  \ud835 \udfda

Invalid ID_Start / ID_Continue

(fox face emoji) 🦊  \\u{1f98a}  \ud83e \udd8a
(dog emoji)  🐕  \\u{1f415}  \ud83d \udc15
*/
public class NonUnicodePropertyNamesInvalidTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "(?<🦊>fox)",
            "(?<\ud83e\udd8a>fox)",
            "(?<🐕>dog)",
            "(?<\ud83d \udc15>dog)",
            "(?<𝟚the>the)",
            "(?<\ud835\udfdathe>the)"
    })
    public void shouldThrowSyntaxError(String pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern);
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
    }
}
