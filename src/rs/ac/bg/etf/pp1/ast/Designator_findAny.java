// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Designator_findAny extends Designator {

    private ArrayName ArrayName;
    private FindAny FindAny;
    private Expr Expr;
    private FindAnyCheck FindAnyCheck;

    public Designator_findAny (ArrayName ArrayName, FindAny FindAny, Expr Expr, FindAnyCheck FindAnyCheck) {
        this.ArrayName=ArrayName;
        if(ArrayName!=null) ArrayName.setParent(this);
        this.FindAny=FindAny;
        if(FindAny!=null) FindAny.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.FindAnyCheck=FindAnyCheck;
        if(FindAnyCheck!=null) FindAnyCheck.setParent(this);
    }

    public ArrayName getArrayName() {
        return ArrayName;
    }

    public void setArrayName(ArrayName ArrayName) {
        this.ArrayName=ArrayName;
    }

    public FindAny getFindAny() {
        return FindAny;
    }

    public void setFindAny(FindAny FindAny) {
        this.FindAny=FindAny;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public FindAnyCheck getFindAnyCheck() {
        return FindAnyCheck;
    }

    public void setFindAnyCheck(FindAnyCheck FindAnyCheck) {
        this.FindAnyCheck=FindAnyCheck;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayName!=null) ArrayName.accept(visitor);
        if(FindAny!=null) FindAny.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(FindAnyCheck!=null) FindAnyCheck.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayName!=null) ArrayName.traverseTopDown(visitor);
        if(FindAny!=null) FindAny.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(FindAnyCheck!=null) FindAnyCheck.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayName!=null) ArrayName.traverseBottomUp(visitor);
        if(FindAny!=null) FindAny.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(FindAnyCheck!=null) FindAnyCheck.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_findAny(\n");

        if(ArrayName!=null)
            buffer.append(ArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAny!=null)
            buffer.append(FindAny.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAnyCheck!=null)
            buffer.append(FindAnyCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_findAny]");
        return buffer.toString();
    }
}
