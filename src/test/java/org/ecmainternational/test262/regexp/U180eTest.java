// Copyright (C) 2017 Leonardo Balter. All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

/*---
esid: prod-CharacterClassEscape
description: >
  U+180E is no longer a Unicode `Space_Separator` symbol as of Unicode v6.3.0.
info: |
  21.2.2.12 CharacterClassEscape

  ...

  The production CharacterClassEscape::s evaluates as follows:

  Return the set of characters containing the characters that are on the
  right-hand side of the WhiteSpace or LineTerminator productions.

  The production CharacterClassEscape::S evaluates as follows:

  Return the set of all characters not included in the set returned by
  CharacterClassEscape::s .
features: [u180e]
---*/
public class U180eTest {

    @Test
    public void test() {
        // \\s should not match U+180E
        RegExp re1 = new RegExp("\\s+", "g");
        assertThat(re1.replace("\u180E", "42")).isEqualTo("\u180E");
        // \\S matches U+180E
        RegExp re2 = new RegExp("\\S+", "g");
        assertThat(re2.replace("\u180E", "42")).isEqualTo("42");
    }
}
