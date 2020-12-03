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

import java.util.BitSet;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author leadpony
 */
class BmpPatternParser extends BasePatternParser {

    @SuppressWarnings("serial")
    private static final BitSet SYNTAX_CHAR_SET = new BitSet() {
        {
            set('^');
            set('$');
            set('\\');
            set('.');
            set('*');
            set('+');
            set('?');
            set('(');
            set(')');
            set('[');
            set(']');
            set('{');
            set('}');
            set('|');
        }
    };

    private int maxCapturingGroupNumber;
    private int leftCapturingParentheses;

    private Set<String> groups;
    private Set<String> groupReferences;

    final PatternVisitor visitor;

    private static final int CHARACTER_CLASS = Integer.MAX_VALUE;

    BmpPatternParser(InputSource source, PatternVisitor visitor) {
        super(source);
        this.visitor = visitor;
    }

    void parse() {
        try {
            pattern();
        } catch (NoSuchElementException e) {
            throw internalError(e);
        }
    }

    @Production("Pattern")
    private void pattern() {
        disjunction();
        if (hasNext()) {
            throw unexpectedChar(peek());
        }
        checkCapturingNumber();
        checkGroupReferences();
    }

    @Production("Disjunction")
    private void disjunction() {
        alternative();
        while (hasNext('|')) {
            next();
            visitor.visitAlternation();
            alternative();
        }
    }

    /**
     * Alternative can be empty.
     */
    @Production("Alternative")
    private void alternative() {
        while (hasNext() && term()) {
        }
    }

    @Production("Term")
    @RequireNext
    private boolean term() {
        if (assertion()) {
            return true;
        } else if (atom()) {
            // quantifier is optional
            quantifier();
            return true;
        }
        return false;
    }

