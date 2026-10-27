package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemAnalyzer extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj currProg;
	private Struct currType;
	private int constant;
	
	private Struct boolType = Tab.find("bool").getType();
	private Struct constType;
	private Obj currMethod = null;
	private boolean isVoidMethod = false;
	private Obj mainMethod = null;
	private boolean returnHappened = false;
	private Struct returnType;
	private int forCnt = 0;
	int nVars;
	public static int gVars;
	
	List<Obj> fparams = new ArrayList<>();
	List<Obj> locals = new ArrayList<>();

	/* LOG MESSAGES */
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		//log.error(msg.toString());
		System.err.println(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		//log.info(msg.toString());
		System.out.println(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	@Override
	public void visit(ProgramName programName) {
		currProg = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(currProg);
		Tab.closeScope();
		currProg = null;
		
		if(mainMethod == null || mainMethod.getLevel() > 0) {
			report_error("Program nema odgovarajucu main metodu", program);
		}
	}
	
	@Override
	public void visit(GlobEnd globEnd) {
		gVars = Tab.currentScope().getnVars();
	}
	
	/*CONST DECLARATIONS*/
	
	@Override
	public void visit(ConstItem constItem) {
		Obj conObj = Tab.find(constItem.getI1());
		if(conObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: " + constItem.getI1(), constItem);
		}else {
			if(constType.assignableTo(currType)) {
				conObj = Tab.insert(Obj.Con, constItem.getI1(), currType);
				conObj.setAdr(constant);
			}else {
				report_error("Nekompatibilna dodela vrednosti konstanti: " + constItem.getI1(), constItem);
			}
			
		}
	}
	
	
	@Override
	public void visit(ConstVal_num constVal_num) {
		constant = constVal_num.getN1();
		constType = Tab.intType;
	}
	
	@Override
	public void visit(ConstVal_char constVal_char) {
		constant = constVal_char.getC1();
		constType = Tab.charType;
	}
	
	@Override
	public void visit(ConstVal_bool constVal_bool) {
		constant = constVal_bool.getB1();
		constType = boolType;
	}
	
	/*VAR DECLARATIONS*/
	
	@Override
	public void visit(VarItem_var varItem_var) {
		Obj varObj = null;
		
		if(currMethod == null) {
			varObj = Tab.find(varItem_var.getI1());
		}else {
			varObj = Tab.currentScope.findSymbol(varItem_var.getI1());
		}
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varItem_var.getI1(), currType);
			locals.add(varObj);
		}else {
			report_error("Dvostruka definicija promenljive: " + varItem_var.getI1(), varItem_var);
		}
	}
	
	@Override
	public void visit(VarItem_arr varItem_arr) {
		Obj varObj = null;
		
		if(currMethod == null) {
			varObj = Tab.find(varItem_arr.getI1());
		}else {
			varObj = Tab.currentScope.findSymbol(varItem_arr.getI1());
		}
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varItem_arr.getI1(), new Struct(Struct.Array, currType));
			locals.add(varObj);
		}else {
			report_error("Dvostruka definicija promenljive: " + varItem_arr.getI1(), varItem_arr);
		}
	}
	
	/*METHOD DECLARATIONS*/
	
	@Override
	public void visit(MethodName methodName) {
		if(isVoidMethod) {
			currMethod  = Tab.insert(Obj.Meth, methodName.getI1(), Tab.noType);
			if(methodName.getI1().equalsIgnoreCase("main")) {
				mainMethod = currMethod;
			}
			isVoidMethod = false;
		}else {
			currMethod  = Tab.insert(Obj.Meth, methodName.getI1(), currType);
		}
		methodName.obj = currMethod;
		Tab.openScope();
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		
		if(!returnHappened && !currMethod.getType().equals(Tab.noType)) {
			report_error("Metoda nema odgovarajuci return iskaz: " + currMethod.getName(), methodDecl);
		}else if(returnHappened && !currMethod.getType().equals(returnType)) {
			report_error("Neodgovarajuci tip return iskaza metode: " + currMethod.getName(), methodDecl);
		}
		
		Tab.chainLocalSymbols(currMethod);
		Tab.closeScope();
		currMethod = null;
		returnHappened = false;
		returnType = Tab.noType;
		fparams.clear();
		locals.clear();
	}
	
	@Override
	public void visit(MethodType_t methodType_t) {
		isVoidMethod  = false;
	}
	
	@Override
	public void visit(MethodType_v methodType_v) {
		isVoidMethod  = true;
	}
	
	/*PARAM DECLARATION */
	
	@Override
	public void visit(ParamItem_var paramItem_var) {
		Obj varObj = null;
		
		if(currMethod == null) {
			report_error("Semanticka greska [ParamItem_var]", paramItem_var);
		}else {
			varObj = Tab.currentScope.findSymbol(paramItem_var.getI2());
		}
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, paramItem_var.getI2(), currType);
			varObj.setFpPos(1);
			currMethod.setLevel(currMethod.getLevel() + 1);
			fparams.add(varObj);
		}else {
			report_error("Dvostruka definicija promenljive: " + paramItem_var.getI2(), paramItem_var);
		}
	}
	
	@Override
	public void visit(ParamItem_arr paramItem_arr) {
		Obj varObj = null;
		
		if(currMethod == null) {
			report_error("Semanticka greska [ParamItem_arr]", paramItem_arr);
		}else {
			varObj = Tab.currentScope.findSymbol(paramItem_arr.getI2());
		}
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, paramItem_arr.getI2(), new Struct(Struct.Array, currType));
			varObj.setFpPos(1);
			currMethod.setLevel(currMethod.getLevel() + 1);
			fparams.add(varObj);
		}else {
			report_error("Dvostruka definicija promenljive: " + paramItem_arr.getI2(), paramItem_arr);
		}
	}
	
	@Override
	public void visit(Type type) {
		Obj objType = Tab.find(type.getI1());
		
		if(objType == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			currType = Tab.noType;
		}
		else if(objType.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + type.getI1(), type);
			currType = Tab.noType;
		}
		else {
			type.struct = currType = objType.getType();
		}
	}
	
	/*STATEMENTS */
	
	@Override
	public void visit(Statement_read statement_read) {
		int kind = statement_read.getDesignator().obj.getKind();
		Struct type = statement_read.getDesignator().obj.getType();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("READ operacija nad neadekvatnom promenljivom: " + statement_read.getDesignator().obj.getName(), statement_read);
		}else if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("READ operacija nad ne int/char/bool vrednosti: " + statement_read.getDesignator().obj.getName(), statement_read);
		}
	}
	
	@Override
	public void visit(Statement_print1 statement_print1) {
		Struct type = statement_print1.getExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("PRINT nad ne int/char/bool izrazom ", statement_print1);
		}
	}
	
	@Override
	public void visit(Statement_print2 statement_print2) {
		Struct type = statement_print2.getExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("PRINT nad ne int/char/bool izrazom ", statement_print2);
		}
	}
	
	@Override
	public void visit(Statement_ret2 statement_ret2) {
		returnType = Tab.noType;
		returnHappened = true;
	}
	
	@Override
	public void visit(Statement_ret1 statement_ret1) {
		returnType = statement_ret1.getExpr().struct;
		returnHappened = true;
	}
	

	@Override
	public void visit(Statement_for statement_for) {
		if(statement_for.getForCondition() instanceof ForCondition_cond && !statement_for.getForCondition().struct.equals(boolType)) {
			report_error("Condition izraz je ne bool tipa [Statement_for]", statement_for);
		}
		forCnt--;
	}
	
	@Override
	public void visit(ForNonterm forNonterm) {
		forCnt++;
	}
	
	@Override
	public void visit(Statement_break statement_break) {
		if(forCnt == 0) {
			report_error("Break iskaz van petlje", statement_break);
		}
	}
	
	@Override
	public void visit(Statement_conti statement_conti) {
		if(forCnt == 0) {
			report_error("Continue iskaz van petlje", statement_conti);
		}
	}
	
	/*CONDITIONS */
	
	@Override
	public void visit(DesigStatement_exp desigStatement_exp) {
		int type = desigStatement_exp.getDesignator().obj.getKind();
		if(type != Obj.Var && type != Obj.Elem) {
			report_error("Dodela u neadekvatnu promenljivu: " + desigStatement_exp.getDesignator().obj.getName(), desigStatement_exp);
		}else if(!desigStatement_exp.getExpr().struct.assignableTo(desigStatement_exp.getDesignator().obj.getType())) {
			report_error("Neadekvatna dodela vrednosti u promenljivu: " + desigStatement_exp.getDesignator().obj.getName(), desigStatement_exp);
		}
	}

	
	@Override
	public void visit(DesigStatement_i desigStatement_i) {
		int type = desigStatement_i.getDesignator().obj.getKind();
		if(type != Obj.Var && type != Obj.Elem) {
			report_error("Dodela u neadekvatnu promenljivu: " + desigStatement_i.getDesignator().obj.getName(), desigStatement_i);
		}else if(desigStatement_i.getDesignator().obj.getType() != Tab.intType) {
			report_error("Opeacija '++' nad neadekvatnim tipom promenljive: " + desigStatement_i.getDesignator().obj.getName(), desigStatement_i);
		}
	}
	
	@Override
	public void visit(DesigStatement_d desigStatement_d) {
		int type = desigStatement_d.getDesignator().obj.getKind();
		if(type != Obj.Var && type != Obj.Elem) {
			report_error("Dodela u neadekvatnu promenljivu: " + desigStatement_d.getDesignator().obj.getName(), desigStatement_d);
		}else if(desigStatement_d.getDesignator().obj.getType() != Tab.intType) {
			report_error("Opeacija '--' nad neadekvatnim tipom promenljive: " + desigStatement_d.getDesignator().obj.getName(), desigStatement_d);
		}
	}
	
	@Override
	public void visit(DesigStatement_emp desigStatement_emp) {
		if(desigStatement_emp.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + desigStatement_emp.getDesignator().obj.getName(), desigStatement_emp);
		}else if(desigStatement_emp.getDesignator().obj.getLevel() > 0) {
			report_error("Metod ocekuje parametre: " + desigStatement_emp.getDesignator().obj.getName(), desigStatement_emp);
		}
	}
	
	@Override
	public void visit(DesigStatement_a desigStatement_a) {
		if(desigStatement_a.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + desigStatement_a.getDesignator().obj.getName(), desigStatement_a);
		}else if(desigStatement_a.getDesignator().obj.getLevel() == 0) {
			report_error("Metod ne prima formalne parametre: " + desigStatement_a.getDesignator().obj.getName(), desigStatement_a);
		}else {
			List<Struct> fpList = new ArrayList<>();
			for(Obj param : desigStatement_a.getDesignator().obj.getLocalSymbols()) {
				if(param.getKind() == Obj.Var && param.getLevel() == 1 && param.getFpPos() == 1) { 
					fpList.add(param.getType());
				}
			}
			ActParamVisitor ac = new ActParamVisitor();
			desigStatement_a.getActualParams().traverseBottomUp(ac);
			List<Struct> acList = ac.finalList;
			
			
			try {
				if(desigStatement_a.getDesignator().obj.getName().equalsIgnoreCase("ord")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(!acList.get(0).equals(Tab.charType)) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else if(desigStatement_a.getDesignator().obj.getName().equalsIgnoreCase("chr")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(!acList.get(0).equals(Tab.intType)) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else if(desigStatement_a.getDesignator().obj.getName().equalsIgnoreCase("len")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(acList.get(0).getKind() != Struct.Array) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else {
					if(fpList.size() != acList.size())
						throw new Exception("Neslaganje broja parametara");
					for(int i = 0; i < fpList.size(); i++) {
						if(!acList.get(i).assignableTo(fpList.get(i))) {
							throw new Exception("Neslaganje tipova parametara");
						}
					}
				}
				
			}catch(Exception e){
				report_error("[" + e.getMessage() + "] pri pozivu metode: " + desigStatement_a.getDesignator().obj.getName(), desigStatement_a);
			}
 		}
	}
	
	
	
	//SubFactor
	@Override 
	public void visit(Factor_n factor_n) {
		factor_n.struct = Tab.intType;
	}
	
	@Override 
	public void visit(Factor_c factor_c) {
		factor_c.struct = Tab.charType;
	}
	
	@Override 
	public void visit(Factor_b factor_b) {
		factor_b.struct = boolType;
	}
	
	@Override
	public void visit(Factor_d factor_d) {
		if(factor_d.getDesignator() instanceof Designator_map) {
			if(factor_d.getDesignator().obj == Tab.noObj) {
				factor_d.struct = Tab.noType;
			}else {
				factor_d.struct = factor_d.getDesignator().obj.getType();
			}
		}else {
			factor_d.struct = factor_d.getDesignator().obj.getType();
		}
	}
	
	@Override
	public void visit(Factor_pos factor_pos) {
		factor_pos.struct = factor_pos.getSubFactor().struct;
	}
	
	@Override
	public void visit(Factor_neg factor_neg) {
		if(!factor_neg.getSubFactor().struct.equals(Tab.intType)) {
			report_error("Negacija neodgovarajuceg tipa podatka: " + factor_neg.getSubFactor().struct, factor_neg);
			factor_neg.struct = Tab.noType;
		}else {
			factor_neg.struct = factor_neg.getSubFactor().struct;
		}
	}
	
	@Override
	public void visit(Factor_newarr factor_newarr) {
		if(!factor_newarr.getExpr().struct.equals(Tab.intType)) {
			report_error("Inicijalizacija niza sa ne int vrednoscu: [Factor_newarr]" , factor_newarr);
			factor_newarr.struct = Tab.noType;
		}else {
			factor_newarr.struct = new Struct(Struct.Array, currType);
		}
	}
	
	@Override
	public void visit(Factor_exp factor_exp) {
		factor_exp.struct = factor_exp.getExpr().struct;
	}
	
	@Override
	public void visit(Factor_f factor_f) {
		Obj methObj = Tab.find(factor_f.getDesignator().obj.getName());
		if(methObj.getKind() != Obj.Meth) {
			report_error("Nepostojeca funkcija: " + methObj.getName(), factor_f);
			factor_f.struct = Tab.noType;
		}else if(methObj.getLevel() > 0) {
			report_error("Metod ocekuje parametre: " + methObj.getName(), factor_f);
			factor_f.struct = Tab.noType;
		}else {
			factor_f.struct = methObj.getType();
			report_info("Poziv funkcije " + methObj.getName() + " [Kind: " + methObj.getKind() + "]", factor_f);
		}
	}
	
	@Override
	public void visit(Factor_fparam factor_fparam) {
		Obj methObj = Tab.find(factor_fparam.getDesignator().obj.getName());
		if(methObj.getKind() != Obj.Meth) {
			report_error("Nepostojeca funkcija: " + methObj.getName(), factor_fparam);
			factor_fparam.struct = Tab.noType;
		}else if(factor_fparam.getDesignator().obj.getLevel() == 0) {
			report_error("Metod ne prima formalne parametre: " + factor_fparam.getDesignator().obj.getName(), factor_fparam);
			factor_fparam.struct = Tab.noType;
		}else {
			factor_fparam.struct = methObj.getType();
			List<Struct> fpList = new ArrayList<>();
			for(Obj param : factor_fparam.getDesignator().obj.getLocalSymbols()) {
				if(param.getKind() == Obj.Var && param.getLevel() == 1 && param.getFpPos() == 1) { 
					fpList.add(param.getType());
				}
			}
			ActParamVisitor ac = new ActParamVisitor();
			factor_fparam.getActualParams().traverseBottomUp(ac);
			List<Struct> acList = ac.finalList;
			
			try {
				if(factor_fparam.getDesignator().obj.getName().equals("ord")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(!acList.get(0).equals(Tab.charType)) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else if(factor_fparam.getDesignator().obj.getName().equalsIgnoreCase("chr")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(!acList.get(0).equals(Tab.intType)) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else if(factor_fparam.getDesignator().obj.getName().equalsIgnoreCase("len")) {
					if(acList.size() != 1) {
						throw new Exception("Neslaganje broja parametara");
					}
					else if(acList.get(0).getKind() != Struct.Array) {
						throw new Exception("Neslaganje tipova parametara");
					}
				}else {
					if(fpList.size() != acList.size())
						throw new Exception("Neslaganje broja parametara");
					for(int i = 0; i < fpList.size(); i++) {
						if(!acList.get(i).assignableTo(fpList.get(i))) {
							throw new Exception("Neslaganje tipova parametara");
						}
					}

				}
				report_info("Poziv funkcije " + methObj.getName() + " [Kind: " + methObj.getKind() + "]", factor_fparam);
			}catch(Exception e){
				report_error("[" + e.getMessage() + "] pri pozivu metode: " + factor_fparam.getDesignator().obj.getName(), factor_fparam);
			}
		}
	}
	
	//Designator
	@Override
	public void visit(Designator_var designator_var) {
		Obj var = Tab.find(designator_var.getI1());
		if(var == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivoj: " + designator_var.getI1(), designator_var);
			designator_var.obj = Tab.noObj;
		}
		else if(var.getKind() != Obj.Var && var.getKind() != Obj.Con && var.getKind() != Obj.Meth) {
			report_error("Neadekvatna promenljiva: " + designator_var.getI1(), designator_var);
			designator_var.obj = Tab.noObj;
		}
		else {
			designator_var.obj = var;
			
			int kind = var.getKind();
			if(kind == Obj.Con) {
				report_info("Pristup simbolickoj konstanti " + var.getName() + " [Kind: " + kind + "]", designator_var);
			}else if(kind == Obj.Var && var.getLevel() == 0) {
				report_info("Pristup globalnoj promenljivoj " + var.getName() + " [Kind: " + kind + "]", designator_var);
			}else if(kind == Obj.Var && var.getFpPos() == 1 && fparams.contains(var)) {
				report_info("Pristup formalnom parametru " + var.getName() + " [Kind: " + kind + "]", designator_var);
			}else if(kind == Obj.Var && var.getFpPos() == 0 && locals.contains(var)) {
				report_info("Pristup lokalnoj promenljivoj " + var.getName() + " [Kind: " + kind + "]", designator_var);
			}
		}
	}
	
	
	
	@Override
	public void visit(ArrayName arrayName) {
		Obj var = Tab.find(arrayName.getI1());
		if(var == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivoj: " + arrayName.getI1(), arrayName);
			arrayName.obj = Tab.noObj;
		}
		else if(var.getKind() != Obj.Var || var.getType().getKind() != Struct.Array) {
			report_error("Neadekvatna promenljiva niza: " + arrayName.getI1(), arrayName);
			arrayName.obj = Tab.noObj;
		}
		else {
			arrayName.obj = var;
		}
	}
	
	@Override
	public void visit(Designator_el designator_el) {
		Obj arrObj = designator_el.getArrayName().obj;
		if(arrObj == Tab.noObj) {
			designator_el.obj = Tab.noObj;
		}else if(!designator_el.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje sa ne int vrednoscu [Designator_el]", designator_el);
			designator_el.obj = Tab.noObj;
		}else {
			designator_el.obj = new Obj(Obj.Elem, arrObj.getName() + "[$]", arrObj.getType().getElemType());
			report_info("Pristup elementu niza " + arrObj.getName() + " [Kind: " + arrObj.getKind() + "]", designator_el);
		}
	}
	
	@Override
	public void visit(Designator_len designator_len) {
		Obj arrObj = designator_len.getArrayName().obj;
		if(arrObj == Tab.noObj) {
			designator_len.obj = Tab.noObj;
		}else {
			designator_len.obj = new Obj(Obj.Var, arrObj.getName() + ".length", Tab.intType);
		}
	}
	
	@Override
	public void visit(Designator_findAny designator_findAny) {
		Obj arrObj = designator_findAny.getArrayName().obj;
		if(arrObj == Tab.noObj) {
			designator_findAny.obj = Tab.noObj;
		}else {
			designator_findAny.obj = new Obj(Obj.Var, arrObj.getName() + ".findany", boolType);
		}
	}
	
	@Override
	public void visit(Designator_map designator_map) {
		Obj arrObj = designator_map.getArrayName().obj;
		Obj ident = designator_map.getMapIdent().obj;
		Struct expr = designator_map.getExpr().struct;
		
		if(arrObj == Tab.noObj || expr == Tab.noType) {
			designator_map.obj = Tab.noObj;
		}
		else if(ident == Tab.noObj || ident.getKind() != Obj.Var) {
			report_error("Nedefinisana promenljiva u funkciji MAP: " + ident.getName(), designator_map);
			designator_map.obj = Tab.noObj;
		}else if(ident.getType() != arrObj.getType().getElemType()) {
			report_error("Nekompatibilni tipovi u funkciji MAP ", designator_map);
			designator_map.obj = Tab.noObj;
		}
		else {
			designator_map.obj = new Obj(Obj.Var, arrObj.getName() + "[map]", new Struct(Struct.Array, designator_map.getExpr().struct));
		}
		
	}
	
	@Override
	public void visit(MapIdent mapIdent) {
		mapIdent.obj = Tab.find(mapIdent.getI1());
	}
	
	//EXPRESIONS
	
	@Override
	public void visit(RMul_rep rmul_rep) {
		Struct factor = rmul_rep.getFactor().struct;
		Struct rmul = rmul_rep.getRMul().struct;
		if(!factor.equals(Tab.intType) || !rmul.equals(Tab.intType)) {
			report_error("Mulop operacija ne int vrednostima", rmul_rep);
			rmul_rep.struct = Tab.noType;
		}else {
			rmul_rep.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(RMul_fac rmul_fac) {
		rmul_fac.struct = rmul_fac.getFactor().struct;
	}
	
	@Override
	public void visit(Term term) {
		term.struct = term.getRMul().struct;
	}
	
	@Override
	public void visit(RAdd_rep radd_rep) {
		Struct term = radd_rep.getTerm().struct;
		Struct radd = radd_rep.getRAdd().struct;
		if(!term.equals(Tab.intType) || !radd.equals(Tab.intType)) {
			report_error("Addop operacija ne int vrednostima", radd_rep);
			radd_rep.struct = Tab.noType;
		}else {
			radd_rep.struct = Tab.intType;
		}
		
	}
	
	@Override
	public void visit(RAdd_term radd_term) {
		radd_term.struct = radd_term.getTerm().struct;
	}
	
	
	@Override
	public void visit(Expr_aritm expr_aritm) {
		expr_aritm.struct = expr_aritm.getRAdd().struct;
	}
	
	@Override
	public void visit(Expr_tern expr_tern) {
		if(!expr_tern.getExpr().struct.compatibleWith(expr_tern.getExpr1().struct)) {
			report_error("Nekompatibilni tipovi izraza [Expr_tern]", expr_tern);
			expr_tern.struct = Tab.noType;
		}else if(!expr_tern.getCondition().struct.equals(boolType)) {
			report_error("Conditon izraz nije bool tipa [Expr_tern]", expr_tern);
			expr_tern.struct = Tab.noType;
		}else {
			expr_tern.struct = expr_tern.getExpr().struct;
		}
	}
	
	/*CONDTERMS */
	
	@Override
	public void visit(CondFact_e condFact_e) {
		if(!condFact_e.getRAdd().struct.equals(boolType)) {
			report_error("Logicki operand nije bool tipa", condFact_e);
			condFact_e.struct = Tab.noType;
		}else {
			condFact_e.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFact_r condFact_r) {
		Struct left = condFact_r.getRAdd().struct;
		Struct right = condFact_r.getRAdd1().struct;
		boolean equ = condFact_r.getRelop() instanceof Relop_eq;
		boolean neq = condFact_r.getRelop() instanceof Relop_neq;
		if(!left.compatibleWith(right)) {
			report_error("Relop nad nekompatibilnim tipovima [condFact_r]", condFact_r);
			condFact_r.struct = Tab.noType;
		}else if(left.getKind() == Struct.Array && right.getKind() == Struct.Array && !equ && !neq) {
			report_error("Neadekvatan operand nad array tipovima [condFact_r]", condFact_r);
			condFact_r.struct = Tab.noType;
		}
		else {
			condFact_r.struct = boolType;
		}
	}
	
	@Override
	public void visit(RCondFact_eps rcondFact_eps) {
		rcondFact_eps.struct = Tab.noType;
	}
	
	@Override
	public void visit(RCondFact_rep rcondFact_rep) {
		Struct condfact = rcondFact_rep.getCondFact().struct;
		if(!condfact.equals(boolType)) {
			report_error("AND operacija nad vrednostima ne bool tipa", rcondFact_rep);
			rcondFact_rep.struct = Tab.noType;
		}else {
			rcondFact_rep.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		Struct left = condTerm.getCondFact().struct;
		Struct right = condTerm.getRCondFact().struct;
		if(condTerm.getRCondFact() instanceof RCondFact_rep) {
			if(!left.equals(boolType) || !right.equals(boolType)) {
				report_error("AND operacija nad vrednostima ne bool tipa", condTerm);
				condTerm.struct = Tab.noType;
			}else {
				condTerm.struct = boolType;
			}
		}else {
			if(!left.equals(boolType)) {
				condTerm.struct = Tab.noType;
			}else {
				condTerm.struct = boolType;
			}	
		}
		
	}
	
	@Override
	public void visit(RCondTerm_rep rcondTerm_rep) {
		Struct condterm = rcondTerm_rep.getCondTerm().struct;
		if(!condterm.equals(boolType)) {
			report_error("OR operacija nad vrednostima ne bool tipa", rcondTerm_rep);
			rcondTerm_rep.struct = Tab.noType;
		}else {
			rcondTerm_rep.struct = boolType;
		}
	}
	
	@Override
	public void visit(RCondTerm_eps rcondTerm_eps) {
		rcondTerm_eps.struct = Tab.noType;
	}
	
	@Override
	public void visit(Condition condition) {
		Struct left = condition.getCondTerm().struct;
		Struct right = condition.getRCondTerm().struct;
		if(condition.getRCondTerm() instanceof RCondTerm_rep) {
			if(!left.equals(boolType) || !right.equals(boolType)) {
				report_error("OR operacija nad vrednostima ne bool tipa", condition);
				condition.struct = Tab.noType;
			}else {
				condition.struct = boolType;
			}
		}else {
			if(!left.equals(boolType)) {
				condition.struct = Tab.noType;
			}else {
				condition.struct = boolType;
			}	
		}
	}
	
	@Override
	public void visit(ForCondition_cond forCondition_cond) {
		forCondition_cond.struct = forCondition_cond.getCondition().struct;
	}
	
}
