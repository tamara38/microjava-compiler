// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Designator_len extends Designator {

    private ArrayName ArrayName;

    public Designator_len (ArrayName ArrayName) {
        this.ArrayName=ArrayName;
        if(ArrayName!=null) ArrayName.setParent(this);
    }

    public ArrayName getArrayName() {
        return ArrayName;
    }

    public void setArrayName(ArrayName ArrayName) {
        this.ArrayName=ArrayName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayName!=null) ArrayName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayName!=null) ArrayName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayName!=null) ArrayName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_len(\n");

        if(ArrayName!=null)
            buffer.append(ArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_len]");
        return buffer.toString();
    }
}
