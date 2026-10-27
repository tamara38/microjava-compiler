// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class VarListList_rep extends VarListList {

    private VarListList VarListList;
    private VarList VarList;

    public VarListList_rep (VarListList VarListList, VarList VarList) {
        this.VarListList=VarListList;
        if(VarListList!=null) VarListList.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
    }

    public VarListList getVarListList() {
        return VarListList;
    }

    public void setVarListList(VarListList VarListList) {
        this.VarListList=VarListList;
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarListList!=null) VarListList.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarListList!=null) VarListList.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarListList!=null) VarListList.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarListList_rep(\n");

        if(VarListList!=null)
            buffer.append(VarListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarListList_rep]");
        return buffer.toString();
    }
}
