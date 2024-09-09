/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;


import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.runtime.ANTLRInputStream;//there are two ANTLR packages, we need the v4 one
import org.antlr.v4.runtime.CharStream;//there are two ANTLR packages, we need the v4 one
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Expression

    @Test
    public void testPrintParserTree() throws IOException {
        CharStream stream =  new ANTLRInputStream("1+2*3+(1+2*5)");
        ExpressionLexer lexer = new ExpressionLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser parser = new ExpressionParser(tokens);

        ParseTree tree = parser.root();

        System.out.println(tree.toStringTree(parser));


    }
    
}
