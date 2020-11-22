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
import java.util.Objects;

/**
 * A utility class for simply validating regular expression pattern in
 * ECMAScript. This class is not defined in the ECMAScript language.
 *
 * @author leadpony
 */
public final class RegExpValidator {

    /**
     * Checks whether a given regular expression pattern is valid or not.
     *
     * @param pattern the regular expression pattern in ECMAScript.
     * @return {@code true} if {@code pattern} is valid, {@code false} if not valid.
     * @throws NullPointerException if {@code pattern} is {@code null}.
     */
    public static boolean validate(String pattern) {
        try {
            validateOrThrow(pattern);
            return true;
        } catch (SyntaxError e) {
            return false;
        }
    }

    /**
     * Validates a given regular expression pattern and throws a syntax error if the
     * pattern is not valid.
     *
     * @param pattern the regular expression pattern in ECMAScript.
     * @throws SyntaxError          if {@code pattern} is not valid.
     * @throws NullPointerException if {@code pattern} is {@code null}.
     */
    public static void validateOrThrow(String pattern) {
        Objects.requireNonNull(pattern, "pattern must not be null");
        RegExp.parsePattern(pattern, Collections.emptySet(), PatternVisitor.EMPTY_VISITOR);
    }

    private RegExpValidator() {
    }
}
