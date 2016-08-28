package main;
import java.util.LinkedList;

public class LexicalAnalyzer {
	
	public static String code = "public int a(){int sr =1; sr=7; char c = 'a'; int s = sr; int b; b = 55 + 6 + 6 + 2 + 3;   if(5 != 6){  s = 6; sr = 26 + 4 ;} int cdb;}";
	public static String code1 = 
			"public int match (int a) { " +
					"double k = 0.0; " +
					"int [] arr = new int [10]; " +
					"arr[9] = 5; " +
					"int [] bal= new int[5]; " +
					"bal[2] = 5; " +
					"String aa1=\"Staka  i  Genti :P\";  " +
					"String s = \"milos\"; " +
					"System.out.println (a); " +
					"for (int i = 0; i < 10; i++) {/*provjera komentara*/  " +
						"if(s<aa1){  String m1 =\"BILJA\" ;" +
							"int d;  " +
							"int kr = aa1; " +
							"return i;" +
						"}else{" +
							"int p = 0;" +
							"return a + 1 ;" +
						"} " +
					"} " +
					"return a; " +
				"} ";
	public static StringBuffer codeB = new StringBuffer(code);
	public static LinkedList<String> words = new LinkedList<String>();
	public String[] keywords = { "String", "abstract", "continue", "for",
			"new", "switch", "assert", "default", "goto", "package",
			"synchronized", "boolean", "do", "if", "private", "this", "break",
			"double", "implements", "protected", "throw", "byte", "else",
			"import", "public", "throws", "case", "enum", "instanceof",
			"return", "transient", "catch", "extends", "int", "short", "try",
			"char", "final", "interface", "static", "void", "class", "finally",
			"long", "strictfp", "volatile", "const", "float", "native",
			"super", "while", "null", "System", "out", "println" };
	public String[] operators = { "+", "-", "++", "<", ">", "=", "==", "*",
			"/", "%", "!=", "<=", ">=", "&&", "||", "--" };
	public String[] identifiers = { "(", ")", "{", "}", "[", "]", ";", ",", "." };

	public void eraseComments() {
		while (codeB.indexOf("/*") != -1) {
			codeB.delete(codeB.indexOf("/*"), codeB.indexOf("*/") + 2);
		}
		for (int i = codeB.indexOf("//"); i < codeB.length(); i++) {
			// if (codeB.charAt(i) == '\n') {
			// codeB.delete(codeB.indexOf("//"), i);
			// }
		}
	}
	
	
	
	public void splitWithSpaces() {
		int cp = 0;
		int tmp = 0;
		String pom = null;
		for (int i = 0; i < codeB.length(); i++) {
			if (codeB.charAt(i) == '"') {
				for (int j = i + 1; j < codeB.length(); j++) {
					if (codeB.charAt(j) == '"') {
						pom = (String) codeB.subSequence(i, j + 1).toString();
						tmp = j;
						i = tmp + 1;
						break;
					}
				}
			}
			if (codeB.charAt(i) == ' ' || codeB.charAt(i) == '\n'
					|| codeB.charAt(i) == '\t') {
				words.addLast((String) codeB.subSequence(cp, i));
				cp = i;
			}
			if (i + 1 == codeB.length()) {
				words.addLast((String) codeB.subSequence(cp, i + 1));
			}
		}
	}

