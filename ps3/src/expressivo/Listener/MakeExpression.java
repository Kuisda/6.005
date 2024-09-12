package expressivo.Listener;

import expressivo.*;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;

public class MakeExpression implements ExpressionListener {
    private final Stack<Expression> stack = new Stack<>();

    private boolean isDouble(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }

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
        //addends.size() represents the number of primitive  == the expression to be added
        List<ExpressionParser.PrimitiveContext> addends = ctx.primitive();
        assert stack.size() >= addends.size();

        assert !addends.isEmpty();
        Expression sum = stack.pop();

        for(int i=1;i<addends.size();i++){
            sum = new AddExpr(stack.pop(),sum);
        }
        stack.push(sum);
    }

    @Override
    public void enterPrimitive(ExpressionParser.PrimitiveContext ctx) {

    }

    @Override
    public void exitPrimitive(ExpressionParser.PrimitiveContext ctx) {
        if(ctx.NUMBER() != null){
            Expression e;
            if(isDouble(ctx.NUMBER().getText())){
                e = new NumberExpr(Double.parseDouble(ctx.NUMBER().getText()));
            }else{
                e = new VariableExpr(ctx.NUMBER().getText());
            }
            stack.push(e);
        }
    }

    @Override
    public void enterMultiply(ExpressionParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(ExpressionParser.MultiplyContext ctx) {
        //extract all the number and variable ,convert to AddExpr and MulExpr
        //build MulExpr like MulExpr(NumExpr,MulExpr(NumExpr,MulExpr(NumExpr,VariableExpr)))
        if(ctx.NUMBER()!=null){
//            System.out.println("exitMultiply" + ctx.NUMBER());
            List<TerminalNode> numbers = ctx.NUMBER();
            for(int i=0;i<numbers.size();i++){
                TerminalNode cur = numbers.get(i);
                Expression e1;
                if(isDouble(cur.getText())){
                    e1 = new NumberExpr( Double.parseDouble(cur.getText()));
                }else{
                    e1 = new VariableExpr(cur.getText());
                }
//                System.out.println(e1);
                stack.push(e1);
            }

            Expression mul = stack.pop();
            for(int time = ctx.NUMBER().size() + ctx.sum().size()-1;time>0;time--){
                mul = new MulExpr(stack.pop(),mul);
            }
            stack.push(mul);

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
