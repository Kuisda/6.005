package expressivo.Listener;

import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrintEverything implements ExpressionListener {
    @Override
    public void enterRoot(ExpressionParser.RootContext ctx) {
        System.out.println("entering root");
    }

    @Override
    public void exitRoot(ExpressionParser.RootContext ctx) {
        System.out.println("exiting root");
    }

    @Override
    public void enterCalculate(ExpressionParser.CalculateContext ctx) {
        System.out.println("entering calculate");
    }

    @Override
    public void exitCalculate(ExpressionParser.CalculateContext ctx) {
         System.out.println("exiting calculate");
    }

    @Override
    public void enterSum(ExpressionParser.SumContext ctx) {
             System.out.println("entering sum");
    }

    @Override
    public void exitSum(ExpressionParser.SumContext ctx) {
             System.out.println("exitSum"+ctx.primitive().toString());
    }

    @Override
    public void enterPrimitive(ExpressionParser.PrimitiveContext ctx) {
         System.out.println("entering primitive");
    }

    @Override
    public void exitPrimitive(ExpressionParser.PrimitiveContext ctx) {
        if(ctx.NUMBER() != null){
            System.out.println("exiting primitive  number:"+ ctx.NUMBER());
        }
        else if(ctx.sum() != null){
            System.out.println("exiting primitive  sum:"+ ctx.sum());
        }
        else if(ctx.multiply() != null){
            System.out.println("exiting primitive  multiply:"+ ctx.multiply());
        }
    }

    @Override
    public void enterMultiply(ExpressionParser.MultiplyContext ctx) {
         System.out.println("entering multiply");
    }

    @Override
    public void exitMultiply(ExpressionParser.MultiplyContext ctx) {
         System.out.println("exiting multiply " + ctx.NUMBER());
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
        System.err.println("terminal " + terminalNode.getText());

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
