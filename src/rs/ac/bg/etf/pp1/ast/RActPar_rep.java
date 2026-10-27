// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class RActPar_rep extends RActPar {

    private ActPar ActPar;
    private RActPar RActPar;

    public RActPar_rep (ActPar ActPar, RActPar RActPar) {
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.RActPar=RActPar;
        if(RActPar!=null) RActPar.setParent(this);
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public RActPar getRActPar() {
        return RActPar;
    }

    public void setRActPar(RActPar RActPar) {
        this.RActPar=RActPar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPar!=null) ActPar.accept(visitor);
        if(RActPar!=null) RActPar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(RActPar!=null) RActPar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(RActPar!=null) RActPar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RActPar_rep(\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RActPar!=null)
            buffer.append(RActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RActPar_rep]");
        return buffer.toString();
    }
}
