// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class RConstItem_rep extends RConstItem {

    private ConstItem ConstItem;
    private RConstItem RConstItem;

    public RConstItem_rep (ConstItem ConstItem, RConstItem RConstItem) {
        this.ConstItem=ConstItem;
        if(ConstItem!=null) ConstItem.setParent(this);
        this.RConstItem=RConstItem;
        if(RConstItem!=null) RConstItem.setParent(this);
    }

    public ConstItem getConstItem() {
        return ConstItem;
    }

    public void setConstItem(ConstItem ConstItem) {
        this.ConstItem=ConstItem;
    }

    public RConstItem getRConstItem() {
        return RConstItem;
    }

    public void setRConstItem(RConstItem RConstItem) {
        this.RConstItem=RConstItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstItem!=null) ConstItem.accept(visitor);
        if(RConstItem!=null) RConstItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstItem!=null) ConstItem.traverseTopDown(visitor);
        if(RConstItem!=null) RConstItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstItem!=null) ConstItem.traverseBottomUp(visitor);
        if(RConstItem!=null) RConstItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RConstItem_rep(\n");

        if(ConstItem!=null)
            buffer.append(ConstItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RConstItem!=null)
            buffer.append(RConstItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RConstItem_rep]");
        return buffer.toString();
    }
}
