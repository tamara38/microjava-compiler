// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Statement_if extends Statement {

    private IfStart IfStart;
    private Condition Condition;
    private Statement Statement;
    private BranchDecisioner BranchDecisioner;
    private ElseStatement ElseStatement;

    public Statement_if (IfStart IfStart, Condition Condition, Statement Statement, BranchDecisioner BranchDecisioner, ElseStatement ElseStatement) {
        this.IfStart=IfStart;
        if(IfStart!=null) IfStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.BranchDecisioner=BranchDecisioner;
        if(BranchDecisioner!=null) BranchDecisioner.setParent(this);
        this.ElseStatement=ElseStatement;
        if(ElseStatement!=null) ElseStatement.setParent(this);
    }

    public IfStart getIfStart() {
        return IfStart;
    }

    public void setIfStart(IfStart IfStart) {
        this.IfStart=IfStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public BranchDecisioner getBranchDecisioner() {
        return BranchDecisioner;
    }

    public void setBranchDecisioner(BranchDecisioner BranchDecisioner) {
        this.BranchDecisioner=BranchDecisioner;
    }

    public ElseStatement getElseStatement() {
        return ElseStatement;
    }

    public void setElseStatement(ElseStatement ElseStatement) {
        this.ElseStatement=ElseStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfStart!=null) IfStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(BranchDecisioner!=null) BranchDecisioner.accept(visitor);
        if(ElseStatement!=null) ElseStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfStart!=null) IfStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(BranchDecisioner!=null) BranchDecisioner.traverseTopDown(visitor);
        if(ElseStatement!=null) ElseStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfStart!=null) IfStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(BranchDecisioner!=null) BranchDecisioner.traverseBottomUp(visitor);
        if(ElseStatement!=null) ElseStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_if(\n");

        if(IfStart!=null)
            buffer.append(IfStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BranchDecisioner!=null)
            buffer.append(BranchDecisioner.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStatement!=null)
            buffer.append(ElseStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_if]");
        return buffer.toString();
    }
}
