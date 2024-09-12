package expressivo;

import expressivo.Listener.MakeExpression;
import expressivo.Listener.PrintEverything;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class Parser {

    public static Expression parse(String input){
        CharStream stream =  new ANTLRInputStream(input);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);

        ExpressionParser parser = new ExpressionParser(tokens);

        ParseTree tree = parser.root();

        //debug
//        new ParseTreeWalker().walk(new PrintEverything(), tree);

        MakeExpression exprMaker = new MakeExpression();
        new ParseTreeWalker().walk(exprMaker, tree);

        return exprMaker.getExpression();
    }
}



