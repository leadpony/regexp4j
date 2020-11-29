// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_2_10 {

    /*---
    info: |
        The production CharacterEscape :: t evaluates by returning
        the character \u0009
    es5id: 15.10.2.10_A1.1_T1
    description: Use \t in RegExp and \u0009 in tested string
    ---*/
    @Test
    @DisplayName("15.10.2.10_A1.1_T1")
    public void A1_1_T1() {
        assertThat(new RegExp("\\t").exec("\u0009"))
            .isEqualTo(new String[] {"\u0009"});

        assertThat(new RegExp("\\t\\t").exec("a\u0009\u0009b"))
            .isEqualTo(new String[] {"\u0009\u0009"});
    }

    @Test
    @DisplayName("15.10.2.10_A1.2_T1")
    public void A1_2_T1() {
        assertThat(new RegExp("\\n").exec("\012"))
            .isEqualTo(new String[] {"\012"});

        assertThat(new RegExp("\\n\\n").exec("a\012\012"))
            .isEqualTo(new String[] {"\012\012"});
    }

    @Test
    @DisplayName("15.10.2.10_A1.3_T1")
    public void A1_3_T1() {
        assertThat(new RegExp("\\v").exec("\u000B"))
            .isEqualTo(new String[] {"\u000B"});

        assertThat(new RegExp("\\v\\v").exec("a\u000B\u000Bb"))
            .isEqualTo(new String[] {"\u000B\u000B"});
    }

    @Test
    @DisplayName("15.10.2.10_A1.4_T1")
    public void A1_4_T1() {
        assertThat(new RegExp("\\f").exec("\u000C"))
            .isEqualTo(new String[] {"\u000C"});

        assertThat(new RegExp("\\f\\f").exec("a\u000C\u000Cb"))
            .isEqualTo(new String[] {"\u000C\u000C"});
    }

    @Test
    @DisplayName("15.10.2.10_A1.5_T1")
    public void A1_5_T1() {
        assertThat(new RegExp("\\r").exec("\015"))
            .isEqualTo(new String[] {"\015"});

        assertThat(new RegExp("\\r\\r").exec("a\015\015b"))
            .isEqualTo(new String[] {"\015\015"});
    }

    public static IntStream A2_1_T1() {
        return IntStream.rangeClosed(0x0041, 0x005A);
    }

    /*---
    info: "CharacterEscape :: c ControlLetter"
    es5id: 15.10.2.10_A2.1_T1
    description: "ControlLetter :: A - Z"
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A2.1_T1")
    public void A2_1_T1(int alpha) {
        testControlLetter(alpha);
    }

    public static IntStream A2_1_T2() {
        return IntStream.rangeClosed(0x0061, 0x007A);
    }

    /*---
    info: "CharacterEscape :: c ControlLetter"
    es5id: 15.10.2.10_A2.1_T2
    description: "ControlLetter :: a - z"
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A2.1_T2")
    public void A2_1_T2(int alpha) {
        testControlLetter(alpha);
    }

    private static void testControlLetter(int alpha) {
        String str = String.valueOf((char)(alpha % 32));
        RegExp re = new RegExp("\\c" + String.valueOf((char) alpha));
        String[] actual = re.exec(str);
        String[] expected = { str };
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> A3_1_T1() {
        return Stream.of(
                Arguments.of("\\x00", "\u0000"),
                Arguments.of("\\x01", "\u0001"),
                Arguments.of("\\x0A", "\012"),
                Arguments.of("\\xFF", "\u00FF")
                );
    }

    /*---
    info: "CharacterEscape :: HexEscapeSequence :: x HexDigit HexDigit"
    es5id: 15.10.2.10_A3.1_T1
    description: Tested string include equal unicode symbols
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A3.1_T1")
    public void A3_1_T1(String pattern, String string) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(string);
        String[] expected = { string };
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> A3_1_T2() {
        List<Arguments> args = new ArrayList<>();
        {
            String[] hex = {"\\x41", "\\x42", "\\x43", "\\x44", "\\x45", "\\x46", "\\x47", "\\x48", "\\x49", "\\x4A", "\\x4B", "\\x4C", "\\x4D", "\\x4E", "\\x4F", "\\x50", "\\x51", "\\x52", "\\x53", "\\x54", "\\x55", "\\x56", "\\x57", "\\x58", "\\x59", "\\x5A"};
            String[] character = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        {
            String[] hex = {"\\x61", "\\x62", "\\x63", "\\x64", "\\x65", "\\x66", "\\x67", "\\x68", "\\x69", "\\x6A", "\\x6B", "\\x6C", "\\x6D", "\\x6E", "\\x6F", "\\x70", "\\x71", "\\x72", "\\x73", "\\x74", "\\x75", "\\x76", "\\x77", "\\x78", "\\x79", "\\x7A"};
            String[] character = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        return args.stream();
    }

    /*---
    info: "CharacterEscape :: HexEscapeSequence :: x HexDigit HexDigit"
    es5id: 15.10.2.10_A3.1_T2
    description: Checking ENGLISH CAPITAL ALPHABET and english small alphabet
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A3.1_T2")
    public void A3_1_T2(String pattern, String string) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(string);
        String[] expected = { string };
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> A4_1_T1() {
        return Stream.of(
                Arguments.of("\\u0000", "\u0000"),
                Arguments.of("\\u0001", "\u0001"),
                Arguments.of("\\u000A", "\012"),
                Arguments.of("\\u00FF", "\u00FF"),
                Arguments.of("\\u0FFF", "\u0FFF"),
                Arguments.of("\\uFFFF", "\uFFFF")
                );
    }

    /*---
    info: |
        CharacterEscape :: UnicodeEscapeSequence :: u HexDigit HexDigit HexDigit
        HexDigit
    es5id: 15.10.2.10_A4.1_T1
    description: RegExp and tested string include uncode symbols
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A4.1_T1")
    public void A4_1_T1(String pattern, String string) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(string);
        String[] expected = { string };
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> A4_1_T2() {
        List<Arguments> args = new ArrayList<>();
        {
            String[] hex = {"\\u0041", "\\u0042", "\\u0043", "\\u0044", "\\u0045", "\\u0046", "\\u0047", "\\u0048", "\\u0049", "\\u004A", "\\u004B", "\\u004C", "\\u004D", "\\u004E", "\\u004F", "\\u0050", "\\u0051", "\\u0052", "\\u0053", "\\u0054", "\\u0055", "\\u0056", "\\u0057", "\\u0058", "\\u0059", "\\u005A"};
            String[] character = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        {
            String[] hex = {"\\u0061", "\\u0062", "\\u0063", "\\u0064", "\\u0065", "\\u0066", "\\u0067", "\\u0068", "\\u0069", "\\u006A", "\\u006B", "\\u006C", "\\u006D", "\\u006E", "\\u006F", "\\u0070", "\\u0071", "\\u0072", "\\u0073", "\\u0074", "\\u0075", "\\u0076", "\\u0077", "\\u0078", "\\u0079", "\\u007A"};
            String[] character = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        return args.stream();
    }

    /*---
    info: |
        CharacterEscape :: UnicodeEscapeSequence :: u HexDigit HexDigit HexDigit
        HexDigit
    es5id: 15.10.2.10_A4.1_T2
    description: >
        Tested string include ENGLISH CAPITAL ALPHABET and english small
        alphabet
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A4.1_T2")
    public void A4_1_T2(String pattern, String string) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(string);
        String[] expected = { string };
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> A4_1_T3() {
        List<Arguments> args = new ArrayList<>();
        {
            String[] hex = {"\\u0410", "\\u0411", "\\u0412", "\\u0413", "\\u0414", "\\u0415", "\\u0416", "\\u0417", "\\u0418", "\\u0419", "\\u041A", "\\u041B", "\\u041C", "\\u041D", "\\u041E", "\\u041F", "\\u0420", "\\u0421", "\\u0422", "\\u0423", "\\u0424", "\\u0425", "\\u0426", "\\u0427", "\\u0428", "\\u0429", "\\u042A", "\\u042B", "\\u042C", "\\u042D", "\\u042E", "\\u042F", "\\u0401"};
            String[] character = {"\u0410", "\u0411", "\u0412", "\u0413", "\u0414", "\u0415", "\u0416", "\u0417", "\u0418", "\u0419", "\u041A", "\u041B", "\u041C", "\u041D", "\u041E", "\u041F", "\u0420", "\u0421", "\u0422", "\u0423", "\u0424", "\u0425", "\u0426", "\u0427", "\u0428", "\u0429", "\u042A", "\u042B", "\u042C", "\u042D", "\u042E", "\u042F", "\u0401"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        {
            String[] hex = {"\\u0430", "\\u0431", "\\u0432", "\\u0433", "\\u0434", "\\u0435", "\\u0436", "\\u0437", "\\u0438", "\\u0439", "\\u043A", "\\u043B", "\\u043C", "\\u043D", "\\u043E", "\\u043F", "\\u0440", "\\u0441", "\\u0442", "\\u0443", "\\u0444", "\\u0445", "\\u0446", "\\u0447", "\\u0448", "\\u0449", "\\u044A", "\\u044B", "\\u044C", "\\u044D", "\\u044E", "\\u044F", "\\u0451"};
            String[] character = {"\u0430", "\u0431", "\u0432", "\u0433", "\u0434", "\u0435", "\u0436", "\u0437", "\u0438", "\u0439", "\u043A", "\u043B", "\u043C", "\u043D", "\u043E", "\u043F", "\u0440", "\u0441", "\u0442", "\u0443", "\u0444", "\u0445", "\u0446", "\u0447", "\u0448", "\u0449", "\u044A", "\u044B", "\u044C", "\u044D", "\u044E", "\u044F", "\u0451"};
            for (int i = 0; i < hex.length; i++) {
                args.add(Arguments.of(hex[i], character[i]));
            }
        }

        return args.stream();
    }

    /*---
    info: |
        CharacterEscape :: UnicodeEscapeSequence :: u HexDigit HexDigit HexDigit
        HexDigit
    es5id: 15.10.2.10_A4.1_T3
    description: >
        Tested string include RUSSIAN CAPITAL ALPHABET and russian small
        alphabet in unicode notation
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A4.1_T3")
    public void A4_1_T3(String pattern, String string) {
        RegExp re = new RegExp(pattern);
        String[] actual = re.exec(string);
        String[] expected = { string };
        assertThat(actual).isEqualTo(expected);
    }

    public static IntStream A5_1_T1() {
        return "~`!@#$%^&*()-+={[}]|\\:;'<,>./?\"".chars();
    }

    /*---
    info: |
        CharacterEscape :: IdentityEscapeSequence :: SourceCharacter but not
        IdentifierPart
    es5id: 15.10.2.10_A5.1_T1
    description: "Tested string is \"~`!@#$%^&*()-+={[}]|\\\\:;'<,>./?\" + '\"'"
    ---*/
    @ParameterizedTest
    @MethodSource
    @DisplayName("15.10.2.10_A5.1_T1")
    public void A5_1_T1(int c) {
        String s = String.valueOf((char) c);
        RegExp re = new RegExp("\\" + s, "g");
        String[] actual = re.exec("~`!@#$%^&*()-+={[}]|\\:;'<,>./?\"");
        String[] expected = { s };
        assertThat(actual).isEqualTo(expected);
    }
}