    @Production("Assertion")
    @RequireNext
    private boolean assertion() {
        final int c = peek();

        if (c == '^') {
            next();
            visitor.visitInputStart();
            return true;
        }

        if (c == '$') {
            next();
            visitor.visitInputEnd();
            return true;
        }

        if (c == '\\') {
            if (available() >= 2) {
                int next = peek(1);
                if (next == 'b') {
                    skip(2);
                    visitor.visitWordBoundary();
                    return true;
                } else if (next == 'B') {
                    skip(2);
                    visitor.visitNonwordBoundary();
                    return true;
                }
            }
            // may be an escape
            return false;
        }

        if (c == '(') {
            final int available = available();
            if (available >= 3) {
                int next = peek(1);
                if (next == '?') {
                    next = peek(2);
                    if (next == '=' || next == '!') {
                        skip(3);
                        visitor.visitLookaheadGroup(next == '=');
                    } else if (next == '<' && available >= 4) {
                        next = peek(3);
                        if (next == '=' || next == '!') {
                            skip(4);
                            visitor.visitLookbehindGroup(next == '=');
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                    disjunction();

                    if (hasNext(')')) {
                        next();
                        visitor.visitGroupEnd();
                        return true;
                    } else {
                        throw syntaxError(Message.thatGroupIsUnterminated());
                    }
                }
            }
            // may be a capturing group
        }

        return false;
    }

    @Production("Quantifier")
    private boolean quantifier() {
        if (!hasNext()) {
            return false;
        }

        switch (peek()) {
        case '*':
        case '+':
        case '?':
            visitor.visitQuantifier((char) next(), quantifierSuffix());
            return true;
        case '{':
            return boundedQuantifier();
        default:
            return false;
        }
    }

    @Production("Atom")
    @RequireNext
    private boolean atom() {

        if (patternCharacter() || characterClass()) {
            return true;
        }

        final int c = peek();
        if (c == '.') {
            next();
            visitor.visitAnyCharacter();
            return true;
        }

        if (c == '\\') {
            next();
            if (hasNext()) {
                return atomEscape();
            } else {
                throw syntaxError(Message.thatPatternEndsWith('\\'));
            }
        }

        if (c == '(') {
            next();
            if (available() >= 2 && peek() == '?' && peek(1) == ':') {
                skip(2);
                visitor.visitNoncapturingGroup();
            } else {
                beginCapturingGroup();
                String name = groupSpecifier();
                if (name != null) {
                    addGroup(name);
                    visitor.visitNamedCapturingGroup(name);
                } else {
                    visitor.visitCapturingGroup();
                }
            }

            disjunction();

            if (hasNext(')')) {
                next();
                visitor.visitGroupEnd();
                return true;
            } else {
                throw syntaxError(Message.thatGroupIsUnterminated());
            }
        }

        return false;
    }

    /**
     * One of ^ $ \ . * + ? ( ) [ ] { } |
     *
     * @return {@code true} if a syntax character was found.
     */
    @Production("SyntaxCharacter")
    @RequireNext
    protected boolean syntaxCharacter() {
        if (isSyntaxCharacter(peek())) {
            next();
            return true;
        }
        return false;
    }

    @Production("PatternCharacter")
    @RequireNext
    protected boolean patternCharacter() {
        if (!isSyntaxCharacter(peek())) {
            visitor.visitCharacter((char) next());
            return true;
        }
        return false;
    }

    /**
     * Early error:
     * <ul>
     * <li>It is a Syntax Error if the enclosing Pattern does not contain a
     * GroupSpecifier with an enclosed RegExpIdentifierName whose StringValue equals
     * the StringValue of the RegExpIdentifierName of this production's GroupName.
     * </li>
     * </ul>
     * @return always {@code true}
     * @throws SyntaxError if escape sequence is not supported.
     */
    @Production("AtomEscape")
    @RequireNext
    private boolean atomEscape() {
        if (decimalEscape() || characterClassEscape()) {
            return true;
        }

        if (characterEscape() >= 0) {
            return true;
        }

        if (peek() == 'k') {
            // skips k
            next();
            String name = groupName();
            if (name != null) {
                addGroupReference(name);
                visitor.visitNamedCapturingGroupReference(name);
                return true;
            } else {
                throw syntaxError(Message.thatNamedCapturingGroupReferenceIsInvalid());
            }
        }

        throw syntaxError(Message.thatEscapeSequenceIsUnsupported());
    }

    /**
     * An escaped character.
     *
     * @return {@code true} if exists.
     */
    @Production("CharacterEscape")
    @RequireNext
    private int characterEscape() {
        int result = controlEscape();
        if (result >= 0) {
            return result;
        }

        if (peek() == 'c') {
            next();
            // controlLetter() calls our handler
            return controlLetter();
        }

        if (peek() == '0') {
            // skips 0
            next();
            if (hasNext() && isDigit(peek())) {
                throw syntaxError(Message.thatNullEscapeWasFollowedByDigit((char) peek()));
            }
            visitor.visitNullEscape();
            return '\u0000';
        }

        result = hexEscapeSequence();
        if (result >= 0) {
            return result;
        }

        result = regExpUnicodeEscapeSequence();
        if (result >= 0) {
            return result;
        }

        result = identityEscape();
        if (result >= 0) {
            return result;
        }

        return -1;
    }

    @Production("ControlEscape")
    @RequireNext
    private int controlEscape() {
        int value;
        switch (peek()) {
        case 't':
            value = 9;
            break;
        case 'n':
            value = 10;
            break;
        case 'v':
            value = 11;
            break;
        case 'f':
            value = 12;
            break;
        case 'r':
            value = 13;
            break;
        default:
            return -1;
        }
        visitor.visitControlEscape((char) next());
        return value;
    }

    /**
     * Parses a control letter
     *
     * @return a controller character.
     * @throws SyntaxError if there is no controller letter.
     */
    @Production("ControlLetter")
    private int controlLetter() {
        if (hasNext()) {
            final int c = peek();
            if (isControlLetter(c)) {
                next();
                visitor.visitControlLetter((char) c);
                return c % 32;
            }
            throw syntaxError(Message.thatControlLetterIsInvalid((char) c));
        }
        throw syntaxError(Message.thatControlEscapeSequenceIsInvalid());
    }

    /**
     *
     * @return the parsed group name, or {@code null} if no specifier found.
     */
    @Production("GroupSpecifier")
    private String groupSpecifier() {
        if (hasNext('?')) {
            next();
            String name = groupName();
            if (name == null) {
                throw syntaxError(Message.thatGroupIsInvalid());
            }
            return name;
        } else {
            // empty
            return null;
        }
    }

    /**
     * Parses a group name.
     *
     * @return the group name found, or {@code null} if not found..
     * @throws SyntaxError
     */
    @Production("GroupName")
    private String groupName() {
        if (hasNext('<')) {
            // skips <
            next();
            String name = regExpIdentifierName();
            if (hasNext('>')) {
                next();
                if (name.isEmpty()) {
                    throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
                }
                return name;
            } else {
                throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
            }
        }
        // no group name was found
        return null;
    }

    /**
     * Parses identifier name.
     *
     * @return the parsed identifier name.
     */
    @Production("RegExpIdentifierName")
    private String regExpIdentifierName() {
        StringBuilder builder = new StringBuilder();

        if (hasNext()) {
            int c = regExpIdentifierStart();
            if (c >= 0) {
                builder.appendCodePoint(c);
                while (hasNext()) {
                    c = regExpIdentifierPart();
                    if (c >= 0) {
                        builder.appendCodePoint(c);
                    } else {
                        break;
                    }
                }
            }
        }

        return builder.toString();
    }

    /**
     * @return
     * @throws SyntaxError
     */
    @Production("RegExpIdentifierStart")
    @RequireNext
    private int regExpIdentifierStart() {
        int c = peek();
        if (isRegExpIdentifierStart((char) c)) {
            next();
            return c;
        } else if (c == '\\') {
            next();
            c = regExpUnicodeEscapeSequenceInGroupName();
            if (isRegExpIdentifierStart((char) c)) {
                return c;
            }
            throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
        } else if (Character.isHighSurrogate((char) c)) {
            final int codePoint = surrogatePair();
            if (isUnicodeIdStart(codePoint)) {
                return codePoint;
            }
            throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
        }
        return -1;
    }

    @Production("RegExpIdentifierPart")
    private int regExpIdentifierPart() {
        int c = peek();
        if (isRegExpIdentifierPart((char) c)) {
            next();
            return c;
        } else if (c == '\\') {
            next();
            c = regExpUnicodeEscapeSequenceInGroupName();
            if (isRegExpIdentifierPart((char) c)) {
                return c;
            }
            throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
        } else if (Character.isHighSurrogate((char) c)) {
            final int codePoint = surrogatePair();
            if (isUnicodeIdContinue(codePoint)) {
                return codePoint;
            }
            throw syntaxError(Message.thatCapuringGroupNameIsInvalid());
        }
        return -1;
    }

    @Production("RegExpUnicodeEscapeSequence")
    private int regExpUnicodeEscapeSequence() {
        if (!hasNext('u')) {
            return -1;
        }
        // skips 'u'
        next();
        final int c = hex4Digits();
        if (c >= 0) {
            visitor.visitUnicodeEscapeSequence((char) c);
        }
        return c;
    }

    @Production("RegExpUnicodeEscapeSequence")
    private int regExpUnicodeEscapeSequenceInGroupName() {
        if (!hasNext()) {
            throw syntaxError(Message.thatPatternEndsWith('\\'));
        }

        if (peek() == 'u') {
            // skips 'u'
            next();
            final int c = hex4Digits();
            if (c >= 0) {
                return c;
            }
        }
        throw syntaxError(Message.thatEscapeSequenceIsUnsupported());
    }

    @Production("IdentityEscape")
    @RequireNext
    private int identityEscape() {
        final int c = peek();
        if (isUnicodeIdContinue((char) c)) {
            return -1;
        }
        next();
        visitor.visitIdentityEscape((char) c);
        return c;
    }

    /**
     * Early error:
     * <ul>
     * <li>It is a Syntax Error if the CapturingGroupNumber of DecimalEscape is
     * larger than NcapturingParens.</li>
     * </ul>
     */
    @Production("DecimalEscape")
    @RequireNext
    private boolean decimalEscape() {
        if (isNonZeroDigit(peek())) {
            int value = convertDigitToNumber(next());
            while (hasNext() && isDigit(peek())) {
                value = value * 10 + convertDigitToNumber(next());
            }

            updateCapturingGroupNumber(value);
            visitor.visitDecimalEscape(value);

            return true;
        }
        return false;
    }

    @Production("CharacterClassEscape")
    @RequireNext
    private boolean characterClassEscape() {
        switch (peek()) {
        case 'd':
        case 'D':
        case 's':
        case 'S':
        case 'w':
        case 'W':
            visitor.visitCharacterClassEscape((char) next());
            return true;
        default:
            return false;
        }
    }

    @Production("CharacterClass")
    @RequireNext
    private boolean characterClass() {
        if (peek() != '[') {
            return false;
        }
        next();

        final boolean negated = peek() == '^';
        if (negated) {
            next();
        }
        visitor.visitClassStart(negated);

        final boolean empty = !classRanges();

        if (hasNext(']')) {
            next();
            visitor.visitClassEnd(empty);
            return true;
        } else {
            throw syntaxError(Message.thatCharacterClassIsUnterminated());
        }
    }

    /**
     * Multiple class ranges.
     */
    @Production("ClassRanges")
    private boolean classRanges() {
        boolean found = false;
        while (hasNext() && nonemptyClassRanges()) {
            found = true;
        }
        return found;
    }

    /**
     * Early error:
     * <ul>
     * <li>It is a Syntax Error if IsCharacterClass of the first ClassAtom is true
     * or IsCharacterClass of the second ClassAtom is true.</li>
     * <li>It is a Syntax Error if IsCharacterClass of the first ClassAtom is false
     * and IsCharacterClass of the second ClassAtom is false and the CharacterValue
     * of the first ClassAtom is larger than the CharacterValue of the second
     * ClassAtom.</li>
     * </ul>
     *
     * @return
     */
    @Production("NonemptyClassRanges")
    @RequireNext
    private boolean nonemptyClassRanges() {
        final int lower = classAtom();
        if (lower >= 0) {
            if (hasNext('-')) {
                next();
                visitor.visitRangeDash();
                final int upper = classAtom();
                if (upper >= 0) {
                    // This may throw early error.
                    checkClassRange(lower, upper);
                    return true;
                }
            } else if (nonemptyClassRangesNoDash()) {
                return true;
            }
            return true;
        }
        return false;
    }

    @Production("NonemptyClassRangesNoDash")
    private boolean nonemptyClassRangesNoDash() {
        final int lower = classAtomNoDash();
        if (lower >= 0) {
            if (hasNext('-')) {
                next();
                visitor.visitRangeDash();
                final int upper = classAtom();
                if (upper >= 0) {
                    // This may throw early error.
                    checkClassRange(lower, upper);
                    return true;
                }
            } else if (nonemptyClassRangesNoDash()) {
                return true;
            }
        }
        return classAtom() >= 0;
    }

    @Production("ClassAtom")
    private int classAtom() {
        if (hasNext('-')) {
            // plain dash
            visitor.visitCharacter(next());
            return '-';
        } else {
            return classAtomNoDash();
        }
    }

    @Production("ClassAtomNoDash")
    private int classAtomNoDash() {
        if (!hasNext()) {
            return -1;
        }
        int c = peek();
        if (c == '\\') {
            next();
            if (hasNext()) {
                return classEscape();
            } else {
                throw syntaxError(Message.thatPatternEndsWith('\\'));
            }
        } else if (c == ']' || c == '-') {
            return -1;
        } else {
            // SourceCharacter but not one of \ or ] or -
            c = next();
            visitor.visitCharacter((char) c);
            return c;
        }
    }

    @Production("ClassEscape")
    @RequireNext
    private int classEscape() {
        if (peek() == 'b') {
            next();
            visitor.visitCharacter('\u0008');
            return '\u0008';
        }

        if (characterClassEscape()) {
            return CHARACTER_CLASS;
        }

        final int character = characterEscape();
        if (character >= 0) {
            return character;
        }

        throw syntaxError(Message.thatEscapeSequenceInCharacterClassIsInvalid());
    }

    /**
     * Parses decimal digits.
     *
     * @return the numeric value parsed, or {@code -1} if not found.
     */
    @Production("DecimalDigits")
    private long decimalDigits() {
        if (!hasNext() || !isDigit(peek())) {
            return -1;
        }

        long value = convertDigitToNumber(next());
        while (hasNext() && isDigit(peek())) {
            value = (value * 10) + convertDigitToNumber(next());
        }
        return value;
    }

    /**
     * Parses four hexadecimal digits.
     *
     * @return the numeric value parsed.
     * @throws SyntaxError if not found.
     */
    @Production("Hex4Digits")
    private int hex4Digits() {
        int value = 0;
        int digits = 0;
        while (hasNext()) {
            final int c = next();
            if (!isHexDigit(c)) {
                break;
            }
            value = value * 16 + convertHexDigitToNumber(c);
            if (++digits >= 4) {
                return value;
            }
        }
        throw syntaxError(Message.thatUnicodeEscapeSequenceIsInvalid());
    }

    @Production("HexEscapeSequence")
    private int hexEscapeSequence() {
        if (!hasNext('x')) {
            return -1;
        }
        next();
        if (hasNext()) {
            final int high = next();
            if (isHexDigit(high)) {
                if (hasNext()) {
                    final int low = next();
                    if (isHexDigit(low)) {
                        int value = convertHexDigitToNumber(high) * 16
                                + convertHexDigitToNumber(low);
                        visitor.visitHexEscapeSequence((char) high, (char) low);
                        return value;
                    }
                }
            }
        }
        throw syntaxError(Message.thatHexEscapeSequenceIsInvalid());
    }

    // Non-production methods

    private boolean boundedQuantifier() {
        // skip {
        next();

        final long lower = quantifierBound();
        if (hasNext()) {
            int c = next();
            if (c == '}') {
                visitor.visitBoundedQuantifier(lower, quantifierSuffix());
                return true;
            } else if (c == ',') {
                if (hasNext('}')) {
                    next();
                    visitor.visitBoundedQuantifier(lower, -1, quantifierSuffix());
                    return true;
                }
                final long upper = quantifierBound();
                if (hasNext()) {
                    if (peek() == '}') {
                        next();
                        checkQuantifierBounds(lower, upper);
                        visitor.visitBoundedQuantifier(lower, upper, quantifierSuffix());
                        return true;
                    }
                    throw syntaxError(Message.thatQuantifierIsInvalid());
                }
            } else {
                throw syntaxError(Message.thatQuantifierIsInvalid());
            }
        }

        throw syntaxError(Message.thatQuantifierIsUnterminated());
    }

    /**
     * Returns the lower or upper bound of bounded quantifier.
     *
     * @return the bound of quantifier.
     */
    private long quantifierBound() {
        if (hasNext()) {
            long value = decimalDigits();
            if (value >= 0) {
                return value;
            } else {
                throw syntaxError(Message.thatQuantifierIsInvalid());
            }
        } else {
            throw syntaxError(Message.thatQuantifierIsUnterminated());
        }
    }

    private boolean quantifierSuffix() {
        if (hasNext('?')) {
            next();
            return true;
        }
        return false;
    }

    private int surrogatePair() {
        final char high = (char) next();
        if (hasNext()) {
            final char low = (char) next();
            if (Character.isSurrogatePair(high, low)) {
                return Character.toCodePoint(high, low);
            }
        }
        throw syntaxError(Message.thatSurrogatePairIsInvalid());
    }

    private void beginCapturingGroup() {
        leftCapturingParentheses++;
    }

    private void updateCapturingGroupNumber(int value) {
        if (value > this.maxCapturingGroupNumber) {
            this.maxCapturingGroupNumber = value;
        }
    }

    private void addGroup(String name) {
        if (this.groups == null) {
            this.groups = new HashSet<>();
        }
        if (this.groups.contains(name)) {
            throw syntaxError(Message.thatCapuringGroupNameIsDuplicated(name));
        } else {
            this.groups.add(name);
        }
    }

    private void addGroupReference(String name) {
        if (this.groupReferences == null) {
            this.groupReferences = new HashSet<>();
        }
        this.groupReferences.add(name);
    }

    private boolean findGroup(String name) {
        return this.groups != null && this.groups.contains(name);
    }

    /**
     * It is a Syntax Error if the MV of the first DecimalDigits is larger than the
     * MV of the second DecimalDigits.
     *
     * @param lower the lower bound of the quantifier.
     * @param upper the upper bound of the quantifier.
     * @return {@code true} if the test passed.
     */
    private void checkQuantifierBounds(long lower, long upper) {
        if (lower > upper) {
            throw earlyError(Message.thatQuantifierBoundsAreOutOfOrder());
        }
    }

    private void checkClassRange(int lower, int upper) {
        if (lower == CHARACTER_CLASS || upper == CHARACTER_CLASS) {
            throw earlyError(Message.thatCharacterClassRangeIsInvalid());
        } else if (lower > upper) {
            throw earlyError(Message.thatCharacterClassRangeIsOutOfOrder());
        }
    }

    private void checkCapturingNumber() {
        if (this.maxCapturingGroupNumber > this.leftCapturingParentheses) {
            throw earlyError(
                    Message.thatCapturingGroupNumberIsOutOfBounds(maxCapturingGroupNumber, leftCapturingParentheses));
        }
    }

    private void checkGroupReferences() {
        if (this.groupReferences == null) {
            return;
        }
        for (String ref : this.groupReferences) {
            if (!findGroup(ref)) {
                throw earlyError(Message.thatNamedCapturingGroupIsNotFound());
            }
        }
    }

    protected static boolean isControlLetter(int c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
    }

    protected static boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    protected static boolean isNonZeroDigit(int c) {
        return '1' <= c && c <= '9';
    }

    protected static boolean isHexDigit(int c) {
        return isDigit(c)
                || ('A' <= c && c <= 'Z')
                || ('a' <= c && c <= 'z');
    }

    protected static boolean isRegExpIdentifierStart(char c) {
        return isUnicodeIdStart(c) || c == '$' || c == '_';
    }

    protected static boolean isRegExpIdentifierPart(char c) {
        return isUnicodeIdContinue(c) || c == '$'
                || c == '\u200c'
                || c == '\u200d';
    }

    protected static boolean isSyntaxCharacter(int c) {
        return SYNTAX_CHAR_SET.get(c);
    }

    protected static boolean isUnicodeIdStart(char c) {
        return Character.isUnicodeIdentifierStart(c);
    }

    protected static boolean isUnicodeIdStart(int codePoint) {
        return Character.isUnicodeIdentifierStart(codePoint);
    }

    protected static boolean isUnicodeIdContinue(char c) {
        return Character.isUnicodeIdentifierPart(c);
    }

    protected static boolean isUnicodeIdContinue(int codePoint) {
        return Character.isUnicodeIdentifierPart(codePoint);
    }

    private static int convertDigitToNumber(int c) {
        return c - '0';
    }

    private static int convertHexDigitToNumber(int c) {
        if (c >= 'a') {
            return c - 'a' + 10;
        } else if (c >= 'A') {
            return c - 'A' + 10;
        } else {
            return c - '0';
        }
    }

    protected final SyntaxError unexpectedChar(int cp) {
        return syntaxError(Message.thatUnexpectedCharIsFound());
    }
}
