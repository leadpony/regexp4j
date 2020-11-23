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
 * A syntax error found in regular expressions.
 *
 * @author leadpony
 */
@SuppressWarnings("serial")
public class SyntaxError extends RuntimeException {

    private final String description;
    private final String pattern;
    private final int index;

    /**
     * Constructs a new {@code SyntaxError} with the specified description.
     *
     * @param description the description of the error
     * @param pattern     the erroneous pattern
     * @param index       the approximate index in the pattern of the error, or -1
     *                    if the index is not known
     */
    public SyntaxError(String description, String pattern, int index) {
        this.description = description;
        this.pattern = pattern;
        this.index = index;
    }

    SyntaxError(String description, String pattern, int index, Throwable cause) {
        super(null, cause);
        this.description = description;
        this.pattern = pattern;
        this.index = index;
    }

    /**
     * Returns the description of the error.
     *
     * @return the description of the error
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the erroneous regular expression pattern or flags.
     *
     * @return the erroneous pattern or flags.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Retutns the error index.
     *
     * @return the approximate index in the pattern of the error, or -1 if the index
     *         is not known
     */
    public int getIndex() {
        return index;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getMessage() {
        final String description = getDescription();
        if (description == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(description);
        builder.append('\n').append(getPattern()).append('\n');
        final int index = getIndex();
        if (index >= 0) {
            for (int i = 0; i < index; i++) {
                builder.append(' ');
            }
            builder.append('^');
        }
        return builder.toString();
    }
}
