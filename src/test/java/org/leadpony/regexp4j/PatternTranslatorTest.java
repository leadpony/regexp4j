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

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * @author leadpony
 */
public class PatternTranslatorTest {

    @ParameterizedTest
    @EnumSource(ValidPattern.class)
    public void shouldTranslatePatternAsExpected(ValidPattern pattern) {
        Set<RegExpFlag> flags = Collections.emptySet();
        PatternTranslator translator = RegExp.translatePattern(pattern.get(), flags);
        Pattern translated = translator.getPattern();
        if (pattern.isSame()) {
            assertThat(translated.toString()).isEqualTo(pattern.get());
        }
    }
}
