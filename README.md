# RegExp4J
[![Apache 2.0 License](https://img.shields.io/:license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/org.leadpony.regexp4j/regexp4j.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.leadpony.regexp4j%22%20AND%20a:%22regexp4j%22)
[![Javadocs](https://www.javadoc.io/badge/org.leadpony.regexp4j/regexp4j.svg?color=green)](https://www.javadoc.io/doc/org.leadpony.regexp4j/regexp4j)
[![Build Status](https://travis-ci.org/leadpony/regexp4j.svg?branch=main)](https://travis-ci.org/leadpony/regexp4j)

ECMAScript compatible RegExp library for Java

## Using as a Maven dependency

```xml
<dependency>
    <groupId>org.leadpony.regexp4j</groupId>
    <artifactId>regexp4j</artifactId>
    <version>0.1.0</version>
</dependency>
```

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
