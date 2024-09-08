package expressivo;




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

}
