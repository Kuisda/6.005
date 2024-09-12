package expressivo;

import java.util.Map;

public class MulExpr implements Expression{
    private Expression e1;
    private Expression e2;

    public MulExpr(Expression e1, Expression e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public String toString(){
        return e1.toString() + " * " + e2.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof MulExpr)) return false;
        MulExpr other = (MulExpr) obj;

        return (this.e1.equals(other.e1) && this.e2.equals(other.e2)) || (this.e1.equals(other.e2) && this.e2.equals(other.e1));
    }

    @Override
    public int hashCode(){
        return e1.hashCode() * e2.hashCode();
    }

    @Override
    public Expression differentiate(String var) {
        return new AddExpr(new MulExpr(e1.differentiate(var),e2),new MulExpr(e1,e2.differentiate(var)));
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        return new MulExpr(e1.simplify(environment),e2.simplify(environment));
    }


}
