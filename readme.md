# Rest server benchmarks

#### Index
- [Introduction](https://github.com/Technolords/benchmark-rest-server/tree/master#introduction)

## Introduction
As the open source communities move forward, and new libraries are created, it raises
some questions:
- Are we re-inventing the wheel?
- Which library is the most efficient (performance wise)?
- Which library is the most friendly (developer wise)?

This project contains Maven modules, where each module represents a different
stack, i.e. a different implementation. All the module shares an implementation
of a single REST API method. Lastly, all the implementations can be used
in a microservice style, i.e. executable Jar files.

## Matrix
The matrix below gives an overview of the modules and their related stacks (ordered by module name):
- module: the name of the Maven module
- container: the web container used for deployment
- wiring: what kind of [IoC](https://en.wikipedia.org/wiki/Inversion_of_control) is used
- stack: the used java libraries

Module | Container | Wiring | Stack
-------|-----------|--------|------

