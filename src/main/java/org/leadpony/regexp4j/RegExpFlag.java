/*
 * Copyright 2020 the original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.regexp4j;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author leadpony
 */
enum RegExpFlag {
    GLOBAL('g'),
    IGNORE_CASE('i'),
    MULTILINE('m'),
    DOT_ALL('s'),
    UNICODE('u'),
    STICKY('y');

    final char letter;

    RegExpFlag(char letter) {
        this.letter = letter;
    }

    static Set<RegExpFlag> parse(String flags) {
        if (flags == null) {
            return Collections.emptySet();
        }

        EnumSet<RegExpFlag> result = EnumSet.noneOf(RegExpFlag.class);
        final int len = flags.length();
        for (int i = 0; i < len; i++) {
            RegExpFlag flag = parseFlag(flags.charAt(i));
            if (result.contains(flag)) {
                throw new SyntaxError(Message.thatFlagsAreInvalid(flags));
            }
            result.add(flag);
        }
        return result;
    }

    /**
     * Parses a letter in the flags.
     *
     * @param flag a letter in the flags
     * @return {@code RegExpFlag} object if {@code flag} is supported
     * @throws SyntaxError if {@code flag} is unsupported
     */
    private static RegExpFlag parseFlag(char flag) {
        switch (flag) {
        case 'g':
            return GLOBAL;
        case 'i':
            return IGNORE_CASE;
        case 'm':
            return MULTILINE;
        case 's':
            return DOT_ALL;
        case 'u':
            return UNICODE;
        case 'y':
            return STICKY;
        default:
            throw new SyntaxError(Message.thatFlagIsUnsupported(flag));
        }
    }
}