	public void forSyso() {
		LinkedList<String> sysoList = new LinkedList<String>();
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).equals("System.out.println")) {
				String s = words.get(i).toString();
				StringBuffer sb = new StringBuffer(s);
				for (int j = 0; j < sb.length(); j++) {
					if (sb.charAt(j) == '.') {
						sb.insert(j, " ");
						sb.insert(j + 2, " ");
						j++;
					}
				}
				System.out.println(sb.toString());
			}
		}
	}

	public void checkString() {
		for (int i = 0; i < words.size(); i++) {
			// if(words.get(i).charAt()){
			//
			// }
		}
	}

	public void addSpaces() {
		for (int i = 0; i < codeB.length(); i++) {
			if (codeB.charAt(i) == '{' || codeB.charAt(i) == '}'
					|| codeB.charAt(i) == '(' || codeB.charAt(i) == ')'
					|| codeB.charAt(i) == ',' || codeB.charAt(i) == ';'
					|| codeB.charAt(i) == '[' || codeB.charAt(i) == ']'){
				codeB.insert(i, " ");
				codeB.insert(i + 2, " ");
				i++;

			}

			if (codeB.charAt(i) == '=' && codeB.charAt(i + 1) != '='
					&& codeB.charAt(i + 1) != '<' && codeB.charAt(i + 1) != '>'&& codeB.charAt(i + 1) != '!'
					&& codeB.charAt(i - 1) != '=' && codeB.charAt(i - 1) != '<'
					&& codeB.charAt(i - 1) != '>'&& codeB.charAt(i -1) != '!') {
				codeB.insert(i, " ");
				codeB.insert(i + 2, " ");
				i++;

			}
			if (codeB.charAt(i) == '<' && codeB.charAt(i + 1) != '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 2, " ");
				i++;

			}
			if (codeB.charAt(i) == '>' && codeB.charAt(i + 1) != '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 2, " ");
				i++;

			}
			if (codeB.charAt(i) == '<' && codeB.charAt(i + 1) == '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}
			if (codeB.charAt(i) == '!' && codeB.charAt(i + 1) == '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}
			if (codeB.charAt(i) == '>' && codeB.charAt(i + 1) == '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}

			if (codeB.charAt(i) == '+' && codeB.charAt(i + 1) == '+') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}
			if (codeB.charAt(i) == '-' && codeB.charAt(i + 1) == '-') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}
			if (codeB.charAt(i) == '=' && codeB.charAt(i + 1) == '=') {
				codeB.insert(i, " ");
				codeB.insert(i + 3, " ");
				i++;
			}
		}
	}

	public void eraseSpaces() {
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).equalsIgnoreCase(" ")
					|| words.get(i).equalsIgnoreCase("  ")
					|| words.get(i).equalsIgnoreCase("   ")) {
				words.remove(i);
			} else if (words.get(i).contains(" ")
					&& !words.get(i).contains("\"")) {
				String s = words.get(i).replaceAll(" ", "");
				words.remove(i);
				words.add(i, s);
			}
			del();
		}
	}

	public void del() {
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).equals(" ")) {
				words.remove(i);
			}
		}
	}

	public void printWords() {
		for (int i = 0; i < words.size(); i++) {
			System.out.print(i + " " + words.get(i).toString() + " ");
		}
	}

	public void changeKeyWords() {
		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < keywords.length; j++) {
				if (words.get(i).equals(keywords[j])) {
					words.remove(i);
					Token t = new Token("KEYWORD(" + keywords[j].toUpperCase()
							+ ")", keywords[j].toUpperCase());
					words.add(i, t.toString());
				}
			}
		}

		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < operators.length; j++) {
				if (words.get(i).equals(operators[j])) {

					words.remove(i);
					Token t = new Token("OPERATOR(" + operators[j] + ")",
							operators[j]);
					words.add(i, t.toString());
				}
			}
		}

		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < identifiers.length; j++) {
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace("(", "LPAREN"),
									"(").toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace(")", "RPAREN"),
									")").toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace("{", "LBRACE"),
									"{").toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace("}", "RBRACE"),
									"}").toString());
				}
				
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace("[", "LARRAY"),
									"[").toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace("]", "RARRAY"),
									"]").toString());
				}
				
				
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i, new Token(
							identifiers[j].replace(",", "COMMA"), ",")
							.toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i,
							new Token(identifiers[j].replace(";", "SEMICOLON"),
									";").toString());
				}
				if (words.get(i).equals(identifiers[j])) {
					words.remove(i);
					words.add(i, new Token(
							identifiers[j].replace(".", "POINT"), ";")
							.toString());
				}
			}
		}
	}

	public void changeV() {
		for (int i = 0; i < words.size(); i++) {
			String t = null;
			if (words.get(i).contains(";")) {
				t = words.get(i).substring(0, words.get(i).length() - 1);
			} else {
				t = words.get(i);
			}
			if (t.matches("[a-z][a-z0-9]*")) {
				words.remove(i);
				words.add(i, new Token(("ID(" + t + ")"), t).toString());
				// words.add(i, ("ID (" + t + ")"));
			} else if (t.matches("[!][a-z][a-z0-9]*")) {
				words.remove(i);
				words.add(
						i,
						new Token(
								("BANG ID(" + t.substring(1, t.length()) + ")"),
								t.substring(1, t.length())).toString());
				// words.add(i, ("BANG ID(" + t.substring(1, t.length()) +
				// ")"));
			} else if (t.contains("\"")) {
				words.remove(i);
				words.add(i,
						new Token(
								("STRING(" + t.substring(1, t.length()) + ")"),
								t.substring(1, t.length())).toString());
				// words.add(i,("STRING (" + t.substring(1, t.length() - 1) +
				// ")"));
			} else if (t.matches("[0-9]+")) {
				words.remove(i);
				words.add(i, new Token(("NUM(" + t + ")"), t).toString());
				// words.add(i, ("NUM (" + t + ")"));
			} else if (t.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
				words.remove(i);
				words.add(i, new Token(("DOUBLE(" + t + ")"), t).toString());
				// words.add(i, ("REAL (" + t + ")"));
			}
			else if (t.matches("['][a-z][a-z0-9]*[']")) {
				words.remove(i);
				words.add(i, new Token(("CHAR(" + t + ")"), t).toString());
				// words.add(i, ("REAL (" + t + ")"));
			}
		}
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		LexicalAnalyzer.code = code;
	}

	public static StringBuffer getCodeB() {
		return codeB;
	}

	public static void setCodeB(StringBuffer codeB) {
		LexicalAnalyzer.codeB = codeB;
	}

	public static LinkedList<String> getWords() {
		return words;

	}

	public static void setWords(LinkedList<String> words) {
		LexicalAnalyzer.words = words;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String[] getOperators() {
		return operators;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}

	public String[] getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(String[] identifiers) {
		this.identifiers = identifiers;
	}
}
