// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Expr_aritm extends Expr {

    private RAdd RAdd;

    public Expr_aritm (RAdd RAdd) {
        this.RAdd=RAdd;
        if(RAdd!=null) RAdd.setParent(this);
    }

    public RAdd getRAdd() {
        return RAdd;
    }

    public void setRAdd(RAdd RAdd) {
        this.RAdd=RAdd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RAdd!=null) RAdd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RAdd!=null) RAdd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RAdd!=null) RAdd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_aritm(\n");

        if(RAdd!=null)
            buffer.append(RAdd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr_aritm]");
        return buffer.toString();
    }
}
