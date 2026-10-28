# MicroJava Compiler

A compiler for the MicroJava programming language, developed as part of university coursework at the University of Belgrade, School of Electrical Engineering.

The compiler translates syntactically and semantically valid MicroJava programs into executable bytecode for the MicroJava Virtual Machine (MJVM).

### Features
- Lexical analysis using JFlex
- LALR(1) syntax analysis and AST generation using AST-CUP
- Semantic analysis with symbol table management and type checking
- Bytecode generation for the MicroJava Virtual Machine
- Support for arrays, functions, expressions, and control flow statements
- Extended `map` operation for element-wise array transformation

### Technologies
Java · JFlex · AST-CUP · Symbol Table Library · MJ Runtime Library