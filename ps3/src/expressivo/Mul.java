package expressivo;

public class Mul implements Expression{
    private final Expression m1;
    private final Expression m2;

    public Mul(Expression m1, Expression m2) {
        this.m1 = m1;
        this.m2 = m2;
    }


    @Override
    public double calculate() {
        return m1.calculate() * m2.calculate();
    }

    @Override
    public String toString() {
        return m1.toString() + " * " + m2.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Mul other)) return false;
        return (m1.equals(other.m1) && m2.equals(other.m2))|| (m1.equals(other.m2) && m2.equals(other.m1));
    }

    @Override
    public int hashCode(){
        return (int) (m1.calculate() * m2.calculate());
    }
}
