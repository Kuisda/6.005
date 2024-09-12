package expressivo;


import java.util.Map;

public class VariableExpr implements Expression{
    private String variable;


    public VariableExpr(String variable){
        this.variable = variable;
    }

    @Override
    public String toString(){
        return variable;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof  VariableExpr)) return false;
        VariableExpr other = (VariableExpr) obj;
        return this.variable.equals(other.variable);
    }

    @Override
    public int hashCode(){
        return variable.hashCode();
    }

    @Override
    public Expression differentiate(String var) {
        if(variable.equals(var))return new NumberExpr(1);
        return new NumberExpr(0);
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        if(environment.containsKey(variable)){
            return new NumberExpr(environment.get(variable));
        }
        return this;
    }

}
