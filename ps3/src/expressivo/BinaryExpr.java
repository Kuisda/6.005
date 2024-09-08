package expressivo;

import java.util.Objects;

public class BinaryExpr implements Expression{
    private final Expression e1;
    private final Expression e2;
    private final String op;

    public BinaryExpr(Expression e1, String op, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;

    }

//    public String getOp(){
//        return op;
//    }
//
//    public Expression getE1(){
//        return e1;
//    }
//
//    public Expression getE2(){
//        return e2;
//    }

//    @Override
//    public double calculate() {
//        if(op.equals("+")){
//            return e1.calculate() + e2.calculate();
//        }else {//op.equals("*")
//            return e1.calculate() * e2.calculate();
//        }
//    }

    @Override
    public String toString(){
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BinaryExpr)) return false;
        BinaryExpr other = (BinaryExpr) obj;
//        return Objects.equals(this.op, other.getOp()) &&
//                ((this.e1.equals(other.getE1())&&(this.e2.equals(other.getE2())))||(this.e1.equals(other.getE2())&&(this.e2.equals(other.getE1()))));

        return Objects.equals(this.op, other.op) && ((this.e1.equals(other.e1) && this.e2.equals(other.e2)) || (this.e1.equals(other.e2) && this.e2.equals(other.e1)));
    }

    @Override
    public int hashCode(){
        if(op.equals("+")){
            return e1.hashCode() + e2.hashCode();
        }else{//op.equals("*")
            return e1.hashCode() * e2.hashCode();
        }
    }

}
