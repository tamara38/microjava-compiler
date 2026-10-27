package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.concepts.*;

public class ActParamVisitor extends VisitorAdaptor {

	List<Struct> finalList;
	
	
	Stack<List<Struct>> actParams = new Stack<>();
	
	@Override
	public void visit(ActPar actPar) {
		actParams.peek().add(actPar.getExpr().struct);
	}
	
	@Override
	public void visit(ActParBegin actParBegin) {
		actParams.add(new ArrayList<>());
	}
	
	@Override
	public void visit(ActualParams actualParams) {
		finalList = actParams.pop();
	}
	
}
