package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;



public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private int mapLoopStart = 0;
	private int endPatch = 0;
	private int gVars = SemAnalyzer.gVars;
	private int i = SemAnalyzer.gVars + 0;
	private int n = SemAnalyzer.gVars + 1;
	private int arrdst = SemAnalyzer.gVars + 2;
	private int arrsrc = SemAnalyzer.gVars + 3;
	
	private boolean forStarted = false;
	private int findAnyLoopStart;
	private int endForAnyLoop;
	private int checkElemLabel;
	private int endForAnyTrue;
	private int breakForAnyTrue;
	
	
	public int getMainPc() {
		return this.mainPc;
	}
	
	@Override
	public void visit(MethodName methodName) {
		methodName.obj.setAdr(Code.pc);
		
		if(methodName.getI1().equalsIgnoreCase("main"))
			mainPc = Code.pc;
		
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	
	
	//STATEMENTS
	
	@Override
	public void visit(Statement_ret2 statement_ret2) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_print1 statement_print1) {
		Code.loadConst(0);
		if(statement_print1.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}
	
	@Override
	public void visit(Statement_print2 statement_print2) {
		Code.loadConst(statement_print2.getN2());
		if(statement_print2.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}
	
	@Override
	public void visit(Statement_read statement_read) {
		if(statement_read.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		Code.store(statement_read.getDesignator().obj);
	}
	
	//FACTOR
	
	@Override
	public void visit(Factor_n factor_n) {
		Code.loadConst(factor_n.getN1());
	}
	
	@Override
	public void visit(Factor_c factor_c) {
		Code.loadConst(factor_c.getC1());
	}
	
	@Override
	public void visit(Factor_b factor_b) {
		Code.loadConst(factor_b.getB1());
	}
	
	@Override
	public void visit(Factor_d factor_d) {
		if(factor_d.getDesignator() instanceof Designator_len) {
			Code.put(Code.arraylength);
		}else if(factor_d.getDesignator() instanceof Designator_map) {
			
		}
		else if(factor_d.getDesignator() instanceof Designator_findAny) {
			
		}
		else {
			Code.load(factor_d.getDesignator().obj);
		}
		
	}
	
	@Override 
	public void visit(Factor_neg factor_neg) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(Factor_newarr factor_newarr) {
		Code.put(Code.newarray);
		if(factor_newarr.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		}else {
			Code.put(1);
		}
	}
	
	@Override
	public void visit(Factor_fparam factor_fparam) {
		String methName = factor_fparam.getDesignator().obj.getName();
		if(!methName.equals("chr") && !methName.equals("ord") && !methName.equals("len")) {
			int offset = factor_fparam.getDesignator().obj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}else {
			if(methName.equals("len"))
				Code.put(Code.arraylength);
		}
		
	}
	
	@Override
	public void visit(Factor_f factor_f) {
		int offset = factor_f.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(ArrayName arrayName) {
		Code.load(arrayName.obj);
	}
	
	//DESIG STATEMENTS
	
	@Override
	public void visit(DesigStatement_exp desigStatement_exp) {
		Code.store(desigStatement_exp.getDesignator().obj);
	}
	
	@Override
	public void visit(DesigStatement_i desigStatement_i) {
		if(desigStatement_i.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(desigStatement_i.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(desigStatement_i.getDesignator().obj);
	}
	
	@Override
	public void visit(DesigStatement_d desigStatement_d) {
		if(desigStatement_d.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(desigStatement_d.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(desigStatement_d.getDesignator().obj);
	}
	
	@Override
	public void visit(DesigStatement_emp desigStatement_emp) {
		
		int offset = desigStatement_emp.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(desigStatement_emp.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesigStatement_a desigStatement_a) {
		String methName = desigStatement_a.getDesignator().obj.getName();
		if(!methName.equals("chr") && !methName.equals("ord") && !methName.equals("len")) {
			int offset = desigStatement_a.getDesignator().obj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}else {
			if(methName.equals("len"))
				Code.put(Code.arraylength);
		}
		
		if(desigStatement_a.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	//MAP
	
	@Override
	public void visit(MapLoop mapLoop) {
		
		//arrsrc
		Code.put(Code.dup);
		Code.put(Code.store);
		Code.put(arrsrc);
		
		//n
		Code.put(Code.arraylength); 
		Code.put(Code.dup);
		Code.put(Code.store);
		Code.put(n); 
		
		//arrdst
		Code.put(Code.newarray);
		Code.loadConst(1);
		Code.put(Code.store);
		Code.put(arrdst); 
		
		//i
		Code.loadConst(0);
		Code.put(Code.store);
		Code.put(i);
		
		// exprStack.empty
		mapLoopStart = Code.pc;
		
		Code.put(Code.load);
		Code.put(i);
		Code.put(Code.load);
		Code.put(n);
		
		Code.putFalseJump(Code.lt, 0);  //goto else branch
		endPatch = Code.pc - 2;
		
		//exprStack.push(arrsrc[i])
		Code.put(Code.load);
		Code.put(arrsrc);
		Code.put(Code.load);
		Code.put(i);
		Code.put(Code.aload);  
		
	}
	
	@Override
	public void visit(MapIdent mapIdent) {
		Code.store(mapIdent.obj);
		
		//preping for storage
		Code.put(Code.load);
		Code.put(arrdst);
		Code.put(Code.load);
		Code.put(i);
	}
	
	@Override
	public void visit(Designator_map designator_map) {
		
		Code.put(Code.astore);
		
		//i++;
		Code.put(Code.load);
		Code.put(i);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store);
		Code.put(i);
	
		
		Code.putJump(mapLoopStart);
		Code.fixup(endPatch);
		
		Code.put(Code.load);
		Code.put(arrdst);
	}
	
	//FINDANY
	
	@Override 
	public void visit(FindAny findAny) {
		
		//adr arrsrc
		Code.put(Code.dup);
		Code.put(Code.store);
		Code.put(arrsrc);
		
		//n = len(arr)
		Code.put(Code.arraylength);
		Code.put(Code.store);
		Code.put(n);
		
		//i
		Code.loadConst(0);
		Code.put(Code.store);
		Code.put(i);
		
		findAnyLoopStart = Code.pc;
		
		Code.put(Code.load);
		Code.put(i);
		Code.put(Code.load);
		Code.put(n);
		
		//if (i >= n)
		Code.putFalseJump(Code.lt, 0); 
		endForAnyLoop = Code.pc - 2;
		
		//Code.putJump(0);
		//checkElemLabel = Code.pc - 2;
		
	}
	
	@Override
	public void visit(FindAnyCheck findAnyCheck) {
		Code.put(Code.load);
		Code.put(arrsrc);
		Code.put(Code.load);
		Code.put(i);
		Code.put(Code.aload);
		
		Code.putFalseJump(Code.ne, 0);
		breakForAnyTrue = Code.pc - 2;
		
		//i++
		Code.put(Code.load);
		Code.put(i);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store);
		Code.put(i);
		
		//elems equal jump to end
		
		
		//elems nequ in loop
		Code.putJump(findAnyLoopStart);
	}
	
	@Override
	public void visit(Designator_findAny designator_findAny) {
		//true comes first
		Code.fixup(breakForAnyTrue);
		Code.loadConst(1);
		Code.putJump(0);
		//skip false
		endForAnyTrue = Code.pc - 2;
		
		//patch false
		Code.fixup(endForAnyLoop);
		Code.loadConst(0);

		//patch true skipping
		Code.fixup(endForAnyTrue);
		
	}
	
	//CONDITION
	
	private Stack<Stack<Integer>> condFalse = new Stack<>();
	private Stack<Stack<Integer>> condTrue = new Stack<>();
	private Stack<Stack<Integer>> forCondFalse = new Stack<>();
	private Stack<Stack<Integer>> forCondTrue = new Stack<>();
	private Stack<Integer> forLoopStart = new Stack<>();
	private Stack<Integer> bodyStart = new Stack<>();
	private Stack<Integer> toStep = new Stack<>();
	private Stack<Stack<Integer>> breakFor = new Stack<>();
	private int forCnt = -1;
	private int skipFalse;
	private int ifEntered = 0;
	
	private int returnRelop(Relop relop) {
		if(relop instanceof Relop_eq) {
			return Code.eq;
		}else if(relop instanceof Relop_neq) {
			return Code.ne;
		}else if(relop instanceof Relop_gt) {
			return Code.gt;
		}else if(relop instanceof Relop_gte) {
			return Code.ge;
		}else if(relop instanceof Relop_ls) {
			return Code.lt;
		}else if(relop instanceof Relop_lte) {
			return Code.le;
		}else {
			return 0;
		}
	}
	
	@Override
	public void visit(CondFact_e condFact_e) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		if(forStarted) {
			forCondFalse.peek().add(Code.pc - 2);
		}else {
			//for ternary expr
			if(ifEntered == 0) condFalse.add(new Stack<>());
			condFalse.peek().add(Code.pc - 2);  //condition false; jump to process next AND term
		}
	}
	
	@Override
	public void visit(CondFact_r condFact_r) {
		Code.putFalseJump(returnRelop(condFact_r.getRelop()), 0);
		if(forStarted) {
			forCondFalse.peek().add(Code.pc - 2);
		}else {
			//for ternary expr
			if(ifEntered == 0) condFalse.add(new Stack<>());
			condFalse.peek().add(Code.pc - 2);  //condition false; jump to process next AND term
		}
	}

	@Override
	public void visit(CondTerm condTerm) {
		
		//whole AND term is true; jump to THEN
		Code.putJump(0);  
		if(forStarted) {
			forCondTrue.peek().add(Code.pc - 2);
		}else {
			
			if(ifEntered == 0) condTrue.add(new Stack<>()); 
			condTrue.peek().add(Code.pc - 2);
		}
		
		//whole AND is false; continue on next AND term
		if(forStarted) {
			while(!forCondFalse.peek().empty()) {
				Code.fixup(forCondFalse.peek().pop());  
			}
		}else {
			while(!condFalse.peek().empty()) {
				Code.fixup(condFalse.peek().pop());  
			}
		}
		
	}
	
	@Override
	public void visit(Condition condition) {
		
		// FALSE JUMP TO ELSE BRANCH
		Code.putJump(0);
		if(forStarted) {
			forCondFalse.peek().add(Code.pc - 2);
		}else {
			condFalse.peek().add(Code.pc - 2);
		}
		
		
		//TRUE jumped to the end of the condition; continue straight to THEN branch
		if(forStarted) {
			while(!forCondTrue.peek().empty()) {
				Code.fixup(forCondTrue.peek().pop());   
			}
		}else {
			while(!condTrue.peek().empty()) {
				Code.fixup(condTrue.peek().pop());   
			}
		}
		
	}
	
	@Override
	public void visit(IfStart ifStart) {
		condTrue.add(new Stack<>());
		condFalse.add(new Stack<>());
		ifEntered++;
	}
	
	@Override 
	public void visit(BranchDecisioner branchDecisioner) {
		//true are skiping else
		Code.putJump(0);
		condTrue.peek().add(Code.pc - 2);
		
		//fix false for else branch
		while(!condFalse.peek().empty()) {
			Code.fixup(condFalse.peek().pop());   
		}
		
	}
	
	@Override
	public void visit(ElseStatement_e elseStatement_e) {
		//fix if it was true
		/*while(!condTrue.empty()) {
			Code.fixup(condTrue.pop());  
		}*/
	}
	
	@Override
	public void visit(Statement_if statement_if) {
		while(!condTrue.peek().empty()) {
			Code.fixup(condTrue.peek().pop());  
		}
		condTrue.pop();
		condFalse.pop();
		ifEntered--;
	}
	
	@Override
	public void visit(ElseStatement_eps elseStatement_eps) {
		//fix if it was true
		/*while(!condTrue.empty()) {
			Code.fixup(condTrue.peek().pop());  
		}*/
	}
	
	//FOR LOOP
	
	@Override
	public void visit(ForNonterm forNonterm) {
		breakFor.add(new Stack<>());
		forCondFalse.add(new Stack<>());
		forCondTrue.add(new Stack<>());
	}
	
	@Override
	public void visit(Statement_for statement_for) {
		
		//for body finished jump to update
		Code.putJump(toStep.peek());
		
		//fix false condition to the end of for
		while(!forCondFalse.peek().empty()) {
			Code.fixup(forCondFalse.peek().pop());   
		}
		forCondTrue.pop();
		forCondFalse.pop();
		
		while(!breakFor.peek().empty()) {
			Code.fixup(breakFor.peek().pop());
		}
		
		breakFor.pop();
		forLoopStart.pop();
		bodyStart.pop();
		toStep.pop();
		
	}
	
	@Override
	public void visit(ForDesigStatementStart_ds forDesigStatementStart_ds) {
		forStarted = true;
		forLoopStart.add(Code.pc);
		
		//fix for loop here to avoid first assigment again
		while(!forCondTrue.peek().empty()) {
			Code.fixup(forCondTrue.peek().pop());  
		}
	}
	
	@Override
	public void visit(ForDesigStatementStart_eps forDesigStatementStart_eps) {
		forStarted = true;
		forLoopStart.add(Code.pc);
		
		//fix for loop here to avoid first assigment again
		while(!forCondTrue.peek().empty()) {
			Code.fixup(forCondTrue.peek().pop());  
		}
	}
	
	
	@Override
	public void visit(ForCondition_cond forCondition_cond) {
		//after condition pass jump to for body
		forStarted = false;
		Code.putJump(0);
		bodyStart.add(Code.pc - 2);
		
		//continue to update
		toStep.add(Code.pc);
	}
	
	@Override
	public void visit(ForCondition_eps forCondition_eps) {
		//after condition pass jump to for body
		forStarted = false;
		Code.putJump(0);
		bodyStart.add(Code.pc - 2);
		
		//continue to update
		toStep.add(Code.pc);
	}
	
	
	@Override
	public void visit(ForDesigStatementEnd_ds forDesigStatementEnd_ds) {
		//updated step jump to next iteration
		Code.putJump(forLoopStart.peek());
		
		//continue to for body
		Code.fixup(bodyStart.peek());
	}
	
	@Override
	public void visit(ForDesigStatementEnd_eps ForDesigStatementEnd_eps) {
		//updated step jump to next iteration
		Code.putJump(forLoopStart.peek());
		
		//continue to for body
		Code.fixup(bodyStart.peek());
	}
	
	@Override
	public void visit(Statement_conti statement_conti) {
		Code.putJump(toStep.peek());
	}
	
	@Override
	public void visit(Statement_break statement_break) {
		Code.putJump(0);
		breakFor.peek().add(Code.pc - 2);
	}
	
	//TERNARY EXPRESSIONS
	
	@Override
	public void visit(ExprDecisioner exprDecisioner) {
		//cond true skip false
		Code.putJump(0);
		skipFalse = Code.pc - 2;
		
		//cond false proceed to false branch
		Code.fixup(condFalse.peek().pop());
		
	}
	
	@Override
	public void visit(Expr_tern expr_tern) {
		Code.fixup(skipFalse);
		if(ifEntered == 0) {
			condFalse.pop();
			condTrue.pop();
		}
	}
	
	//EXPRESSIONS
	
	@Override
	public void visit(RAdd_rep radd_rep) {
		if(radd_rep.getAddop() instanceof Addop_p) {
			Code.put(Code.add);
		}else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(RMul_rep rmul_rep) {
		if(rmul_rep.getMulop() instanceof Mulop_as) {
			Code.put(Code.mul);
		}else if(rmul_rep.getMulop() instanceof Mulop_div){
			Code.put(Code.div);
		}else {
			Code.put(Code.rem);
		}
	}
}
