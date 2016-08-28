package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class SemanticAnalyzer {
	LexicalAnalyzer la = new LexicalAnalyzer();
	LinkedList<String> idString = new LinkedList<String>();
	LinkedList<String> idInt = new LinkedList<String>();
	LinkedList<String> idDouble = new LinkedList<String>();
	LinkedList<String> idChar = new LinkedList<String>();
	LinkedList<IdArrayInt> idArrayInt = new LinkedList<IdArrayInt>();
	LinkedList<String> operForInt = new LinkedList<String>();
	LinkedList<String> operForString = new LinkedList<String>();
	SyntaxAnalyzer sa = new SyntaxAnalyzer();

	public LinkedList<String> addStringIds() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).contains("ID")) {
				if (la.getWords().get(i - 1).equals("KEYWORD(STRING)")) {
					if (!idString.contains(la.getWords().get(i))) {
						idString.add(la.getWords().get(i));
					} else {
						System.out.println("Variable "
								+ la.getWords().get(i).toString()
								+ " is already initialiazed");
					}
				}
			}
		}
		return idString;
	}

	public LinkedList<String> addIntIds() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).contains("ID")) {
				if (la.getWords().get(i - 1).equals("KEYWORD(INT)")) {
					if (!idInt.contains(la.getWords().get(i))) {
						idInt.add(la.getWords().get(i));
					} else {
						System.out.println("Variable "
								+ la.getWords().get(i).toString()
								+ " is already initialiazed");
					}
				}
			}
		}
		return idInt;
	}

	public LinkedList<String> addDoubleIds() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).contains("ID")) {
				if (la.getWords().get(i - 1).equals("KEYWORD(DOUBLE)")) {
					if (!idDouble.contains(la.getWords().get(i))) {
						idDouble.add(la.getWords().get(i));
					} else {
						System.out.println("Variable "
								+ la.getWords().get(i).toString()
								+ " is already initialiazed");
					}
				}
			}
		}
		return idDouble;
	}

	public LinkedList<String> addCharIds() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).contains("ID")) {
				if (la.getWords().get(i - 1).equals("KEYWORD(CHAR)")) {
					if (!idChar.contains(la.getWords().get(i))) {
						idChar.add(la.getWords().get(i));
					} else {
						System.out.println("Variable "
								+ la.getWords().get(i).toString()
								+ " is already initialiazed");
					}
				}
			}
		}
		return idChar;
	}

	public void checkString() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(STRING)")) {
				if (la.getWords().get(i + 1).contains("ID")) {
					if (la.getWords().get(i + 2).contains("OPERATOR(=)")) {
						if (la.getWords().get(i + 3).contains("ID")
								|| la.getWords().get(i + 3).contains("STRING")) {
							if ((idString.contains(la.getWords().get(i + 1)) && idString
									.contains(la.getWords().get(i + 3)))
									|| la.getWords().get(i + 3)
											.contains("STRING")) {
							} else {
								System.out
										.println("Incompatible types String and int.");
							}
						} else {
							System.out.println("Define same type variables.");
						}
					}
				}
			}
		}
	}

	public void checkDouble() {
		this.addDoubleIds();
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(DOUBLE)")) {
				if (la.getWords().get(i + 1).contains("ID")) {
					if (la.getWords().get(i + 2).contains("OPERATOR(=)")) {
						if (la.getWords().get(i + 3).contains("ID")
								|| la.getWords().get(i + 3).contains("NUM")
								|| la.getWords().get(i + 3).contains("DOUBLE")) {
							if ((idDouble.contains(la.getWords().get(i + 1)) && idDouble
									.contains(la.getWords().get(i + 3)))
									|| la.getWords().get(i + 3)
											.contains("DOUBLE")) {
							} else {
								System.out.println("Incompatible types.");
							}
						} else {
							System.out.println("Define same type variables.");
						}
					}
				} else {
					System.out.println();
				}
			}
		}
	}

	public void checkChar() {
		this.addCharIds();
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(CHAR)")) {
				if (la.getWords().get(i + 1).contains("ID")) {
					if (la.getWords().get(i + 2).contains("OPERATOR(=)")) {
						if (la.getWords().get(i + 3).contains("ID")
								|| la.getWords().get(i + 3).contains("CHAR")) {
							if ((idChar.contains(la.getWords().get(i + 1)) && idChar
									.contains(la.getWords().get(i + 3)))
									|| la.getWords().get(i + 3)
											.contains("CHAR")) {
							} else {
								System.out.println("Incompatible types.");
							}
						} else {
							System.out.println("Define same type variables.");
						}
					}
				} else {
					System.out.println();
				}
			}
		}
	}

	public int intForIdArrayInt(String a) {
		if (a == null)
			return 0;
		if (a.contains("ID")) {
			try {
				String b = a.substring(3, a.length() - 1);
				return Integer.parseInt(b);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (a.contains("NUM")) {
			String b = a.substring(4, a.length() - 1);
			return Integer.parseInt(b);
		}
		return 0;
	}

	// check if new "int" is in a regular form;
	public boolean checkInt() {
		boolean checkIntBool = false;
		for (int i = 0; i < la.getWords().size(); i++) {
			int checkArrayfull = 0; // for checking if it is a array
			if (la.getWords().get(i).equals("KEYWORD(INT)")) {
				if (sa.checkNewArray(i)) {
					i += 4;
					// idArrayInt.add(new IdArrayInt(la.getWords().get(i+1),
					// 0));
					checkArrayfull = 1;
				} else if (sa.checkRegularInt(i)) {
					checkArrayfull = 3;
					i++;
				}

				// if (la.getWords().get(i + 1).contains("ID")) {
				if (la.getWords().get(i).contains("OPERATOR(=)")) {
					if (checkArrayfull == 3) {
						if (la.getWords().get(i + 1).contains("ID")
								|| la.getWords().get(i + 1).contains("NUM")) {
							if ((!idInt.contains(la.getWords().get(i - 1)) && !idInt
									.contains(la.getWords().get(i + 1)))
									|| !la.getWords().get(i + 1)
											.contains("NUM")) {
								System.out
										.println("Incompatible types int and String.");
							}
							checkIntBool = true;
						} else {
							System.err
									.println("define variable FALSE------------"
											+ la.getWords().get(i + 1));
						}

					} else if (checkArrayfull == 1) {
						if (la.getWords().get(i + 1).equals("KEYWORD(NEW)")) {
							if (la.getWords().get(i + 2).equals("KEYWORD(INT)")) {
								if (sa.checkArray((i + 2))) {
									idArrayInt.add(new IdArrayInt(la.getWords()
											.get(i - 1), intForIdArrayInt(la
											.getWords().get(i + 4))));
									i += 3;
								}
							} else {
								System.out
										.println("Define same type variables. int");
							}
						} else {
							System.out
									.println("Define same type variables. new");
						}
					}
				} else if (la.getWords().get(i).equals("SEMICOLON")
						&& la.getWords().get(i - 1).contains("ID")
						&& la.getWords().get(i - 2).equals("RARRAY")) {
					idArrayInt.add(new IdArrayInt(la.getWords().get(i - 1), 0));
				}
			} else if (la.getWords().get(i).contains("ID")) {
				if (la.getWords().get(i + 1).equals("LARRAY")) {
					if (la.getWords().get(i + 2).contains("NUM")) {
						if (!checkArrayCorrect(la.getWords().get(i + 2), la
								.getWords().get(i)))
							System.err
									.println("Exception in thread \"main\" java.lang.IndexOutOfBoundsException: Index: "
											+ (i + 2));
					}
				}
			}
		}
		return checkIntBool;
	}

	public boolean checkArrayCorrect(String a, String b) { 
	// for (int i = 0; i < la.words.size(); i++) {
		if (a.contains("NUM")) {
			// if (sy.checkArray(i)) {
			for (int j = 0; j < idArrayInt.size(); j++) {
				if (b.equals(idArrayInt.get(j).id)) {
					if (intForIdArrayInt(a) < idArrayInt.get(j).idLength)
						return true;
				}
			}
			// }
		}
		// }
		return false;
	}

	// check if variable are initialized
	public void checkId() {
		for (int i = 0; i < la.words.size(); i++) {
			if (la.words.get(i).contains("ID")) {
				if (!idInt.contains(la.words.get(i))) {
					if (!idString.contains(la.words.get(i))) {
						if (!idDouble.contains(la.words.get(i))) {
							if (!idChar.contains(la.words.get(i))) {
								int a = -1;
								for (int j = 0; j < idArrayInt.size(); j++) {
									if (idArrayInt.get(j).id.equals(la
											.getWords().get(i).toString())) {
										a = j;
									}
								}
								if (a == -1) {
									System.out
											.println("Variable must be initialized --"
													+ la.words.get(i) + " " + i);
								}
							}
						}
					}
				}

			}
		}

	}

	public void checkIf() {
		operForInt.add("OPERATOR(==)");
		operForInt.add("OPERATOR(!=)");
		operForInt.add("OPERATOR(<)");
		operForInt.add("OPERATOR(>)");
		operForInt.add("OPERATOR(<=)");
		operForInt.add("OPERATOR(>=)");
		if (this.checkAfterInt()) {
			return;
		}
		for (int i = 0; i < la.words.size(); i++) {
			if (operForInt.contains(la.getWords().get(i))) {
				if ((idInt.contains(la.words.get(i - 1)) || la.words.get(i - 1)
						.contains("NUM"))
						&& (idInt.contains(la.words.get(i + 1)) || la.words
								.get(i + 1).contains("NUM"))) {
				} else {
					if (checkStringOperations() != true) {
						System.out.println("Define variable"
								+ la.getWords().get(i - 1) + " "
								+ la.getWords().get(i + 1));
					} else {

					}
				}
			}
		}
	}

	public boolean checkStringOperations() {
		operForString.add("OPERATOR(==)");
		operForString.add("OPERATOR(!=)");
		if (checkAfterString())
			return false;
		for (int i = 0; i < la.words.size(); i++) {
			if (operForString.contains(la.getWords().get(i))) {
				if (idString.contains(la.words.get(i - 1))
						&& idString.contains(la.words.get(i + 1))) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public boolean checkAfterInt() {
		for (int i = 0; i < la.words.size(); i++) {
			if (operForInt.contains(la.getWords().get(i))) {
				String alb = la.getWords().get(i - 1);
				String abl2 = la.getWords().get(i + 1);
				for (int j = i; j < la.getWords().size(); j++) {
					if (la.getWords().get(j).equals("KEYWORD(INT)")
							&& la.getWords().get(j + 1).equals(alb)) {
						System.out
								.println("Non initialized first variable INT :)");
						return true;
					} else if (la.getWords().get(j).equals("KEYWORD(INT)")
							&& la.getWords().get(j + 1).equals(abl2)) {
						System.out
								.println("Non initialized second variable INT :)");
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkAfterString() {
		for (int i = 0; i < la.words.size(); i++) {
			if (operForString.contains(la.getWords().get(i))) {
				String alb = la.getWords().get(i - 1);
				String abl2 = la.getWords().get(i + 1);
				for (int j = i; j < la.getWords().size(); j++) {
					if (la.getWords().get(j).equals("KEYWORD(STRING)")
							&& la.getWords().get(j + 1).equals(alb)) {
						System.out
								.println("Non initialized first variable STRING :)");
						return true;
					} else if (la.getWords().get(j).equals("KEYWORD(STRING)")
							&& la.getWords().get(j + 1).equals(abl2)) {
						System.out
								.println("Non initialized second variable STRING :)");
						return true;
					}
				}
			}
		}
		return false;
	}

	public LexicalAnalyzer getLa() {
		return la;
	}

	public void setLa(LexicalAnalyzer la) {
		this.la = la;
	}

	public LinkedList<String> getIdString() {
		return idString;
	}

	public void setIdString(LinkedList<String> idString) {
		this.idString = idString;
	}

	public LinkedList<String> getIdInt() {
		return idInt;
	}

	public void setIdInt(LinkedList<String> idInt) {
		this.idInt = idInt;
	}

	public LinkedList<String> getIdDouble() {
		return idDouble;
	}

	public void setIdDouble(LinkedList<String> idDouble) {
		this.idDouble = idDouble;
	}

	public LinkedList<IdArrayInt> getIdArrayInt() {
		return idArrayInt;
	}

	public void setIdArrayInt(LinkedList<IdArrayInt> idArrayInt) {
		this.idArrayInt = idArrayInt;
	}

	public LinkedList<String> getOperForInt() {
		return operForInt;
	}

	public void setOperForInt(LinkedList<String> operForInt) {
		this.operForInt = operForInt;
	}

	public LinkedList<String> getOperForString() {
		return operForString;
	}

	public void setOperForString(LinkedList<String> operForString) {
		this.operForString = operForString;
	}

	public SyntaxAnalyzer getSA() {
		return sa;
	}

	public void setSA(SyntaxAnalyzer sa) {
		this.sa = sa;
	}

}
