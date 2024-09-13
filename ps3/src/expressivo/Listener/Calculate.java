package expressivo.Listener;

import expressivo.*;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;

/**
 * use the listener when the ast tree not has variable,
 */
public class Calculate implements ExpressionListener {
    private final Stack<Expression> stack = new Stack<>();

    public Expression getExpression(){
        return stack.get(0);
    }
    @Override
    public void enterRoot(ExpressionParser.RootContext ctx) {

    }

    @Override
    public void exitRoot(ExpressionParser.RootContext ctx) {

    }

    @Override
    public void enterCalculate(ExpressionParser.CalculateContext ctx) {

    }

    @Override
    public void exitCalculate(ExpressionParser.CalculateContext ctx) {

    }

    @Override
    public void enterSum(ExpressionParser.SumContext ctx) {

    }

    @Override
    public void exitSum(ExpressionParser.SumContext ctx) {
        List<ExpressionParser.PrimitiveContext> addends = ctx.primitive();
        assert stack.size() >= addends.size();

        assert !addends.isEmpty();
        NumberExpr sum = (NumberExpr)stack.pop();
        double sumNum = sum.getValue();
        for(int i=1;i<addends.size();i++){
            sum = (NumberExpr)stack.pop();
            sumNum += sum.getValue();
        }
        stack.push(new NumberExpr(sumNum));

    }

    @Override
    public void enterPrimitive(ExpressionParser.PrimitiveContext ctx) {
    }

    @Override
    public void exitPrimitive(ExpressionParser.PrimitiveContext ctx) {
        if(ctx.NUMBER() != null){
            double num = Double.parseDouble(ctx.NUMBER().getText());
            stack.push(new NumberExpr(num));
        }

    }

    @Override
    public void enterMultiply(ExpressionParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(ExpressionParser.MultiplyContext ctx) {
        if(ctx.NUMBER()!=null){
//            System.out.println("exitMultiply" + ctx.NUMBER());
            List<TerminalNode> numbers = ctx.NUMBER();
            for(int i=0;i<numbers.size();i++){
                TerminalNode cur = numbers.get(i);
                double num = Double.parseDouble(cur.getText());
                stack.push(new NumberExpr(num));
            }
            NumberExpr mul = (NumberExpr) stack.pop();
            double mulNum = mul.getValue();
            for(int time = ctx.NUMBER().size() + ctx.sum().size()-1;time>0;time--){
                mul = (NumberExpr)stack.pop();
                mulNum *= mul.getValue();
            }
            stack.push(new NumberExpr(mulNum));

        }

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

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
