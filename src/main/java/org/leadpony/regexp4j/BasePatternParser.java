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
abstract class BasePatternParser {

    private final InputSource source;

    protected BasePatternParser(InputSource source) {
        this.source = source;
    }

    protected final boolean hasNext() {
        return source.hasNext();
    }

    protected final boolean hasNext(int c) {
        return hasNext() && peek() == c;
    }

    protected final int next() {
        return source.next();
    }

    protected final int peek() {
        return source.peek();
    }

    protected final int peek(int offset) {
        return source.peek(offset);
    }

    protected final void skip(int count) {
        source.skip(count);
    }

    protected final int available() {
        return source.available();
    }

    protected final void mark() {
        source.mark();
    }

    protected final void reset() {
        source.reset();
    }

    protected final SyntaxError syntaxError() {
        return new SyntaxError();
    }

    protected final SyntaxError syntaxError(String message) {
        return new SyntaxError(message);
    }

    protected final SyntaxError earlyError(String message) {
        return syntaxError(message);
    }
}
