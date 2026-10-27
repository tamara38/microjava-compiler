package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {

    public int classCount = 1;      
    public int methodCount = 0;
    public int globalVarCount = 0;
    public int globalConstCount = 0;
    public int globalArrCount = 0;
    public int localVarInMainCount = 0;
    public int statementsInMainCount = 0;
    public int functionCallsInMainCount = 0;

    private boolean inMain = false;
    private boolean inAnyMethod = false;

    @Override
    public void visit(MethodName methodName) {
        inAnyMethod = true;
        methodCount++;
        if (methodName.getI1().equalsIgnoreCase("main")) {
            inMain = true;
        }
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        inMain = false;
        inAnyMethod = false;
    }

    @Override
    public void visit(ConstItem constItem) {
        if (!inAnyMethod) {
            globalConstCount++;
        }
    }

    @Override
    public void visit(VarItem_var varItem_var) {
        if (inMain) {
            localVarInMainCount++;
        } else if (!inAnyMethod) {
            globalVarCount++;
        }
    }

    @Override
    public void visit(VarItem_arr varItem_arr) {
        if (inMain) {
            localVarInMainCount++;
        } else if (!inAnyMethod) {
            globalArrCount++;
        }
    }

   
    @Override
    public void visit(Statement_if s)       { if (inMain) statementsInMainCount++; }

    @Override
    public void visit(Statement_break s)    { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(Statement_conti s)    { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(Statement_ret1 s)     { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(Statement_ret2 s)     { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(Statement_read s)     { if (inMain) {statementsInMainCount++; functionCallsInMainCount++;} }
    @Override
    public void visit(Statement_print1 s)   { if (inMain) {statementsInMainCount++; functionCallsInMainCount++;} }
    @Override
    public void visit(Statement_print2 s)   { if (inMain) {statementsInMainCount++; functionCallsInMainCount++;} }
    @Override
    public void visit(Statement_for s)      { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(Statement_stat s)     { if (inMain) statementsInMainCount++; } // blok { ... }

   
    @Override
    public void visit(DesigStatement_exp s) { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(DesigStatement_i s)   { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(DesigStatement_d s)   { if (inMain) statementsInMainCount++; }
    @Override
    public void visit(DesigStatement_emp s) {
        if (inMain) { statementsInMainCount++; functionCallsInMainCount++; }
    }
    @Override
    public void visit(DesigStatement_a s) {
        if (inMain) { statementsInMainCount++; functionCallsInMainCount++; }
    }
    
    @Override
    public void visit(Designator_map d) {
        if (inMain) functionCallsInMainCount++;
    }
    
    @Override
    public void visit(Factor_f f)      { if (inMain) functionCallsInMainCount++; }
    @Override
    public void visit(Factor_fparam f) { if (inMain) functionCallsInMainCount++; }
	
	
}
