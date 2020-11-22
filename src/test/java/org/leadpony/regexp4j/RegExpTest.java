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

import static org.assertj.core.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author leadpony
 */
public class RegExpTest {

    private static final Logger LOG = Logger.getLogger(RegExpTest.class.getName());

    @Test
    public void test() {
        RegExp re = new RegExp("a*b");
        assertThat(re.test("aaaaab")).isTrue();
    }

    @ParameterizedTest
    @EnumSource(ValidPattern.class)
    public void constructorShouldConstructInstance(ValidPattern pattern) {
        RegExp re = new RegExp(pattern.get());
        assertThat(re).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(InvalidPattern.class)
    public void constructorShouldThrowSyntaxErrorIfPatternIsInvalid(InvalidPattern pattern) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp(pattern.get());
        });

        assertThat(thrown).isInstanceOf(SyntaxError.class);
        LOG.info(thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "g",
            "i",
            "m",
            "s",
            "u",
            "y",
            "gimsuy"
    })
    public void constructorShouldSucceedIfFlagsAreValid(String flags) {
        RegExp re = new RegExp("abc", flags);
        assertThat(re).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "gg",
            "ii",
            "mm",
            "ss",
            "uu",
            "yy",
            "gimsuyg",
            "hello"
    })
    public void constructorShouldThrowSyntaxErrorIfFlagsAreInvalid(String flags) {
        Throwable thrown = catchThrowable(() -> {
            new RegExp("abc", flags);
        });
        assertThat(thrown).isInstanceOf(SyntaxError.class);
        assertThat(thrown.getMessage()).isNotNull();
        LOG.info(thrown.getMessage());
    }

    public enum ExecTestCase {
        ALTERNATIVES("a|ab", "abc", "a"),
        GROUPS("((a)|(ab))((c)|(bc))", "abc",
                "abc", "a", "a", null, "bc", null, "bc");

        final String pattern;
        final String input;
        final String[] expected;

        ExecTestCase(String pattern, String input, String... expected) {
            this.pattern = pattern;
            this.input = input;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(ExecTestCase.class)
    public void execShouldReturnMatchedResults(ExecTestCase test) {
        RegExp re = new RegExp(test.pattern);
        String[] results = re.exec(test.input);
        assertThat(results).isEqualTo(test.expected);
    }

    @Test
    public void execShouldDoGlobalSearch() {
        RegExp re = new RegExp("foo*", "g");
        String input = "table football, foosball";

        String[] results = re.exec(input);
        assertThat(results).isNotNull();
        assertThat(results.length).isEqualTo(1);
        assertThat(results[0]).isEqualTo("foo");
        assertThat(re.getLastIndex()).isEqualTo(9);

        results = re.exec(input);
        assertThat(results).isNotNull();
        assertThat(results.length).isEqualTo(1);
        assertThat(results[0]).isEqualTo("foo");
        assertThat(re.getLastIndex()).isEqualTo(19);

        results = re.exec(input);
        assertThat(results).isNull();
    }

    public enum StringTestCase {
        NO_PATTERN("/(?:)/"),
        NO_FLAGS("a+b+c", "/a+b+c/"),
        FLAG("bar", "g", "/bar/g"),
        ESCAPE("\n", "g", "/\n/g"),
        CONTROL_ESCAPE("\\n", "g", "/\\n/g");

        final String pattern;
        final String flags;
        final String expected;

        StringTestCase(String expected) {
            this(null, null, expected);
        }

        StringTestCase(String pattern, String expected) {
            this(pattern, null, expected);
        }

        StringTestCase(String pattern, String flags, String expected) {
            this.pattern = pattern;
            this.flags = flags;
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @EnumSource(StringTestCase.class)
    public void toStringShouldReturnStringRepresentation(StringTestCase test) {
        RegExp re;
        if (test.pattern == null) {
            re = new RegExp();
        } else if (test.flags == null) {
            re = new RegExp(test.pattern);
        } else {
            re = new RegExp(test.pattern, test.flags);
        }
        assertThat(re.toString()).isEqualTo(test.expected);
    }
}
