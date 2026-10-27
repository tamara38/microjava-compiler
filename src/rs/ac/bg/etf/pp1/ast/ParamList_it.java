// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class ParamList_it extends ParamList {

    private ParamItem ParamItem;

    public ParamList_it (ParamItem ParamItem) {
        this.ParamItem=ParamItem;
        if(ParamItem!=null) ParamItem.setParent(this);
    }

    public ParamItem getParamItem() {
        return ParamItem;
    }

    public void setParamItem(ParamItem ParamItem) {
        this.ParamItem=ParamItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParamItem!=null) ParamItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParamItem!=null) ParamItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParamItem!=null) ParamItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParamList_it(\n");

        if(ParamItem!=null)
            buffer.append(ParamItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParamList_it]");
        return buffer.toString();
    }
}
