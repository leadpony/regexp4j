# RegExp4J

[![Build Status](https://travis-ci.org/leadpony/regexp4j.svg?branch=main)](https://travis-ci.org/leadpony/regexp4j)

ECMAScript compatible RegExp library for Java

## Limitations

* Matching is performed by code point, not by code unit as in ECMAScript. For example, metacharacter `.` may match a character in supplementary planes encoded as a surrogate pair.
* Regular expression `.` does not match a next-line character `U+0085`, which is categorized as a line terminator in Java.
* Forward references are not supported.
* Group values never be overwritten by `null` in looping by quantifiers.
* The lower and upper bounds in bounded quantifier `{m}`, `{m,}`, and `{m, n}` cannot be greater than `Integer.MAX_VALUE`. 
* Unicode flag `u` is not implemented yet.

## Copyright Notice
Copyright &copy; 2020 the RegExp4J authors. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
