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
 * An input source to parsers.
 *
 * @author leadpony
 */
interface InputSource {

    /**
     * Checks if this input source has next character or not.
     *
     * @return {@code true} if there exists next character.
     */
    boolean hasNext();

    /**
     * Returns the next character.
     *
     * @return the next character.
     * @throws NoSuchElementException if next character is not available.
     */
    int next();

    default int peek() {
        return peek(0);
    }

    int peek(int offset);

    void skip(int count);

    int length();

    /**
     * Returns the number of code units remaining.
     *
     * @return the number of code units.
     */
    int available();

    /**
     * Returns the current index.
     *
     * @return the current index in code units starting from zero.
     */
    int index();

    void mark();

    void reset();

    /**
     * Returns the original text given to this source.
     * @return the original text
     */
    @Override
    String toString();
}
