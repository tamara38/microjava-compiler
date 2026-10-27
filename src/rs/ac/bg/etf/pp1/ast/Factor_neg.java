// generated with ast extension for cup
// version 0.8
// 31/7/2026 17:25:30


package rs.ac.bg.etf.pp1.ast;

public class Factor_neg extends Factor {

    private SubFactor SubFactor;

    public Factor_neg (SubFactor SubFactor) {
        this.SubFactor=SubFactor;
        if(SubFactor!=null) SubFactor.setParent(this);
    }

    public SubFactor getSubFactor() {
        return SubFactor;
    }

    public void setSubFactor(SubFactor SubFactor) {
        this.SubFactor=SubFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SubFactor!=null) SubFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SubFactor!=null) SubFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SubFactor!=null) SubFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_neg(\n");

        if(SubFactor!=null)
            buffer.append(SubFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_neg]");
        return buffer.toString();
    }
}
