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
interface PatternVisitor {

    PatternVisitor EMPTY_VISITOR = new PatternVisitor() { };

    default void visitAlternation() {
    }

    default void visitInputStart() {
    }

    default void visitInputEnd() {
    }

    default void visitWordBoundary() {
    }

    default void visitNonwordBoundary() {
    }

    default void visitCapturingGroup() {
    }

    default void visitNamedCapturingGroup(String name) {
    }

    default void visitNoncapturingGroup() {
    }

    default void visitLookaheadGroup(boolean positive) {
    }

    default void visitLookbehindGroup(boolean positive) {
    }

    default void visitGroupEnd() {
    }

    default void visitCharacter(char c) {
    }

    default void visitCharacter(int codePoint) {
    }

    default void visitAnyCharacter() {
    }

    default void visitQuantifier(char c, boolean reluctant) {
    }

    default void visitBoundedQuantifier(long count, boolean reluctant) {
    }

    default void visitBoundedQuantifier(long lower, long upper, boolean reluctant) {
    }

    default void visitNamedCapturingGroupReference(String name) {
    }

    // character class

    default void visitClassStart(boolean negated) {
    }

    default void visitClassEnd() {
    }

    default void visitRangeDash() {
    }

    // Escapes

    default void visitControlEscape(char c) {
    }

    default void visitHexEscapeSequence(char high, char low) {
    }

    default void visitControlLetter(char c) {
    }

    default void visitDecimalEscape(int value) {
    }

    default void visitCharacterClassEscape(char c) {
    }

    default void visitNullEscape() {
    }

    default void visitIdentityEscape(char c) {
    }
}
