// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.leadpony.regexp4j.RegExp;

public class Test15_10_8 {

    @Test
    @DisplayName("15.10.2.8_A1_T1")
    public void A1_T1() {
        RegExp re = new RegExp("(?=(a+))");
        String[] actual = re.exec("baaabac");
        String[] expected = {"", "aaa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A1_T2")
    public void A1_T2() {
        RegExp re = new RegExp("(?=(a+))a*b\\1");
        String[] actual = re.exec("baaabac");
        String[] expected = {"aba", "a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A1_T3")
    public void A1_T3() {
        RegExp re = new RegExp("[Jj]ava([Ss]cript)?(?=\\:)");
        String[] actual = re.exec("just Javascript: the way af jedi");
        String[] expected = {"Javascript", "script"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A1_T4")
    public void A1_T4() {
        RegExp re = new RegExp("[Jj]ava([Ss]cript)?(?=\\:)");
        String[] actual = re.exec("taste of java: the cookbook ");
        String[] expected = {"java", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A1_T5")
    public void A1_T5() {
        RegExp re = new RegExp("[Jj]ava([Ss]cript)?(?=\\:)");
        boolean actual = re.test("rhino is JavaScript engine");
        assertThat(actual).isFalse();
    }

    /*---
    info: |
        The form (?! Disjunction ) specifies a zero-width negative lookahead.
        In order for it to succeed, the pattern inside Disjunction must fail to match at the current position.
        The current position is not advanced before matching the sequel
    es5id: 15.10.2.8_A2_T1
    description: >
        Execute /(.*?)a(?!(a+)b\2c)\2(.*)/.exec("baaabaac") and check
        results
    ---*/
    @Test
    @DisplayName("15.10.2.8_A2_T1")
    @Disabled
    public void A2_T1() {
        RegExp re = new RegExp("(.*?)a(?!(a+)b\\2c)\\2(.*)");
        String[] actual = re.exec("baaabaac");
        String[] expected = {"baaabaac", "ba", null, "abaac"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T2")
    public void A2_T2() {
        RegExp re = new RegExp("Java(?!Script)([A-Z]\\w*)");
        String[] actual = re.exec("using of JavaBeans technology");
        String[] expected = {"JavaBeans", "Beans"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T3")
    public void A2_T3() {
        RegExp re = new RegExp("Java(?!Script)([A-Z]\\w*)");
        boolean actual = re.test("using of Java language");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.8_A2_T4")
    public void A2_T4() {
        RegExp re = new RegExp("Java(?!Script)([A-Z]\\w*)");
        boolean actual = re.test("i'm a JavaScripter ");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.8_A2_T5")
    public void A2_T5() {
        RegExp re = new RegExp("Java(?!Script)([A-Z]\\w*)");
        String[] actual = re.exec("JavaScr oops ipt ");
        String[] expected = {"JavaScr", "Scr"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T6")
    public void A2_T6() {
        RegExp re = new RegExp("(\\.(?!com|org)|\\/)");
        String[] actual = re.exec("ah.info");
        String[] expected = {".", "."};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T7")
    public void A2_T7() {
        RegExp re = new RegExp("(\\.(?!com|org)|\\/)");
        String[] actual = re.exec("ah/info");
        String[] expected = {"/", "/"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T8")
    public void A2_T8() {
        RegExp re = new RegExp("(\\.(?!com|org)|\\/)");
        boolean actual = re.test("ah.com");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.8_A2_T9")
    public void A2_T9() {
        RegExp re = new RegExp("(?!a|b)|c");
        String[] actual = re.exec("");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T10")
    public void A2_T10() {
        RegExp re = new RegExp("(?!a|b)|c");
        String[] actual = re.exec("bc");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A2_T11")
    public void A2_T11() {
        RegExp re = new RegExp("(?!a|b)|c");
        String[] actual = re.exec("d");
        String[] expected = {""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T1")
    public void A3_T1() {
        RegExp re = new RegExp("([Jj]ava([Ss]cript)?)\\sis\\s(fun\\w*)");
        String[] actual = re.exec("Learning javaScript is funny, really");
        String[] expected = {"javaScript is funny", "javaScript", "Script", "funny"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T2")
    public void A3_T2() {
        RegExp re = new RegExp("([Jj]ava([Ss]cript)?)\\sis\\s(fun\\w*)");
        String[] actual = re.exec("Developing with Java is fun, try it");
        String[] expected = {"Java is fun", "Java", null, "fun"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T3")
    public void A3_T3() {
        RegExp re = new RegExp("([Jj]ava([Ss]cript)?)\\sis\\s(fun\\w*)");
        boolean actual = re.test("Developing with JavaScript is dangerous, do not try it without assistance");
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("15.10.2.8_A3_T4")
    public void A3_T4() {
        RegExp re = new RegExp("(abc)");
        String[] actual = re.exec("abc");
        String[] expected = {"abc", "abc"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T5")
    public void A3_T5() {
        RegExp re = new RegExp("a(bc)d(ef)g");
        String[] actual = re.exec("abcdefg");
        String[] expected = {"abcdefg", "bc", "ef"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T6")
    public void A3_T6() {
        RegExp re = new RegExp("(.{3})(.{4})");
        String[] actual = re.exec("abcdefgh");
        String[] expected = {"abcdefg","abc","defg"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T7")
    public void A3_T7() {
        RegExp re = new RegExp("(aa)bcd\\1");
        String[] actual = re.exec("aabcdaabcd");
        String[] expected = {"aabcdaa","aa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T8")
    public void A3_T8() {
        RegExp re = new RegExp("(aa).+\\1");
        String[] actual = re.exec("aabcdaabcd");
        String[] expected = {"aabcdaa","aa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T9")
    public void A3_T9() {
        RegExp re = new RegExp("(.{2}).+\\1");
        String[] actual = re.exec("aabcdaabcd");
        String[] expected = {"aabcdaa","aa"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T10")
    public void A3_T10() {
        RegExp re = new RegExp("(\\d{3})(\\d{3})\\1\\2");
        String[] actual = re.exec("123456123456");
        String[] expected = {"123456123456","123","456"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T11")
    public void A3_T11() {
        RegExp re = new RegExp("a(..(..)..)");
        String[] actual = re.exec("abcdefgh");
        String[] expected = {"abcdefg","bcdefg","de"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T12")
    public void A3_T12() {
        RegExp re = new RegExp("(a(b(c)))(d(e(f)))");
        String[] actual = re.exec("xabcdefg");
        String[] expected = {"abcdef","abc","bc","c","def","ef","f"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T13")
    public void A3_T13() {
        RegExp re = new RegExp("(a(b(c)))(d(e(f)))\\2\\5");
        String[] actual = re.exec("xabcdefbcefg");
        String[] expected = {"abcdefbcef","abc","bc","c","def","ef","f"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T14")
    public void A3_T14() {
        RegExp re = new RegExp("a(.?)b\\1c\\1d\\1");
        String[] actual = re.exec("abcd");
        String[] expected = {"abcd",""};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T15")
    public void A3_T15() {
        final int numParens = 200;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numParens; i++) {
            builder.append('(');
        }
        builder.append("hello");
        for (int i = 0; i < numParens; i++) {
            builder.append(')');
        }

        RegExp re = new RegExp(builder.toString());
        String[] actual = re.exec("hello");

        List<String> expected = new ArrayList<>(201);
        for (int i = 0; i <= numParens; i++) {
            expected.add("hello");
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T16")
    public void A3_T16() {
        final int numParens = 200;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numParens; i++) {
            builder.append("(?:");
        }
        builder.append("hello");
        for (int i = 0; i < numParens; i++) {
            builder.append(')');
        }

        RegExp re = new RegExp(builder.toString());
        String[] actual = re.exec("hello");
        String[] expected = {"hello"};

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T17")
    public void A3_T17() {
        String body = new StringBuilder()
                .append("<body onXXX=\"alert(event.type);\">\n")
                .append("<p>Kibology for all</p>\n")
                .append("<p>All for Kibology</p>\n")
                .append("</body>")
                .toString();

        String html = new StringBuilder()
                .append("<html>\n")
                .append(body)
                .append("\n</html>")
                .toString();

        RegExp re = new RegExp("<body.*>((.*\\n?)*?)<\\/body>", "i");
        String[] actual = re.exec(html);
        String[] expected = {
                body,
                "\n<p>Kibology for all</p>\n<p>All for Kibology</p>\n",
                "<p>All for Kibology</p>\n"
                };

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T18")
    public void A3_T18() {
        RegExp re = new RegExp("(\\|)([\\w\\x81-\\xff ]*)(\\|)([\\/a-z][\\w:\\/\\.]*\\.[a-z]{3,4})(\\|)", "ig");
        String actual = re.replace("To sign up click |here|https:www.xxxx.org/subscribe.htm|",
                "<a href=\"$4\">$2</a>");
        String expected = "To sign up click <a href=\"https:www.xxxx.org/subscribe.htm\">here</a>";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T19")
    public void A3_T19() {
        RegExp re = new RegExp("([\\S]+([ \\t]+[\\S]+)*)[ \\t]*=[ \\t]*[\\S]+");
        String[] actual = re.exec("Course_Creator = Test");
        String[] expected = {"Course_Creator = Test", "Course_Creator", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T20")
    public void A3_T20() {
        RegExp re = new RegExp("^(A)?(A.*)$");
        String[] actual = re.exec("AAA");
        String[] expected = {"AAA", "A", "AA"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T21")
    public void A3_T21() {
        RegExp re = new RegExp("^(A)?(A.*)$");
        String[] actual = re.exec("AA");
        String[] expected = {"AA", "A", "A"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T22")
    public void A3_T22() {
        RegExp re = new RegExp("^(A)?(A.*)$");
        String[] actual = re.exec("A");
        String[] expected = {"A", null, "A"};
        assertThat(actual).isEqualTo(expected);
    }

    /*
     * original sttring is:
     * "zxcasd;fl\\\  ^AAAaaAAaaaf;lrlrzs"
     */
    @Test
    @DisplayName("15.10.2.8_A3_T23")
    public void A3_T23() {
        String string = "zxcasd;fl\\  ^AAAaaAAaaaf;lrlrzs";
        RegExp re = new RegExp("(A)?(A.*)");
        String[] actual = re.exec(string);
        String[] expected = {"AAAaaAAaaaf;lrlrzs", "A", "AAaaAAaaaf;lrlrzs"};
        assertThat(actual).isEqualTo(expected);
    }

    /*
     * original sttring is:
     * "zxcasd;fl\\\  ^AAaaAAaaaf;lrlrzs"
     */
    @Test
    @DisplayName("15.10.2.8_A3_T24")
    public void A3_T24() {
        String string = "zxcasd;fl\\  ^AAaaAAaaaf;lrlrzs";
        RegExp re = new RegExp("(A)?(A.*)");
        String[] actual = re.exec(string);
        String[] expected = {"AAaaAAaaaf;lrlrzs","A","AaaAAaaaf;lrlrzs"};
        assertThat(actual).isEqualTo(expected);
    }

    /*
     * original sttring is:
     * "zxcasd;fl\\\  ^AaaAAaaaf;lrlrzs"
     */
    @Test
    @DisplayName("15.10.2.8_A3_T25")
    public void A3_T25() {
        String string = "zxcasd;fl\\  ^AaaAAaaaf;lrlrzs";
        RegExp re = new RegExp("(A)?(A.*)");
        String[] actual = re.exec(string);
        String[] expected = {"AaaAAaaaf;lrlrzs", null, "AaaAAaaaf;lrlrzs"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T26")
    public void A3_T26() {
        RegExp re = new RegExp("(a)?a");
        String[] actual = re.exec("a");
        String[] expected = {"a", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T27")
    public void A3_T27() {
        RegExp re = new RegExp("a|(b)");
        String[] actual = re.exec("a");
        String[] expected = {"a", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T28")
    public void A3_T28() {
        RegExp re = new RegExp("(a)?(a)");
        String[] actual = re.exec("a");
        String[] expected = {"a", null, "a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T29")
    public void A3_T29() {
        RegExp re = new RegExp("^([a-z]+)*[a-z]$");
        String[] actual = re.exec("a");
        String[] expected = {"a", null};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T30")
    public void A3_T30() {
        RegExp re = new RegExp("^([a-z]+)*[a-z]$");
        String[] actual = re.exec("ab");
        String[] expected = {"ab", "a"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T31")
    public void A3_T31() {
        RegExp re = new RegExp("^([a-z]+)*[a-z]$");
        String[] actual = re.exec("abc");
        String[] expected = {"abc", "ab"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T32")
    public void A3_T32() {
        RegExp re = new RegExp("^(([a-z]+)*[a-z]\\.)+[a-z]{2,}$");
        String[] actual = re.exec("www.netscape.com");
        String[] expected = {"www.netscape.com", "netscape.", "netscap"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A3_T33")
    public void A3_T33() {
        RegExp re = new RegExp("^(([a-z]+)*([a-z])\\.)+[a-z]{2,}$");
        String[] actual = re.exec("www.netscape.com");
        String[] expected = {"www.netscape.com", "netscape.", "netscap", "e"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T1")
    public void A4_T1() {
        RegExp re = new RegExp("ab.de");
        String[] actual = re.exec("abcde");
        String[] expected = {"abcde"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T2")
    public void A4_T2() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("line 1\nline 2");
        String[] expected = {"line 1"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T3")
    public void A4_T3() {
        RegExp re = new RegExp(".*a.*");
        String[] actual = re.exec("this is a test");
        String[] expected = {"this is a test"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T4")
    public void A4_T4() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("this is a *&^%$# test");
        String[] expected = {"this is a *&^%$# test"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T5")
    public void A4_T5() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("....");
        String[] expected = {"...."};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T6")
    public void A4_T6() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("abcdefghijklmnopqrstuvwxyz");
        String[] expected = {"abcdefghijklmnopqrstuvwxyz"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T7")
    public void A4_T7() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        String[] expected = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T8")
    public void A4_T8() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("`1234567890-=~!@#$%^&*()_+");
        String[] expected = {"`1234567890-=~!@#$%^&*()_+"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A4_T9")
    public void A4_T9() {
        RegExp re = new RegExp(".+");
        String[] actual = re.exec("|\\[{]};:\"\',<>.?/");
        String[] expected = {"|\\[{]};:\"\',<>.?/"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A5_T1")
    public void A5_T1() {
        RegExp re = new RegExp("[a-z]+", "ig");
        String[] actual = re.exec("ABC def ghi");
        String[] expected = {"ABC"};
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("15.10.2.8_A5_T2")
    public void A5_T2() {
        RegExp re = new RegExp("[a-z]+");
        String[] actual = re.exec("ABC def ghi");
        String[] expected = {"def"};
        assertThat(actual).isEqualTo(expected);
    }
}
