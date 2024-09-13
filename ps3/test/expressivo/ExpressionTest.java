/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;


import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import expressivo.Listener.PrintEverything;
import org.antlr.v4.runtime.ANTLRInputStream;//there are two ANTLR packages, we need the v4 one
import org.antlr.v4.runtime.CharStream;//there are two ANTLR packages, we need the v4 one
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.util.Stack;

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
        CharStream stream =  new ANTLRInputStream("1+2*3+2*(1+2*5*x)");
        ExpressionLexer lexer = new ExpressionLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser parser = new ExpressionParser(tokens);

        ParseTree tree = parser.root();

        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressionListener listener = new PrintEverything();
        walker.walk(listener, tree);

    }
    @Test
    public void testStack(){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.get(0));
        System.out.println(stack);
    }

    @Test
    public void testParseDouble(){
        String s = "1";
        Double d = 0.0;
        if(s.matches("-?\\d+(\\.\\d+)?")){
            d = Double.parseDouble(s);
        }
        System.out.println(d);
    }

    @Test
    public void testVariable(){
        String output = "1+2*3+2*(1+2*5*x)";
        if(output.matches(".*[a-zA-Z]+.*")){
            System.out.println("variable");
        }else{
            System.out.println("not variable");
        }
    }
    
}
