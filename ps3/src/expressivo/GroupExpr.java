package expressivo;

public class GroupExpr implements Expression{
    private final Expression e;

    public GroupExpr(Expression e){
        this.e = e;
    }

//    public double calculate(){
//        return e.calculate();
//    }

    public String toString(){
        return "(" + e.toString() + ")";
    }

    public boolean equals(Object obj){
        if(!(obj instanceof  GroupExpr)) return false;
        GroupExpr other = (GroupExpr) obj;
        return e.equals(other.e);
    }

    public int hashCode(){
        return e.hashCode();
    }


}
