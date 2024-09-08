package expressivo;

public class Number implements Expression{
    private  final double value;

    public Number(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    @Override
    public double calculate(){
        return value;
    }
    @Override
    public String toString(){
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Number number)) return false;
        return number.getValue() == this.value;
    }

    @Override
    public int hashCode(){
        return Double.hashCode(value);
    }
}
