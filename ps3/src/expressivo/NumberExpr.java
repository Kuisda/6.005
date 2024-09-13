package expressivo;

import java.util.Map;
import java.util.Objects;

public class NumberExpr implements Expression{
    private  final double value;
    public NumberExpr(double value){
        this.value = value;
    }

//    public double getValue(){
//        return value;
//    }

//    @Override
//    public double calculate(double x){
//        return x;
//    }
    public double getValue(){return value;}
    @Override
    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof NumberExpr number)) return false;
        return Objects.equals(number.value, this.value);
    }

    @Override
    public int hashCode(){
        return Double.hashCode(value);
    }

    @Override
    public Expression differentiate(String var) {
        return new NumberExpr(0);
    }
    @Override
    public Expression simplify(Map<String,Double> environment){
        return this;
    }
}
