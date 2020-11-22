// Copyright 2009 the Sputnik authors.  All rights reserved.
// This code is governed by the BSD license found in the LICENSE file.
package org.ecmainternational.test262.regexp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.leadpony.regexp4j.RegExp;

/*---
info: XML Shallow Parsing with Regular Expressions
es5id: 15.10.2_A1_T1
description: "See bug http://bugzilla.mozilla.org/show_bug.cgi?id=103087"
---*/

// REX/Javascript 1.0
// Robert D. Cameron "REX: XML Shallow Parsing with Regular Expressions",
// Technical Report TR 1998-17, School of Computing Science, Simon Fraser
// University, November, 1998.
// Copyright (c) 1998, Robert D. Cameron.
// The following code may be freely used and distributed provided that
// this copyright and citation notice remains intact and that modifications
// or additions are clearly identified.
public class Test15_10_2 {

    static final String TextSE = "[^<]+";
    static final String UntilHyphen = "[^-]*-";
    static final String Until2Hyphens = UntilHyphen + "([^-]" + UntilHyphen + ")*-";
    static final String CommentCE = Until2Hyphens + ">?";
    static final String UntilRSBs = "[^\\]]*\\]([^\\]]+\\])*\\]+";
    static final String CDATA_CE = UntilRSBs + "([^\\]>]" + UntilRSBs + ")*>";
    static final String S = "[ \\n\\t\\r]+";
    static final String NameStrt = "[A-Za-z_:]|[^\\x00-\\x7F]";
    static final String NameChar = "[A-Za-z0-9_:.-]|[^\\x00-\\x7F]";
    static final String Name = "(" + NameStrt + ")(" + NameChar + ")*";
    static final String QuoteSE = "\"[^\"]" + "*" + '"' + "|'[^']*'";
    static final String DT_IdentSE = S + Name + "(" + S + "(" + Name + "|" + QuoteSE + "))*";
    static final String MarkupDeclCE = "([^\\]\"'><]+|" + QuoteSE + ")*>";
    static final String S1 = "[\\n\\r\\t ]";
    static final String UntilQMs = "[^?]*\\?+";
    static final String PI_Tail = "\\?>|" + S1 + UntilQMs + "([^>?]" + UntilQMs + ")*>";
    static final String DT_ItemSE = "<(!(--" + Until2Hyphens + ">|[^-]" + MarkupDeclCE + ")|\\?" + Name + "(" + PI_Tail
            + "))|%" + Name + ";|" + S;
    static final String DocTypeCE = DT_IdentSE + "(" + S + ")?(\\[(" + DT_ItemSE + ")*\\](" + S + ")?)?>?";
    static final String DeclCE = "--(" + CommentCE + ")?|\\[CDATA\\[(" + CDATA_CE + ")?|DOCTYPE(" + DocTypeCE + ")?";
    static final String PI_CE = Name + "(" + PI_Tail + ")?";
    static final String EndTagCE = Name + "(" + S + ")?>?";
    static final String AttValSE = "\"[^<\"]" + "*" + '"' + "|'[^<']*'";
    static final String ElemTagCE = Name + "(" + S + Name + "(" + S + ")?=(" + S + ")?(" + AttValSE + "))*(" + S
            + ")?/?>?";
    static final String MarkupSPE = "<(!(" + DeclCE + ")?|\\?(" + PI_CE + ")?|/(" + EndTagCE + ")?|(" + ElemTagCE
            + ")?)";
    static final String XML_SPE = TextSE + "|" + MarkupSPE;

    static final String HTML = "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "      xmlns:xlink=\"http://www.w3.org/XML/XLink/0.9\">\n" +
            "  <head><title>Three Namespaces</title></head>\n" +
            "  <body>\n" +
            "    <h1 align=\"center\">An Ellipse and a Rectangle</h1>\n" +
            "    <svg xmlns=\"http://www.w3.org/Graphics/SVG/SVG-19991203.dtd\"\n" +
            "         width=\"12cm\" height=\"10cm\">\n" +
            "      <ellipse rx=\"110\" ry=\"130\" />\n" +
            "      <rect x=\"4cm\" y=\"1cm\" width=\"3cm\" height=\"6cm\" />\n" +
            "    </svg>\n" +
            "    <p xlink:type=\"simple\" xlink:href=\"ellipses.html\">\n" +
            "      More about ellipses\n" +
            "    </p>\n" +
            "    <p xlink:type=\"simple\" xlink:href=\"rectangles.html\">\n" +
            "      More about rectangles\n" +
            "    </p>\n" +
            "    <hr/>\n" +
            "    <p>Last Modified February 13, 2000</p>\n" +
            "  </body>\n" +
            "</html>";

    @ParameterizedTest
    @ValueSource(strings = {
            TextSE, UntilHyphen, Until2Hyphens, CommentCE, UntilRSBs, CDATA_CE, S, NameStrt, NameChar,
            Name, QuoteSE, DT_IdentSE, MarkupDeclCE, S1, UntilQMs, PI_Tail, DT_ItemSE, DocTypeCE, DeclCE,
            PI_CE, EndTagCE, AttValSE, ElemTagCE, MarkupSPE, XML_SPE
    })
    public void test(String pattern) {
        RegExp re = new RegExp(pattern);
        re.test(HTML);
    }
}
