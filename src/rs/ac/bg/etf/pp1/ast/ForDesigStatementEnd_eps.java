// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class ForDesigStatementEnd_eps extends ForDesigStatementEnd {

    public ForDesigStatementEnd_eps () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesigStatementEnd_eps(\n");

        buffer.append(tab);
        buffer.append(") [ForDesigStatementEnd_eps]");
        return buffer.toString();
    }
}
