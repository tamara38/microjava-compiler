// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class VarList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private VarItem VarItem;
    private RVarItem RVarItem;

    public VarList (Type Type, VarItem VarItem, RVarItem RVarItem) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarItem=VarItem;
        if(VarItem!=null) VarItem.setParent(this);
        this.RVarItem=RVarItem;
        if(RVarItem!=null) RVarItem.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarItem getVarItem() {
        return VarItem;
    }

    public void setVarItem(VarItem VarItem) {
        this.VarItem=VarItem;
    }

    public RVarItem getRVarItem() {
        return RVarItem;
    }

    public void setRVarItem(RVarItem RVarItem) {
        this.RVarItem=RVarItem;
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
        if(Type!=null) Type.accept(visitor);
        if(VarItem!=null) VarItem.accept(visitor);
        if(RVarItem!=null) RVarItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarItem!=null) VarItem.traverseTopDown(visitor);
        if(RVarItem!=null) RVarItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarItem!=null) VarItem.traverseBottomUp(visitor);
        if(RVarItem!=null) RVarItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarItem!=null)
            buffer.append(VarItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RVarItem!=null)
            buffer.append(RVarItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarList]");
        return buffer.toString();
    }
}
