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

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@code RegExp} object contains a regular expression and the associated
 * flags.
 *
 * @see <a href=
 *      "https://www.ecma-international.org/ecma-262/11.0/index.html#sec-regexp-regular-expression-objects">ECMAScript&copy;
 *      2020 Language Specification</a>
 * @author leadpony
 */
public final class RegExp {

    private final String source;
    private final Set<RegExpFlag> flags;

    private final Pattern pattern;
    private final Map<String, Integer> groupNames;

    private int lastIndex;

    /**
     * Constructs a new {@code RegExp} without a pattern.
     */
    public RegExp() {
        this(null, null);
    }

    /**
     * Constructs a new {@code RegExp} with the specified pattern.
     *
     * @param pattern the regular expression pattern.
     * @throws SyntaxError if a syntax error was found in {@code pattern}.
     */
    public RegExp(String pattern) {
        this(pattern, null);
    }

    /**
     * Constructs a new {@code RegExp} with the specified pattern and flags.
     *
     * @param pattern the regular expression pattern.
     * @param flags   a string consisting of the flags for this object.
     * @throws SyntaxError if a syntax error was found in {@code pattern} or
     *                     {@code flags}.
     */
    public RegExp(String pattern, String flags) {
        Set<RegExpFlag> flagSet = RegExpFlag.parse(flags);
        this.source = pattern;
        this.flags = flagSet;

        PatternTranslator translator = translatePattern(getSource(), flagSet);
        this.pattern = translator.getPattern();
        this.groupNames = translator.getGroupNames();
    }

    /**
     * Returns the text of the pattern.
     *
     * @return the text of the pattern, or {@code (?:)} if the pattern is not
     *         specified.
     */
    public String getSource() {
        if (source == null) {
            return "(?:)";
        }
        return this.source;
    }

    /**
     * Returns a string that contains the flags of this object.
     *
     * @return the string that contains the flags of this object.
     */
    public String getFlags() {
        StringBuilder builder = new StringBuilder();
        for (RegExpFlag flag : this.flags) {
            builder.append(flag.letter);
        }
        return builder.toString();
    }

    /**
     * Returns whether to test the regular expression against all possible matches
     * in a string, or only against the first.
     *
     * @return {@code true} if the global search is enabled, {@code false}
     *         otherwise.
     */
    public boolean isGlobal() {
        return flags.contains(RegExpFlag.GLOBAL);
    }

    /**
     * Returns whether to ignore case while attempting a match in a string.
     *
     * @return {@code true} if the cases are ignored, {@code false} otherwise.
     */
    public boolean isIgnoreCase() {
        return flags.contains(RegExpFlag.IGNORE_CASE);
    }

    /**
     * Returns whether or not to search in strings across multiple lines.
     *
     * @return {@code true} if the multiline search is enabled, {@code false}
     *         otherwise.
     */
    public boolean isMultiline() {
        return flags.contains(RegExpFlag.MULTILINE);
    }

    /**
     * Returns whether {@code .} matches newlines or not.
     *
     * @return {@code true} if dot matches newlines, {@code false} otherwise.
     */
    public boolean isDotAll() {
        return flags.contains(RegExpFlag.DOT_ALL);
    }

    /**
     * Returns whether or not Unicode features are enabled.
     *
     * @return {@code true} if the Unicode features are enabled, {@code false}
     *         otherwise.
     */
    public boolean isUnicode() {
        return flags.contains(RegExpFlag.UNICODE);
    }

    /**
     * Returns whether or not the search is sticky.
     *
     * @return {@code true} if the sticky search is enabled, {@code false}
     *         otherwise.
     */
    public boolean isSticky() {
        return flags.contains(RegExpFlag.STICKY);
    }

    /**
     * Returns the index at which to start the next match. The index starts from
     * zero.
     *
     * @return the index at which to start the next match.
     */
    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * Assigns the index at which to start the next match. The index starts from
     * zero.
     *
     * @param lastIndex the index at which to start the next match.
     */
    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    /**
     * Executes a search for a match between a regular expression and a specified
     * string.
     *
     * @param string the input text to test.
     * @return {@code true} if a match was found, {@code false} otherwise.
     */
    public boolean test(String string) {
        Matcher matcher = matcher(string);
        if (isSticky()) {
            if (matcher.find(lastIndex)) {
                lastIndex = matcher.end();
                return true;
            }
            return false;
        } else {
            return matcher.find();
        }
    }

    /**
     * Performs a regular expression match of {@code string} against the regular
     * expression and returns an array object containing the result of the match, or
     * {@code null} if string did not match.
     *
     * @param string the input text to check.
     * @return the result of the match as an array, or {@code null} if the
     *         {@code string} did not match.
     */
    public String[] exec(String string) {
        ExecResult result = execForResult(string);
        return (result != null) ? result.asArray() : null;
    }

    /**
     * Performs a regular expression match of {@code string} against the regular
     * expression and returns an {@link ExecResult} object containing the result of
     * the match, or {@code null} if string did not match.
     *
     * @param string the input text to check.
     * @return the result of the match as an {@link ExecResult} object, or
     *         {@code null} if the {@code string} did not match.
     */
    public ExecResult execForResult(String string) {
        Matcher matcher = matcher(string);
        if (isGlobal() || isSticky()) {
            if (matcher.find(lastIndex)) {
                this.lastIndex = matcher.end();
                return createExecResult(string, matcher);
            }
        } else {
            if (matcher.find()) {
                return createExecResult(string, matcher);
            }
        }
        return null;
    }

    /**
     * Replaces the subsequence of the input sequence that matches the pattern with
     * the given replacement string.
     *
     * @param string       the string to be replaced.
     * @param replaceValue the replacement string.
     * @return The string constructed by replacing the matching subsequence by the
     *         replacement string, substituting captured subsequences as needed
     */
    public String replace(String string, String replaceValue) {
        Matcher matcher = matcher(string);
        if (isGlobal()) {
            return matcher.replaceAll(replaceValue);
        } else {
            return matcher.replaceFirst(replaceValue);
        }
    }

    /**
     * Returns a string representing the regular expression.
     *
     * @return the string representing the regular expression.
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append('/').append(getSource()).append('/').append(getFlags())
                .toString();
    }

    private Matcher matcher(String string) {
        return pattern.matcher(string);
    }

    private ExecResult createExecResult(String string, Matcher matcher) {
        return new ExecResult(string, matcher, this.groupNames);
    }

    /**
     * Translates a pattern in ECMAScript into a pattern in Java.
     *
     * @param pattern the pattern in ECMAScript.
     * @param flags   the flags specified to the constructor.
     * @return the translator.
     * @throws SyntaxError if syntax error was found in the pattern.
     */
    static PatternTranslator translatePattern(String pattern, Set<RegExpFlag> flags) {
        PatternTranslator translator = createTranslator(pattern, flags);
        parsePattern(pattern, flags, translator);
        return translator;
    }

    static void parsePattern(String pattern, Set<RegExpFlag> flags, PatternVisitor visitor) {
        InputSource source = new BmpInputSource(pattern);
        BmpPatternParser parser = new BmpPatternParser(source, visitor);
        parser.parse();
    }

    private static PatternTranslator createTranslator(String pattern, Set<RegExpFlag> flags) {
        if (flags.contains(RegExpFlag.MULTILINE)) {
            return new MultilinePatternTranslator(pattern, flags);
        } else {
            return new PatternTranslator(pattern, flags);
        }
    }
}
