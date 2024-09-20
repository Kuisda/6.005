/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

/**
 * This file is the grammar file used by ANTLR.
 *
 * In order to compile this file, navigate to this folder
 * (minesweeper/expressivo/parser) and run the following command:
 * 
 * java -jar ../../../lib/antlr.jar Expression.g4
 *
 * PS3 instructions: you are free to change this grammar.
 */

grammar Expression;
import Configuration;

/*
 * Nonterminal rules (a.k.a. parser rules) must be lowercase, e.g. "root" and "sum" below.
 *
 * Terminal rules (a.k.a. lexical rules) must be UPPERCASE, e.g. NUMBER below.
 * Terminals can be defined with quoted strings or regular expressions.
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". The start rule should end with the special token
 * EOF so that it describes the entire input. Below, "root" is the start rule.
 *
 * For more information, see reading 18 about parser generators, which explains
 * how to use Antlr and has links to reference information.
 */


//root : sum EOF;
//sum : primitive ('+' primitive)*;
//primitive : NUMBER | '(' sum ')';
//NUMBER : [0-9]+;


/*thought
*think about the operator priority and an algorithm question to do similar thing that convert string to a tree with operator and number
*For example:1*2+3 can convert to:
*       +
*      / \
*     *   3
*    / \
*   1   2
*you will find that the operator with lower priority will be higher in the tree.
*the main idea is to divide the string to smaller one by the lowerst priority operator,the second lowerest operator,and so on.
*
*we can divide the string to smaller one first by the lowerst priority operator '+‘,
*one thing to pay attention is the parentheses,the equation inside the parentheses should be seened as the highest priority
*so the priority is : (...) > * > +
*
*so the first nontermial is sum which is used to describe the '+'
*'+' divide the string to many parts,I call this parts as primitive
*it consist NUMBER,multiply equation and parentheses sum (parentheses is also been defined,you can use some examples in following expression)
*then it comes to the multiply,it may contain NUMBER and one of the higher priority operator,(...) , and it will represent the '*' operator
*/

root : calculate EOF;
calculate: (sum|multiply)*;
sum : primitive ('+' primitive)*;
primitive : NUMBER | '(' sum ')' | multiply;
multiply: (NUMBER|'(' sum ')')  ('*' (NUMBER|'(' sum ')'))*;
NUMBER : INT | FLOAT | VAR;
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]+;
VAR: [a-zA-Z]+;


/* Tell Antlr to ignore spaces around tokens. */
SPACES : [ ]+ -> skip;
