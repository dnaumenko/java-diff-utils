# Diff Utils

Diff Utils library is an OpenSource library for performing the comparison operations between texts: computing diffs, applying patches, generating unified diffs or parsing them, generating diff output for easy future displaying (like side-by-side view) and so on.

Main reason to build this library was the lack of easy-to-use libraries with all the usual stuff you need while working with diff files. Originally it was inspired by JRCS library and it's nice design of diff module.

## Main Features

* computing the difference between two texts.
* capable to hand more than plain ASCII. Arrays or List of any type that implements hashCode() and equals() correctly can be subject to differencing using this library
* patch and unpatch the text with the given patch
* parsing the unified diff format
* producing human-readable differences

## Algorithms

This library implements Myer's diff algorithm. But it can easily replaced by any other which is better for handing your texts. I have plan to add implementation of some in future.

## Changelog

### Version 1.2
* JDK 1.5 compatibility
* Ant build script
* Generate output in unified diff format (thanks for Bill James)

## To Install

Just add the code below to your maven dependencies:

```xml
<dependency>
    <groupId>com.googlecode.java-diff-utils</groupId>
    <artifactId>diffutils</artifactId>
    <version>1.3.0</version>
</dependency>
```

And for Ivy:

```xml
<dependency org="com.googlecode.java-diff-utils" name="diffutils" rev="1.3.0"/>
```

## Coming eventually

* support for inline diffs in output
* helpers for showing side-by-side, line-by-line diffs or text with inter-line and intra-line change highlights
* customization of diff algorithm for better experience while computing diffs between strings (ignoring blank lines or spaces, etc)
* generating output in other formats (not only unified). E.g. CVS.

## Tutorials

http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=CompararFicherosJavaDiffUtils (in Spanish). Thanks Miguel

## License

This work is licensed under The Apache Software License, Version 1.1.
Reason: The code contains work of HP, which contributed it under Apache-1.1.
[[Example code](https://github.com/apache/wicket/blob/master/wicket-util/src/main/java/org/apache/wicket/util/diff/Delta.java)).
It was easier to change the license to Apache-1.1 than to contact HP Legal for a code created in 2003 at HP Bristol.
