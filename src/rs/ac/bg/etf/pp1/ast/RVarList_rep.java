// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class RVarList_rep extends RVarList {

    private VarListList VarListList;

    public RVarList_rep (VarListList VarListList) {
        this.VarListList=VarListList;
        if(VarListList!=null) VarListList.setParent(this);
    }

    public VarListList getVarListList() {
        return VarListList;
    }

    public void setVarListList(VarListList VarListList) {
        this.VarListList=VarListList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarListList!=null) VarListList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarListList!=null) VarListList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarListList!=null) VarListList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RVarList_rep(\n");

        if(VarListList!=null)
            buffer.append(VarListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RVarList_rep]");
        return buffer.toString();
    }
}
