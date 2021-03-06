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

/**
 * @author leadpony
 */
public enum ValidPattern {
    INPUT_START("^"),
    INPUT_END("$", false),
    WORD_BOUNDARY("\\b"),
    NONWORD_BOUNDARY("\\B"),

    POSITIVE_LOOKAHEAD("(?=abc)"),
    NEGATIVE_LOOKAHEAD("(?!abc)"),
    POSITIVE_LOOKBEHIND("(?<=abc)"),
    NEGATIVE_LOOKBEHIND("(?<!abc)"),

    ANY("."),

    CAPRUING_GROUP("(abc)"),
    NAMED_CAPTURING_GROUP("(?<name1>abc)", false),
    NAMED_CAPTURING_GROUP_WITH_ESCAPE("(?<\\u0041>abc)", false),
    NONCAPTURING_GROUP("(?:abc)"),

    ZERO_OR_ONE("a?"),
    ZERO_OR_MORE("a*"),
    ONE_OR_MORE("a+"),

    ZERO_OR_ONE_RELUCTANT("a??"),
    ZERO_OR_MORE_RELUCTANT("a*?"),
    ONE_OR_MORE_RELUCTANT("a+?"),

    EXACT_QUANTIFIER("a{3}"),
    LOWER_QUANTIFIER("a{3,}"),
    BOUNDS_QUANTIFIER("a{3,5}"),

    NAMED_CAPTURING_GROUP_REFERENCE("(?<name1>abc)\\k<name1>", false),

    CHARACTER_CLASS("[a-z]"),
    CHARACTER_CLASS_NEGATED("[^a-z]"),
    EMPTY_CHARACTER_CLASS("[]", false),

    HYPHEN_IN_CHARACTER_CLASS("[-]"),
    HYPHEN_IN_NEGATED_CHARACTER_CLASS("[^-]"),

    ESCAPED_TAB("\\t"),
    ESCAPED_NEWLINE("\\n"),
    ESCAPED_VERTICAL_TAB("\\v", false),
    ESCAPED_FORM_FEED("\\f"),
    ESCAPED_RETURN("\\r"),

    CONTROL_LETTER("\\cA"),
    CONTROL_LETTER_LOWER_CASE("\\ca", false),

    DECIMAL_ESCAPE("(abc)\\1"),
    HEX_ESCAPE_SEQUENCE("\\x3f"),
    NULL_ESCAPE("\\0", false),

    CHARACTER_CLASS_ESCAPE_DIGIT("\\d"),
    CHARACTER_CLASS_ESCAPE_NONDIGIT("\\D"),
    CHARACTER_CLASS_ESCAPE_WORD("\\w"),
    CHARACTER_CLASS_ESCAPE_NONWORD("\\W"),
    CHARACTER_CLASS_ESCAPE_WHITESPACE("\\s", false),
    CHARACTER_CLASS_ESCAPE_NONWHITESPACE("\\S", false),

    IDENTITY_ESCAPE("\\\\"),

    ALTERNATION("apple|grape|oranage");

    private final String pattern;
    private final boolean same;

    ValidPattern(String pattern) {
        this(pattern, true);
    }

    ValidPattern(String pattern, boolean same) {
        this.pattern = pattern;
        this.same = same;
    }

    String get() {
        return pattern;
    }

    boolean isSame() {
        return same;
    }
}
