package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		if (args.length < 2) {
            System.err.println("Potrebno je proslediti putanju do ulaznog .mj fajla (arg[0]) i izlaznog .obj fajla (arg[1]).");
            return;
        }
		
		Logger log = Logger.getLogger(Compiler.class);
		
		File sourceCode = new File(args[0]);
        
		
		Reader br = null;
		try {
			//File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			//AST
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse(); 
	        
	        Program prog = (Program)(s.value); 
			
	        // ISPIS AST
			//log.info(prog.toString(""));
			//log.info("================SEMANTICKA OBRADA===================");
	        System.out.println(prog.toString(""));
	        System.out.println("================SEMANTICKA OBRADA===================");
	        
			//inicijalizacija tabele simbola
			Tab.init();
			
			Struct boolType = new Struct(Struct.Bool);
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);
			
			//semanticka analiza
			SemAnalyzer sa = new SemAnalyzer();
			prog.traverseBottomUp(sa);
		
			
			// ispis prepoznatih programskih konstrukcija
			System.out.println("==================SINTAKSNA ANALIZA====================");
			RuleVisitor rv = new RuleVisitor();
			prog.traverseBottomUp(rv); 
	      
			System.out.println(rv.classCount + " classes");
			System.out.println(rv.methodCount + " methods in the program");
			System.out.println(rv.globalVarCount + " global variables");
			System.out.println(rv.globalConstCount + " global constants");
			System.out.println(rv.globalArrCount + " global arrays");
			System.out.println(rv.localVarInMainCount + " local variables in main");
			System.out.println(rv.statementsInMainCount + " statements in main");
			System.out.println(rv.functionCallsInMainCount + " function calls in main");
			
			//ispis tabele simbola
			Tab.dump();
			
			if(!p.errorDetected && sa.passed()) {
				File objFile = new File(args[1]);
				//File objFile = new File("test/program.obj");
				if(objFile.exists()) objFile.delete();

				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = sa.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				
				System.out.println("Parsiranje uspesno zavrseno!");
				//log.info("Parsiranje uspesno zavrseno!");
			}else {
				System.err.println("Parsiranje uspesno zavrseno!");
				//log.info("Parsiranje NIJE uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
