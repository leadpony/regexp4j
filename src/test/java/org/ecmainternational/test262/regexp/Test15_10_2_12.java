// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_12 {

    /*---
    info: |
        The production CharacterClassEscape :: w evaluates by returning the set of characters containing the sixty-three characters:
        a - z, A - Z, 0 - 9, _
    es5id: 15.10.2.12_A3_T5
    description: non-w
    ---*/
    @Test
    @DisplayName("15.10.2.12_A3_T5")
    public void A3_T5() {
        String non_w = "\f\n\r\t\013~`!@#$%^&*()-+={[}]|\\:;'<,>./? \"";
        assertThat(new RegExp("\\w").exec(non_w)).isNull();

        String non_W = "_0123456789_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int k = 0;
        RegExp re = new RegExp("\\w", "g");
        while (re.exec(non_W) != null) {
            k++;
        }
        assertThat(k).isEqualTo(non_W.length());
    }

    /*---
    info: |
        The production CharacterClassEscape :: W evaluates by returning the set of all characters not
        included in the set returned by CharacterClassEscape :: w
    es5id: 15.10.2.12_A4_T5
    description: non-w
    ---*/
    @Test
    @DisplayName("15.10.2.12_A4_T5")
    public void A4_T5() {
        String non_w = "\f\n\r\t\013~`!@#$%^&*()-+={[}]|\\:;'<,>./? \"";
        int k = 0;
        RegExp re = new RegExp("\\W", "g");
        while (re.exec(non_w) != null) {
            k++;
        }
        assertThat(k).isEqualTo(non_w.length());

        String non_W = "_0123456789_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        assertThat(new RegExp("\\W").exec(non_W)).isNull();
    }
}
