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
public enum InvalidPattern {
    // lookaround
    UNTERMINATED_POSITIVE_LOOKAHEAD("(?=abc"),
    UNTERMINATED_NEGATIVE_LOOKAHEAD("(?!abc"),
    UNTERMINATED_POSITIVE_LOOKBEHIND("(?<=abc"),
    UNTERMINATED_NEGATIVE_LOOKBEHIND("(?<!abc"),

    // escape
    ENDS_WITH_REVERSE_SOLIDUS("\\"),
    EMPTY_UNICODE_ESCAPE_SEQUENCE("(?<\\u>)"),
    INCOMPLETE_UNICODE_ESCAPE_SEQUENCE("(?<\\u004>)"),
    EMPTY_HEX_ESCAPE_SEQUENCE("\\x"),
    SHORT_HEX_ESCAPE_SEQUENCE("\\x4"),
    DIGIT_FOLLOWS_NULL_ESCAPE("\\01"),

    // group
    UNTERMINATED_GROUP("(abc"),
    UNTERMINATED_NAMED_GROUP("(?<name1>abc"),
    UNTERMINATED_NONCAPTURING_GROUP("(?:abc"),

    // named capturing group
    NO_GROUP_NAME("(?abc)"),
    BLANK_GROUP_NAME("(?<>abc)"),
    INCOMPLETE_GRUOP_NAME("(?<)"),
    DUPLICATE_GROUP_NAME("(?<name1>abc)(?<name1>xyz)"),
    INVALID_GROUP_NAME_START("(?<\\u0040>abc)"),
    INVALID_GROUP_NAME_CONTINUE("(?<$\\u0040>abc)"),
    INVALID_GROUP_NAME_START_WITH_SURROGATE_PAIR("(?<\uD834\uDD1E>abc)"),
    INVALID_GROUP_NAME_CONTINUE_WITH_SURROGATE_PAIR("(?<$\uD834\uDD1E>abc)"),

    // name back reference
    NO_GROUP_REFERENCE("(?<name1>abc)\\k"),
    BLANK_GROUP_REFERENCE("(?<name1>abc)\\k<>"),
    INCOMPLETE_GROUP_REFERENCE("(?<name1>abc)\\k<name1"),
    NONEXISTENT_GROUP_REFERENCE("(?<name1>abc)\\k<name2>"),

    // number back reference
    NONEXISTENT_DECIMAL_ESCAPE("(abc)\\2"),

    // bounded quantifier
    BOUNDED_QUANTIFIER_OUT_OF_ORDER("a{3,1}"),

    // character class
    UNTERMINATED_CLASS_RANGE("[a-z"),
    CLASS_RANGE_OUT_OF_ORDER("[z-a]"),
    CHARACTER_CLASS_ESCAPE_AS_LOWER_CLASS_RANGE("[\\d-9]"),
    CHARACTER_CLASS_ESCAPE_AS_UPPER_CLASS_RANGE("[1-\\d]");

    private final String source;

    InvalidPattern(String source) {
        this.source = source;
    }

    String get() {
        return source;
    }
}
