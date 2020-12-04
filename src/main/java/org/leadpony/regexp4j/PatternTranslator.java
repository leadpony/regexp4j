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

import java.util.Set;
import java.util.regex.Pattern;

/**
 * A visitor who will translate the pattern into a {@link Pattern} object in
 * Java.
 *
 * @author leadpony
 */
class PatternTranslator implements PatternVisitor {

    /*
     * Whitespace: U+0009, U+000B, U+000C, U+0020, U+0009, U+00a0, U+FEFF, and other
     * Space_Separator(Zs)
     *
     * Line Terminator: U+000A, U+000D, U+2028, U+2029
     */
    private static final String WHITESPACE_CHARACTERS =
            // Whitespace
            "\\u0009\\u000b\\u000c\\u0020\\u00a0\\ufeff"
            // Space_Separator (Zs)
            // \\p{gc=Zs}
            + "\\u1680\\u2000-\\u200a\\u202f\\u205f\\u3000"
            // Line Terminator
            + "\\u000a\\u000d\\u2028\\u2029";

    private static final String WHITESPACE_CHARACTER_CLASS = "[" + WHITESPACE_CHARACTERS + "]";

    private static final String NONWHITESPACE_CHARACTER_CLASS = "[^" + WHITESPACE_CHARACTERS + "]";

    protected final StringBuilder builder = new StringBuilder();

    private final int options;

    PatternTranslator(Set<RegExpFlag> flags) {
        int options = 0;
        if (flags.contains(RegExpFlag.IGNORE_CASE)) {
            options |= Pattern.CASE_INSENSITIVE;
        }
        if (flags.contains(RegExpFlag.MULTILINE)) {
            options |= Pattern.MULTILINE;
        }
        this.options = options;
    }

    /**
     * Returns the translated pattern.
     *
     * @return the translated pattern.
     * @throws PatternSyntaxException if the compilation failed.
     */
    Pattern getPattern() {
        return Pattern.compile(builder.toString(), options);
    }

    @Override
    public void visitAlternation() {
        builder.append('|');
    }

    @Override
    public void visitInputStart() {
        builder.append('^');
    }

    @Override
    public void visitInputEnd() {
        builder.append("\\z");
    }

    @Override
    public void visitWordBoundary() {
        builder.append("\\b");
    }

    @Override
    public void visitNonwordBoundary() {
        builder.append("\\B");
    }

    @Override
    public void visitCapturingGroup() {
        builder.append('(');
    }

    @Override
    public void visitNamedCapturingGroup(String name) {
        builder.append("(?<").append(name).append('>');
    }

    @Override
    public void visitNoncapturingGroup() {
        builder.append("(?:");
    }

    @Override
    public void visitLookaheadGroup(boolean positive) {
        if (positive) {
            builder.append("(?=");
        } else {
            builder.append("(?!");
        }
    }

    @Override
    public void visitLookbehindGroup(boolean positive) {
        if (positive) {
            builder.append("(?<=");
        } else {
            builder.append("(?<!");
        }
    }

    @Override
    public void visitGroupEnd() {
        builder.append(')');
    }

    @Override
    public void visitCharacter(char c) {
        builder.append(c);
    }

    @Override
    public void visitCharacter(int codePoint) {
        builder.appendCodePoint(codePoint);
    }

    @Override
    public void visitAnyCharacter() {
        builder.append('.');
    }

    @Override
    public void visitQuantifier(char c, boolean reluctant) {
        builder.append(c);
        if (reluctant) {
            builder.append('?');
        }
    }

    @Override
    public void visitBoundedQuantifier(long count, boolean reluctant) {
        builder.append('{').append(count).append('}');
        if (reluctant) {
            builder.append('?');
        }
    }

    @Override
    public void visitBoundedQuantifier(long lower, long upper, boolean reluctant) {
        builder.append('{').append(lower).append(',');
        if (upper >= 0) {
            builder.append(upper);
        }
        builder.append('}');
        if (reluctant) {
            builder.append('?');
        }
    }

    @Override
    public void visitNamedCapturingGroupReference(String name) {
        builder.append("\\k").append('<').append(name).append('>');
    }

    @Override
    public void visitClassStart(boolean negated) {
        if (negated) {
            builder.append("[^");
        } else {
            builder.append('[');
        }
    }

    @Override
    public void visitClassEnd() {
        builder.append(']');
    }

    @Override
    public void visitEmptyClass(boolean negated) {
        if (negated) {
            builder.append("[a[^a]]");
        } else {
            builder.append("[a&&[^a]]");
        }
    }

    @Override
    public void visitRangeDash() {
        builder.append('-');
    }

    @Override
    public void visitControlEscape(char c) {
        builder.append('\\');
        if (c == 'v') {
            builder.append("x0b");
        } else {
            builder.append(c);
        }
    }

    @Override
    public void visitHexEscapeSequence(char high, char low) {
        builder.append("\\x").append(high).append(low);
    }

    @Override
    public void visitUnicodeEscapeSequence(char c) {
        builder.append("\\u").append(String.format("%04x", (int) c));
    }

    @Override
    public void visitControlLetter(char c) {
        builder.append('\\').append('c');
        if ('A' <= c && c <= 'Z') {
            builder.append(c);
        } else {
            builder.append((char) ('A' + c - 'a'));
        }
    }

    @Override
    public void visitDecimalEscape(int value) {
        builder.append('\\').append(value);
    }

    @Override
    public void visitCharacterClassEscape(char c) {
        switch (c) {
        case 'd':
        case 'D':
        case 'w':
        case 'W':
            builder.append('\\').append(c);
            break;
        case 's':
            builder.append(WHITESPACE_CHARACTER_CLASS);
            break;
        case 'S':
            builder.append(NONWHITESPACE_CHARACTER_CLASS);
            break;
        default:
            break;
        }
    }

    @Override
    public void visitNullEscape() {
        builder.append("\\00");
    }

    @Override
    public void visitIdentityEscape(char c) {
        builder.append('\\').append(c);
    }
}
