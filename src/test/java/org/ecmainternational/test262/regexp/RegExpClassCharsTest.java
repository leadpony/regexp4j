// Copyright (C) 2019 Mike Pennisi. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

/*---
description: RegularExpressionClassChars may include the forward slash character
info: |
  11.8.5Regular Expression Literals

  RegularExpressionClass ::
    [ RegularExpressionClassChars ]

  RegularExpressionClassChars ::
    [empty]
    RegularExpressionClassChars RegularExpressionClassChar

  RegularExpressionClassChar ::
    RegularExpressionNonTerminator but not one of ] or \
    RegularExpressionBackslashSequence

  RegularExpressionNonTerminator ::
    SourceCharacterbut not LineTerminator
esid: sec-literals-regular-expression-literals
---*/
public class RegExpClassCharsTest {

    @Test
    public void testForwardSlash() {
        RegExp re = new RegExp("[/]");
        assertThat(re.test("/")).isTrue();
        assertThat(re.test("x")).isFalse();
    }

    @Test
    public void testForwardSlashRepeated() {
        RegExp re = new RegExp("[//]");
        assertThat(re.test("/")).isTrue();
        assertThat(re.test("x")).isFalse();
    }
}
