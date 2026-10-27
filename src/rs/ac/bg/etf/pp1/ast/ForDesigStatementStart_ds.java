// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class ForDesigStatementStart_ds extends ForDesigStatementStart {

    private DesigStatement DesigStatement;

    public ForDesigStatementStart_ds (DesigStatement DesigStatement) {
        this.DesigStatement=DesigStatement;
        if(DesigStatement!=null) DesigStatement.setParent(this);
    }

    public DesigStatement getDesigStatement() {
        return DesigStatement;
    }

    public void setDesigStatement(DesigStatement DesigStatement) {
        this.DesigStatement=DesigStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesigStatement!=null) DesigStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigStatement!=null) DesigStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigStatement!=null) DesigStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesigStatementStart_ds(\n");

        if(DesigStatement!=null)
            buffer.append(DesigStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDesigStatementStart_ds]");
        return buffer.toString();
    }
}
