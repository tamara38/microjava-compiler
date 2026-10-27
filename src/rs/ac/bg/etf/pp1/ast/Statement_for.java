// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Statement_for extends Statement {

    private ForNonterm ForNonterm;
    private ForDesigStatementStart ForDesigStatementStart;
    private ForCondition ForCondition;
    private ForDesigStatementEnd ForDesigStatementEnd;
    private Statement Statement;

    public Statement_for (ForNonterm ForNonterm, ForDesigStatementStart ForDesigStatementStart, ForCondition ForCondition, ForDesigStatementEnd ForDesigStatementEnd, Statement Statement) {
        this.ForNonterm=ForNonterm;
        if(ForNonterm!=null) ForNonterm.setParent(this);
        this.ForDesigStatementStart=ForDesigStatementStart;
        if(ForDesigStatementStart!=null) ForDesigStatementStart.setParent(this);
        this.ForCondition=ForCondition;
        if(ForCondition!=null) ForCondition.setParent(this);
        this.ForDesigStatementEnd=ForDesigStatementEnd;
        if(ForDesigStatementEnd!=null) ForDesigStatementEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonterm getForNonterm() {
        return ForNonterm;
    }

    public void setForNonterm(ForNonterm ForNonterm) {
        this.ForNonterm=ForNonterm;
    }

    public ForDesigStatementStart getForDesigStatementStart() {
        return ForDesigStatementStart;
    }

    public void setForDesigStatementStart(ForDesigStatementStart ForDesigStatementStart) {
        this.ForDesigStatementStart=ForDesigStatementStart;
    }

    public ForCondition getForCondition() {
        return ForCondition;
    }

    public void setForCondition(ForCondition ForCondition) {
        this.ForCondition=ForCondition;
    }

    public ForDesigStatementEnd getForDesigStatementEnd() {
        return ForDesigStatementEnd;
    }

    public void setForDesigStatementEnd(ForDesigStatementEnd ForDesigStatementEnd) {
        this.ForDesigStatementEnd=ForDesigStatementEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForNonterm!=null) ForNonterm.accept(visitor);
        if(ForDesigStatementStart!=null) ForDesigStatementStart.accept(visitor);
        if(ForCondition!=null) ForCondition.accept(visitor);
        if(ForDesigStatementEnd!=null) ForDesigStatementEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonterm!=null) ForNonterm.traverseTopDown(visitor);
        if(ForDesigStatementStart!=null) ForDesigStatementStart.traverseTopDown(visitor);
        if(ForCondition!=null) ForCondition.traverseTopDown(visitor);
        if(ForDesigStatementEnd!=null) ForDesigStatementEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonterm!=null) ForNonterm.traverseBottomUp(visitor);
        if(ForDesigStatementStart!=null) ForDesigStatementStart.traverseBottomUp(visitor);
        if(ForCondition!=null) ForCondition.traverseBottomUp(visitor);
        if(ForDesigStatementEnd!=null) ForDesigStatementEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_for(\n");

        if(ForNonterm!=null)
            buffer.append(ForNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesigStatementStart!=null)
            buffer.append(ForDesigStatementStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondition!=null)
            buffer.append(ForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesigStatementEnd!=null)
            buffer.append(ForDesigStatementEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_for]");
        return buffer.toString();
    }
}
