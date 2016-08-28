package main;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LexicalAnalyzer la = new LexicalAnalyzer();
		System.out.println("-------LEXICAL ANALYZER-------");
		System.out.println("Add spaces, erase comments:");
		la.addSpaces();
		la.eraseComments();
		System.out.println(LexicalAnalyzer.codeB);
		la.splitWithSpaces();
		la.eraseSpaces();
		la.printWords();
		System.out.println("");
		System.out.println("Make changes, print words:");
		la.changeKeyWords();
		la.changeV();
		la.printWords();
		System.out.println();

		System.out.println("-------SYNTAX ANALYZER-------");
		SyntaxAnalyzer sa = new SyntaxAnalyzer();
		System.out.println("Check BRACKETS");
		sa.bracket();
		sa.bracketA();
		System.out.println("Check IF");
		sa.checkReturnIf();
		System.out.println("Check ELSE");
		sa.checkReturnElse();
		// sa.stampaj();

		System.out.println("Check FOR");
		sa.checkReturnFor();
		System.out.println("Check WHILE");
		sa.checkWhile();
		System.out.println("Check SEMICOLONS");
		sa.defineSemicolons();
		
		System.out.println("-------SEMANTIC ANALYZER-------");
		SemanticAnalyzer sem = new SemanticAnalyzer();
		sem.addStringIds().toString();
		sem.addIntIds().toString();
		System.out.println("Check String ONLY");
		sem.checkString();
		System.out.println("Check int only");
		sem.checkInt();
		System.out.println("Check Double");
		sem.checkDouble();
		System.out.println("Check Char");
		sem.checkChar();
		System.out.println("Check ID");
		sem.checkId();
		System.out.println("Check IF");
		//vnjdfv
		sem.checkIf();
//		for(int i = 0; i<sem.idArrayInt.size(); i++){
//			System.out.println(i + " " + sem.idArrayInt.get(i).id + sem.idArrayInt.get(i).idLength);
//		}
		
//		System.out.println(sem.idDouble.toString());
		System.out.println("-------CODE GENERATOR-------");
		CodeGenerator cd = new CodeGenerator(sem.idInt);
		cd.addReservedReg();
//		cd.changeOperation();
		//cd.changeOperation1();
		
//		cd.definePlus();
		cd.finalOutput();
		
	}
}
