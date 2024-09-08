package expressivo;

public class Add implements Expression{
    private final Expression e1;
    private final Expression e2;

    public Add(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public double calculate(){
        return e1.calculate() + e2.calculate();
    }

    @Override
    public String toString() {
        return e1.toString() + "+" + e2.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Add other)) return false;
        return (e1.equals(other.e1) && e2.equals(other.e2))||(e1.equals(other.e2) && e2.equals(other.e1));
    }

    @Override
    public int hashCode(){
        return (int) (e1.calculate() + e2.calculate());
    }

}
