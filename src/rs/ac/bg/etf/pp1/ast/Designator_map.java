// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Designator_map extends Designator {

    private ArrayName ArrayName;
    private MapLoop MapLoop;
    private MapIdent MapIdent;
    private Expr Expr;

    public Designator_map (ArrayName ArrayName, MapLoop MapLoop, MapIdent MapIdent, Expr Expr) {
        this.ArrayName=ArrayName;
        if(ArrayName!=null) ArrayName.setParent(this);
        this.MapLoop=MapLoop;
        if(MapLoop!=null) MapLoop.setParent(this);
        this.MapIdent=MapIdent;
        if(MapIdent!=null) MapIdent.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ArrayName getArrayName() {
        return ArrayName;
    }

    public void setArrayName(ArrayName ArrayName) {
        this.ArrayName=ArrayName;
    }

    public MapLoop getMapLoop() {
        return MapLoop;
    }

    public void setMapLoop(MapLoop MapLoop) {
        this.MapLoop=MapLoop;
    }

    public MapIdent getMapIdent() {
        return MapIdent;
    }

    public void setMapIdent(MapIdent MapIdent) {
        this.MapIdent=MapIdent;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayName!=null) ArrayName.accept(visitor);
        if(MapLoop!=null) MapLoop.accept(visitor);
        if(MapIdent!=null) MapIdent.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayName!=null) ArrayName.traverseTopDown(visitor);
        if(MapLoop!=null) MapLoop.traverseTopDown(visitor);
        if(MapIdent!=null) MapIdent.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayName!=null) ArrayName.traverseBottomUp(visitor);
        if(MapLoop!=null) MapLoop.traverseBottomUp(visitor);
        if(MapIdent!=null) MapIdent.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_map(\n");

        if(ArrayName!=null)
            buffer.append(ArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MapLoop!=null)
            buffer.append(MapLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MapIdent!=null)
            buffer.append(MapIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_map]");
        return buffer.toString();
    }
}
