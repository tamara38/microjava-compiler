// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class CondFact_r extends CondFact {

    private RAdd RAdd;
    private Relop Relop;
    private RAdd RAdd1;

    public CondFact_r (RAdd RAdd, Relop Relop, RAdd RAdd1) {
        this.RAdd=RAdd;
        if(RAdd!=null) RAdd.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.RAdd1=RAdd1;
        if(RAdd1!=null) RAdd1.setParent(this);
    }

    public RAdd getRAdd() {
        return RAdd;
    }

    public void setRAdd(RAdd RAdd) {
        this.RAdd=RAdd;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public RAdd getRAdd1() {
        return RAdd1;
    }

    public void setRAdd1(RAdd RAdd1) {
        this.RAdd1=RAdd1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RAdd!=null) RAdd.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(RAdd1!=null) RAdd1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RAdd!=null) RAdd.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(RAdd1!=null) RAdd1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RAdd!=null) RAdd.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(RAdd1!=null) RAdd1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_r(\n");

        if(RAdd!=null)
            buffer.append(RAdd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RAdd1!=null)
            buffer.append(RAdd1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_r]");
        return buffer.toString();
    }
}
