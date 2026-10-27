// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class CondTerm implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private CondFact CondFact;
    private RCondFact RCondFact;

    public CondTerm (CondFact CondFact, RCondFact RCondFact) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.RCondFact=RCondFact;
        if(RCondFact!=null) RCondFact.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public RCondFact getRCondFact() {
        return RCondFact;
    }

    public void setRCondFact(RCondFact RCondFact) {
        this.RCondFact=RCondFact;
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
        if(CondFact!=null) CondFact.accept(visitor);
        if(RCondFact!=null) RCondFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(RCondFact!=null) RCondFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(RCondFact!=null) RCondFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTerm(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RCondFact!=null)
            buffer.append(RCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTerm]");
        return buffer.toString();
    }
}
