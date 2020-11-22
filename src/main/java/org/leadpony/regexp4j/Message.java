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

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Messages.
 *
 * @author leadpony
 */
enum Message {
    UNEXPECTED_END,
    UNTERMINATED_GROUP,
    INVALID_GROUP,
    INVALID_CAPTURING_GROUP_NAME,
    DUPLICATE_CAPTURING_GROUP_NAME,
    INVALID_NAMED_CAPTURING_GROUP_REFERNCE,
    NAMED_CAPTURING_GROUP_NOT_FOUND,
    CAPTURING_GROUP_NUMBER_OUT_OF_BOUNDS,

    // quantifier
    INVALID_QUANTIFIER,
    UNTERMINATED_QUANTIFIER,
    QUANTIFIER_BOUNDS_OUT_OF_ORDER,

    // escape
    INVALID_UNICODE_ESCAPE_SEQUENCE,
    INVALID_HEX_ESCAPE_SEQUENCE,
    DIGIT_FOLLOWS_NULL_ESCAPE,

    // character class range
    UNTERMINATED_CHARACTER_CLASS_RANGE,
    INVALID_CHARACTER_CLASS_RANGE,
    CHARACER_CLASS_RANGE_OUT_OF_ORDER,

    // flags
    INVALID_FLAGS,
    UNSUPPORTED_FLAG,

    // others
    INTERNAL_ERROR;

    private static final String BUNDLE_BASE_NAME = Message.class.getPackage().getName().concat(".messages");

    static String thatPatternEndsWith(char c) {
        return UNEXPECTED_END.format(c);
    }

    static String thatGroupIsUnterminated() {
        return UNTERMINATED_GROUP.message();
    }

    static String thatGroupIsInvalid() {
        return INVALID_GROUP.message();
    }

    static String thatCapuringGroupNameIsInvalid() {
        return INVALID_CAPTURING_GROUP_NAME.message();
    }

    static String thatCapuringGroupNameIsDuplicated(String name) {
        return DUPLICATE_CAPTURING_GROUP_NAME.format(name);
    }

    static String thatNamedCapturingGroupReferenceIsInvalid() {
        return INVALID_NAMED_CAPTURING_GROUP_REFERNCE.message();
    }

    static String thatCapturingGroupNumberIsOutOfBounds(int number, int maxNumber) {
        return CAPTURING_GROUP_NUMBER_OUT_OF_BOUNDS.format(maxNumber, number);
    }

    static String thatNamedCapturingGroupIsNotFound() {
        return NAMED_CAPTURING_GROUP_NOT_FOUND.message();
    }

    static String thatQuantifierIsInvalid() {
        return INVALID_QUANTIFIER.message();
    }

    static String thatQuantifierIsUnterminated() {
        return UNTERMINATED_QUANTIFIER.message();
    }

    static String thatQuantifierBoundsAreOutOfOrder() {
        return QUANTIFIER_BOUNDS_OUT_OF_ORDER.message();
    }

    static String thatUnicodeEscapeSequenceIsInvalid() {
        return INVALID_UNICODE_ESCAPE_SEQUENCE.message();
    }

    static String thatHexEscapeSequenceIsInvalid() {
        return INVALID_HEX_ESCAPE_SEQUENCE.message();
    }

    static String thatNullEscapeWasFollowedByDigit(char c) {
        return DIGIT_FOLLOWS_NULL_ESCAPE.format(c);
    }

    static String thatCharacterClassIsUnterminated() {
        return UNTERMINATED_CHARACTER_CLASS_RANGE.message();
    }

    static String thatCharacterClassRangeIsInvalid() {
        return INVALID_CHARACTER_CLASS_RANGE.message();
    }

    static String thatCharacterClassRangeIsOutOfOrder() {
        return CHARACER_CLASS_RANGE_OUT_OF_ORDER.message();
    }

    static String thatFlagsAreInvalid(String flags) {
        return INVALID_FLAGS.format(flags);
    }

    static String thatFlagIsUnsupported(char flag) {
        return UNSUPPORTED_FLAG.format(flag);
    }

    static String thatInternalErrorOccurred() {
        return INTERNAL_ERROR.message();
    }

    // helper methods

    String message() {
        return getBundle().getString(name());
    }

    private String format(Object... arguments) {
        return MessageFormat.format(message(), arguments);
    }

    private static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME);
    }
}
