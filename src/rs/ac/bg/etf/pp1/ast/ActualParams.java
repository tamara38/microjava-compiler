// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class ActualParams implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ActParBegin ActParBegin;
    private ActPar ActPar;
    private RActPar RActPar;

    public ActualParams (ActParBegin ActParBegin, ActPar ActPar, RActPar RActPar) {
        this.ActParBegin=ActParBegin;
        if(ActParBegin!=null) ActParBegin.setParent(this);
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.RActPar=RActPar;
        if(RActPar!=null) RActPar.setParent(this);
    }

    public ActParBegin getActParBegin() {
        return ActParBegin;
    }

    public void setActParBegin(ActParBegin ActParBegin) {
        this.ActParBegin=ActParBegin;
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

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParBegin!=null) ActParBegin.accept(visitor);
        if(ActPar!=null) ActPar.accept(visitor);
        if(RActPar!=null) RActPar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParBegin!=null) ActParBegin.traverseTopDown(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(RActPar!=null) RActPar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParBegin!=null) ActParBegin.traverseBottomUp(visitor);
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(RActPar!=null) RActPar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParams(\n");

        if(ActParBegin!=null)
            buffer.append(ActParBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
        buffer.append(") [ActualParams]");
        return buffer.toString();
    }
}
