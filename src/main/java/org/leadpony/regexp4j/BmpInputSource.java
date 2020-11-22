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

import java.util.NoSuchElementException;

/**
 * @author leadpony
 */
final class BmpInputSource implements InputSource {

    private final CharSequence input;
    private final int length;
    private int pos;
    private int lastMarkedPos = -1;

    BmpInputSource(CharSequence input) {
        this.input = input;
        this.length = input.length();
    }

    @Override
    public boolean hasNext() {
        return pos < length;
    }

    @Override
    public int next() {
        if (hasNext()) {
            return input.charAt(pos++);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int peek() {
        if (hasNext()) {
            return input.charAt(pos);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int peek(int offset) {
        if (pos + offset < length) {
            return input.charAt(pos + offset);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void skip(int count) {
        if (pos + count < length) {
            pos += count;
        } else {
            pos = length;
        }
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int available() {
        return length - pos;
    }

    @Override
    public void mark() {
        lastMarkedPos = pos;
    }

    @Override
    public void reset() {
        if (lastMarkedPos >= 0) {
            pos = lastMarkedPos;
        }
    }
}
