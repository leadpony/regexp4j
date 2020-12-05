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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * A result returned by {@link RegExp#exec(String)}.
 *
 * @author leadpony
 */
public final class ExecResult {

    private final String input;
    private final Matcher matcher;
    private final Map<String, Integer> groupNames;

    ExecResult(String input, Matcher matcher, Map<String, Integer> groupNames) {
        this.input = input;
        this.matcher = matcher;
        this.groupNames = groupNames;
    }

    /**
     * Returns this result as an array.
     *
     * @return an array containing matched subsequence.
     */
    public String[] asArray() {
        return getResultAsArray(this.matcher);
    }

    /**
     * Returns the number of the entries in the result.
     *
     * @return the number of the entries in the result.
     */
    public int length() {
        return this.matcher.groupCount() + 1;
    }

    /**
     * Returns the last index of this match.
     *
     * @return the last index of this match.
     */
    public int index() {
        return this.matcher.end();
    }

    /**
     * Returns the input string.
     *
     * @return the input string.
     */
    public String input() {
        return this.input;
    }

    /**
     * Returns the result as a map containing group name/value pairs.
     *
     * @return the map containing group name and value pairs.
     */
    public Map<String, String> groups() {
        Map<String, String> groups = new HashMap<>();
        for (Map.Entry<String, Integer> entry : this.groupNames.entrySet()) {
            groups.put(entry.getKey(), matcher.group(entry.getValue()));
        }
        return groups;
    }

    static String[] getResultAsArray(Matcher matcher) {
        String[] array = new String[1 + matcher.groupCount()];
        for (int i = 0; i < array.length; i++) {
            array[i] = matcher.group(i);
        }
        return array;
    }
}
