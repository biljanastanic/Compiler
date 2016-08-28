package main;

import java.util.Iterator;
import java.util.LinkedList;

public class CodeGenerator {
	LexicalAnalyzer la = new LexicalAnalyzer();
	LinkedList<String> idInt = new LinkedList<String>();
	LinkedList<ID> reservedReg = new LinkedList<ID>();
	LinkedList<String> mipsList = new LinkedList<String>();
	LinkedList<String> reservedStack = new LinkedList<String>();
	LinkedList<ID> listId = new LinkedList<>();
	LinkedList<ID> listForDelete = new LinkedList<ID>();
	int counterOpenId = 8;

	public CodeGenerator(LinkedList<String> idInt) {
		super();
		this.idInt = idInt;
	}

	public LinkedList<ID> addReservedReg() {
		for (int i = 0; i < la.words.size(); i++) {
			try {
				if (la.words.get(i).equals("KEYWORD(INT)")
						&& !la.words.get(i + 4).equals("OPERATOR(+)")) {
					if (la.words.get(i + 1).contains("ID")) {
						if (la.words.get(i + 3).contains("NUM")
								&& la.words.get(i + 4).equals("SEMICOLON")) {
							listId.add(new ID("$" + counterOpenId, la.words
									.get(i + 1).toString(), la.getWords().get(
									i + 3), "int"));
							mipsList.add("addi "
									+ listId.getLast().getRegistar()
									+ ", $0, "
									+ listId.getLast()
											.getValue()
											.substring(
													listId.getLast().getValue()
															.indexOf("(") + 1,
													listId.getLast().getValue()
															.indexOf(")")));
							counterOpenId++;
						} else if (la.words.get(i + 2).equals("SEMICOLON")) {
							listId.add(new ID("$" + counterOpenId, la.words
									.get(i + 1).toString(), null, "int"));
							mipsList.add("add "
									+ listId.getLast().getRegistar()
									+ ", $0, $0");
							counterOpenId++;
						} else if (la.words.get(i + 3).contains("ID")
								&& la.words.get(i + 4).equals("SEMICOLON")) {
							for (int j = 0; j < listId.size(); j++) {
								if (listId.get(j).getIdentification()
										.equals(la.words.get(i + 3))) {
									listId.add(new ID("$" + counterOpenId,
											la.words.get(i + 1).toString(),
											listId.get(j).getValue(), "int"));
									mipsList.add("add "
											+ listId.getLast().getRegistar()
											+ ", $0, "
											+ listId.get(j).getRegistar());
									counterOpenId++;
								}
							}
						}
					}
				} else 
				if (la.words.get(i).equals("KEYWORD(IF)")) {
					i = chekIfKeyword(i);
//					mipslist.removeLast();
//					chekIfKeyword(i);
					mipsList.add("jExit");
					
				} else if(la.words.get(i).equals("KEYWORD(FOR)")){
					i = chekForKewyord(i); 
					mipsList.add("jExit");
					
				}else
					// if(la.words.get(i).contains("ID") &&
					// !la.words.get(i).equals("KEYWORD(INT)") &&
					// la.words.get(i+3).equals("OPERATOR(+)")) {
					whereIsKeyword(i);
				// } else if(la.words.get(i).equals("KEYWORD(INT)") &&
				// !la.words.get(i+4).equals("OPERATOR(+)")) {
				// checkForMoreOperations(i+2);
				// }
			} catch (IndexOutOfBoundsException e) {
				if (la.words.get(i).equals("KEYWORD(INT)")) {
					if (la.words.get(i + 1).contains("ID")) {
						if (la.words.get(i + 3).contains("NUM")
								&& la.words.get(i + 4).equals("SEMICOLON")) {
							listId.add(new ID("$" + counterOpenId, la.words
									.get(i + 1).toString(), la.getWords().get(
									i + 3), "int"));
							mipsList.add("addi "
									+ listId.getLast().getRegistar()
									+ ", $0, "
									+ listId.getLast()
											.getValue()
											.substring(
													listId.getLast().getValue()
															.indexOf("(") + 1,
													listId.getLast().getValue()
															.indexOf(")")));
							counterOpenId++;
						} else if (la.words.get(i + 2).equals("SEMICOLON")) {
							listId.add(new ID("$" + counterOpenId, la.words
									.get(i + 1).toString(), null, "int"));
							mipsList.add("add "
									+ listId.getLast().getRegistar()
									+ ", $0, $0");
							counterOpenId++;
						} else if (la.words.get(i + 3).contains("ID")
								&& la.words.get(i + 4).equals("SEMICOLON")) {
							for (int j = 0; j < listId.size(); j++) {
								if (listId.get(j).getIdentification()
										.equals(la.words.get(i + 3))) {
									listId.add(new ID("$" + counterOpenId,
											la.words.get(i + 1).toString(),
											listId.get(j).getValue(), "int"));
									mipsList.add("add "
											+ listId.getLast().getRegistar()
											+ ", $0, "
											+ listId.get(j).getRegistar());
									counterOpenId++;
								}
							}
						}
					}
				} else
					// if(la.words.get(i).contains("ID") &&
					// !la.words.get(i).equals("KEYWORD(INT)") &&
					// la.words.get(i+3).equals("OPERATOR(+)")) {
					whereIsKeyword(i);
				// } else if(la.words.get(i).equals("KEYWORD(INT)") &&
				// !la.words.get(i+4).equals("OPERATOR(+)")) {
				// checkForMoreOperations(i+2);
				// }
			}
		}
		return listId;
	}

	public int whereIsKeyword(int k) {
		int getIdPosition = 0;
		if (la.words.get(k).contains("ID")
				&& !la.words.get(k - 1).contains("KEYWORD")) {

			if (la.words.get(k + 2).contains("NUM")
					&& la.words.get(k + 3).equals("SEMICOLON")) {
				for (int j = 0; j < listId.size(); j++) {
					if (listId.get(j).getIdentification()
							.equals(la.words.get(k))) {
						getIdPosition = j;
						break;
					}
				}
				listId.get(getIdPosition).setValue(la.getWords().get(k + 2));
				// listId.add(j, new ID("$" + counterOpenId, la.words.get(k)
				// .toString(), la.getWords().get(k+2), "int"));
				mipsList.add("addi "
						+ listId.get(getIdPosition).getRegistar()
						+ ", $0, "
						+ listId.get(getIdPosition)
								.getValue()
								.substring(
										listId.get(getIdPosition).getValue()
												.indexOf("(") + 1,
										listId.get(getIdPosition).getValue()
												.indexOf(")")));
				k = k + 3;

			} else if (la.words.get(k + 2).contains("ID")
					&& la.words.get(k + 3).equals("SEMICOLON")) {
				for (int j = 0; j < listId.size(); j++) {
					if (listId.get(j).getIdentification()
							.equals(la.words.get(k - 2))) {
						getIdPosition = j;
					}
				}
				listId.get(getIdPosition).setValue(la.getWords().get(k + 2));
				// listId.add(j, new ID("$" + counterOpenId, la.words.get(k)
				// .toString(), la.getWords().get(k+2), "int"));
				mipsList.add("addi "
						+ listId.getLast().getRegistar()
						+ ", $0, "
						+ listId.getLast()
								.getValue()
								.substring(
										listId.getLast().getValue()
												.indexOf("(") + 1,
										listId.getLast().getValue()
												.indexOf(")")));

			} else if (!la.words.get(k + 2).contains("ID")
					&& !la.words.get(k + 3).equals("SEMICOLON")) {
				for (int j = 0; j < listId.size(); j++) {
					if (listId.get(j).getIdentification()
							.equals(la.words.get(k - 2))) {
						getIdPosition = j;
					}
				}
				LinkedList<ID> chekfest = checkForMoreOperations(k + 1);
				if (chekfest != null) {
					String[] eeee = { listId.get(getIdPosition).getRegistar(),
							listId.get(getIdPosition).getIdentification(),
							chekfest.getLast().getValue(), "int" };
					listId.remove(getIdPosition);
					listId.add(getIdPosition, new ID(eeee[0], eeee[1], eeee[2],
							eeee[3]));
					mipsList.add("add "
							+ listId.get(getIdPosition).getRegistar()
							+ ", $0, "
							+ chekfest.get(chekfest.size() - 1).getRegistar());
				}
			}
		}
		return k;
	}

	public LinkedList<ID> checkForMoreOperations(int k) {
		int b = 0;
		int counterfakeId = counterOpenId;
		LinkedList<ID> fakeId = new LinkedList<ID>();
		if (la.getWords().get(k).equals("OPERATOR(=)")) {
			if (la.getWords().get(k + 2).equals("OPERATOR(+)")) {
				while (true) {
					if ((la.getWords().get(k + 1).contains("ID") || la
							.getWords().get(k + 1).contains("NUM"))
							&& (la.getWords().get(k + 3).contains("ID") || la
									.getWords().get(k + 3).contains("NUM"))) {
						if (la.getWords().get(k + 2).equals("OPERATOR(+)")) {
							int abb = Integer.parseInt(la
									.getWords()
									.get(k + 1)
									.substring(
											la.getWords().get(k + 1)
													.indexOf("(") + 1,
											la.getWords().get(k + 1)
													.indexOf(")")));
							int acc = Integer.parseInt(la
									.getWords()
									.get(k + 3)
									.substring(
											la.getWords().get(k + 3)
													.indexOf("(") + 1,
											la.getWords().get(k + 3)
													.indexOf(")")));
							int abc = abb + acc;
							int abibi1 = 0;
							int abibi2 = 0;
							for (int i = 0; i < listId.size(); i++) {

								if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 1))) {
									abibi1 = i;
								} else if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 3))) {
									abibi2 = i;
								}
							}

							if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("NUM")) {
								if (counterfakeId == this.counterOpenId) {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("addi "
											+ fakeId.getLast().getRegistar()
											+ ", " + Integer.toString(abb)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								} else {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("addi "
											+ fakeId.getLast().getRegistar()
											+ ", $"
											+ Integer
													.toString(counterfakeId - 1)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								}
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("NUM")) {
								mipsList.add("addi "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", " + Integer.toString(acc));
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("addi "
										+ fakeId.getLast().getRegistar() + ", "
										+ Integer.toString(acc) + ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("add "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							}

						}
						if (la.getWords().get(k + 4).equals("SEMICOLON")) {
							k++;
							break;
						}
						k++;
					}
					k++;
				}
			}else if (la.getWords().get(k + 2).equals("OPERATOR(-)")) {
				while (true) {
					if ((la.getWords().get(k + 1).contains("ID") || la
							.getWords().get(k + 1).contains("NUM"))
							&& (la.getWords().get(k + 3).contains("ID") || la
									.getWords().get(k + 3).contains("NUM"))) {
						if (la.getWords().get(k + 2).equals("OPERATOR(-)")) {
							int abb = Integer.parseInt(la
									.getWords()
									.get(k + 1)
									.substring(
											la.getWords().get(k + 1)
													.indexOf("(") + 1,
											la.getWords().get(k + 1)
													.indexOf(")")));
							int acc = Integer.parseInt(la
									.getWords()
									.get(k + 3)
									.substring(
											la.getWords().get(k + 3)
													.indexOf("(") + 1,
											la.getWords().get(k + 3)
													.indexOf(")")));
							int abc = abb - acc;
							int abibi1 = 0;
							int abibi2 = 0;
							for (int i = 0; i < listId.size(); i++) {

								if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 1))) {
									abibi1 = i;
								} else if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 3))) {
									abibi2 = i;
								}
							}

							if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("NUM")) {
								if (counterfakeId == this.counterOpenId) {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("subi "
											+ fakeId.getLast().getRegistar()
											+ ", " + Integer.toString(abb)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								} else {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("subi "
											+ fakeId.getLast().getRegistar()
											+ ", $"
											+ Integer
													.toString(counterfakeId - 1)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								}
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("NUM")) {
								mipsList.add("subi "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", " + Integer.toString(acc));
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("subi "
										+ fakeId.getLast().getRegistar() + ", "
										+ Integer.toString(acc) + ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("sub "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							}

						}
						if (la.getWords().get(k + 4).equals("SEMICOLON")) {
							k++;
							break;
						}
						k++;
					}
					k++;
				}
			}if (la.getWords().get(k + 2).equals("OPERATOR(*)")) {
				while (true) {
					if ((la.getWords().get(k + 1).contains("ID") || la
							.getWords().get(k + 1).contains("NUM"))
							&& (la.getWords().get(k + 3).contains("ID") || la
									.getWords().get(k + 3).contains("NUM"))) {
						if (la.getWords().get(k + 2).equals("OPERATOR(*)")) {
							int abb = Integer.parseInt(la
									.getWords()
									.get(k + 1)
									.substring(
											la.getWords().get(k + 1)
													.indexOf("(") + 1,
											la.getWords().get(k + 1)
													.indexOf(")")));
							int acc = Integer.parseInt(la
									.getWords()
									.get(k + 3)
									.substring(
											la.getWords().get(k + 3)
													.indexOf("(") + 1,
											la.getWords().get(k + 3)
													.indexOf(")")));
							int abc = abb * acc;
							int abibi1 = 0;
							int abibi2 = 0;
							for (int i = 0; i < listId.size(); i++) {

								if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 1))) {
									abibi1 = i;
								} else if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 3))) {
									abibi2 = i;
								}
							}

							if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("NUM")) {
								if (counterfakeId == this.counterOpenId) {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("muli "
											+ fakeId.getLast().getRegistar()
											+ ", " + Integer.toString(abb)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								} else {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("muli "
											+ fakeId.getLast().getRegistar()
											+ ", $"
											+ Integer
													.toString(counterfakeId - 1)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								}
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("NUM")) {
								mipsList.add("muli "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", " + Integer.toString(acc));
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("muli "
										+ fakeId.getLast().getRegistar() + ", "
										+ Integer.toString(acc) + ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("mul "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							}

						}
						if (la.getWords().get(k + 4).equals("SEMICOLON")) {
							k++;
							break;
						}
						k++;
					}
					k++;
				}
			}else if (la.getWords().get(k + 2).equals("OPERATOR(/)")) {
				while (true) {
					if ((la.getWords().get(k + 1).contains("ID") || la
							.getWords().get(k + 1).contains("NUM"))
							&& (la.getWords().get(k + 3).contains("ID") || la
									.getWords().get(k + 3).contains("NUM"))) {
						if (la.getWords().get(k + 2).equals("OPERATOR(/)")) {
							int abb = Integer.parseInt(la
									.getWords()
									.get(k + 1)
									.substring(
											la.getWords().get(k + 1)
													.indexOf("(") + 1,
											la.getWords().get(k + 1)
													.indexOf(")")));
							int acc = Integer.parseInt(la
									.getWords()
									.get(k + 3)
									.substring(
											la.getWords().get(k + 3)
													.indexOf("(") + 1,
											la.getWords().get(k + 3)
													.indexOf(")")));
							int abc = abb / acc;
							int abibi1 = 0;
							int abibi2 = 0;
							for (int i = 0; i < listId.size(); i++) {

								if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 1))) {
									abibi1 = i;
								} else if (listId.get(i).getIdentification()
										.equals(la.getWords().get(k + 3))) {
									abibi2 = i;
								}
							}

							if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("NUM")) {
								if (counterfakeId == this.counterOpenId) {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("divi "
											+ fakeId.getLast().getRegistar()
											+ ", " + Integer.toString(abb)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								} else {
									fakeId.add(new ID("$" + counterfakeId,
											"novi" + counterfakeId, Integer
													.toString(abc), "int"));
									mipsList.add("divi "
											+ fakeId.getLast().getRegistar()
											+ ", $"
											+ Integer
													.toString(counterfakeId - 1)
											+ ", " + Integer.toString(acc));
									counterfakeId++;
								}
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("NUM")) {
								mipsList.add("divi "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", " + Integer.toString(acc));
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("NUM")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("divi "
										+ fakeId.getLast().getRegistar() + ", "
										+ Integer.toString(acc) + ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							} else if (la.getWords().get(k + 1).contains("ID")
									&& la.getWords().get(k + 3).contains("ID")) {
								mipsList.add("div "
										+ fakeId.getLast().getRegistar() + ", "
										+ listId.get(abibi1).getRegistar()
										+ ", "
										+ listId.get(abibi2).getRegistar());
								counterfakeId++;
							}

						}
						if (la.getWords().get(k + 4).equals("SEMICOLON")) {
							k++;
							break;
						}
						k++;
					}
					k++;
				}
			}
		}
		if (fakeId.size() == 0)
			return null;
		else
			return fakeId;
	}
	
	public int checkFakeIDs(int i, int counterFakeId){
		if (la.getWords().get(i + 3).equals("OPERATOR(<)")) {
			String firstValue = la
					.getWords()
					.get(i + 2)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 2).indexOf(")"));
			String secondValue = la
					.getWords()
					.get(i + 4)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 4).indexOf(")"));
			mipsList.add("slt $" + counterFakeId + ", " + firstValue + ", "
					+ secondValue);
			mipsList.add("beq $" + counterFakeId + ", $0, Exit!");
			i = i + 7;
		}else if (la.getWords().get(i + 3).equals("OPERATOR(>)")) {
			String firstValue = la
					.getWords()
					.get(i + 2)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 2).indexOf(")"));
			String secondValue = la
					.getWords()
					.get(i + 4)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 4).indexOf(")"));
			mipsList.add("sgt $" + counterFakeId + ", " + firstValue + ", "
					+ secondValue);
			mipsList.add("beq $" + counterFakeId + ", $0, Exit!");
			i = i + 7;
		}
		else if (la.getWords().get(i + 3).equals("OPERATOR(=)")) {
			String firstValue = la
					.getWords()
					.get(i + 2)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 2).indexOf(")"));
			String secondValue = la
					.getWords()
					.get(i + 4)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 4).indexOf(")"));
			mipsList.add("beq $" + counterFakeId + ", " + firstValue + ", "
					+ secondValue);
			mipsList.add("beq $" + counterFakeId + ", $0, Exit!");
			i = i + 7;
		}
		else if (la.getWords().get(i + 3).equals("OPERATOR(!=)")) {
			String firstValue = la
					.getWords()
					.get(i + 2)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 2).indexOf(")"));
			String secondValue = la
					.getWords()
					.get(i + 4)
					.substring(la.getWords().get(i + 2).indexOf("(") + 1,
							la.getWords().get(i + 4).indexOf(")"));
			mipsList.add("bneq $" + counterFakeId + ", " + firstValue + ", "
					+ secondValue);
			mipsList.add("beq $" + counterFakeId + ", $0, Exit!");
			i = i + 7;
		}
		return i;
	}

	public int chekIfKeyword(int i) { 
		int counterFakeId = this.counterOpenId;
		LinkedList<ID> fakeId = new LinkedList<ID>();
		LinkedList<String> patternIf = new LinkedList<String>();
		if (la.getWords().get(i).equals("KEYWORD(IF)")) {
			i = checkFakeIDs(i, counterFakeId);
			if (la.getWords().get(i).contains("ID")) {
				int ijij = i;
				aiha: while (true) {
					whereIsKeyword(i);
					for (int j = i; j < la.getWords().indexOf("RBRACE"); j++) {
						if (la.getWords().get(j).equals("SEMICOLON")
								&& la.getWords().get(j + 1).contains("ID")) {
							whereIsKeyword(j+1);
							i=whereIsKeyword(j+1)+1;
							mipsList.removeLast();
							continue aiha;
						}
					}
					break;
				}

			}
		}
		return i;
	}
	
	public int chekForKewyord(int i){
		int counterFakeId = this.counterOpenId;
		LinkedList<ID> fakeId = new LinkedList<ID>();
		if (la.getWords().get(i).equals("KEYWORD(FOR)")) {
			if (la.getWords().get(i + 5).contains("NUM")) {
				i = i+5;
				String firstValue = la
						.getWords()
						.get(i)
						.substring(la.getWords().get(i).indexOf("(") + 1,
								la.getWords().get(i).indexOf(")"));
				mipsList.add("addi $" + counterFakeId + ", $0, " + firstValue);
				counterFakeId++;
				i = i+ 3;
			}
			if (la.getWords().get(i).equals("OPERATOR(<)")) {
				i = checkFakeIDs(i - 3, counterFakeId) - 3;
			}
			
			if(la.getWords().get(i+3).contains("++")){
				i=i+6;
			}
			
			if (la.getWords().get(i).contains("ID")) {
				aiha: while (true) {
					whereIsKeyword(i);
					for (int j = i; j < la.getWords().indexOf("RBRACE"); j++) {
						if (la.getWords().get(j).equals("SEMICOLON")
								&& la.getWords().get(j + 1).contains("ID")) {
							whereIsKeyword(j+1);
							i=whereIsKeyword(j+1)+1;
							mipsList.removeLast();
							continue aiha;
						}
					}
					break;
				}

			}
		}
		return i;
	}

	public LinkedList<String> changeOperation() {
		LinkedList<String> cp = new LinkedList<String>();
		for (int i = 0; i < la.words.size(); i++) {
			if (la.words.get(i).equals("OPERATOR(=)")) {
				if (la.words.get(i - 1).contains("ID")
						&& la.words.get(i + 1).contains("NUM")
						&& la.words.get(i + 2).equals("SEMICOLON")) {
					for (int j = 0; j < listId.size(); j++) {
						if (la.words.get(i - 1).equals(
								listId.get(j).getIdentification())) {
							mipsList.add("addi "
									+ listId.get(j).getRegistar()
									+ ", $0, "
									+ la.words.get(i + 1).substring(
											la.getWords().get(i + 1)
													.indexOf("(") + 1,
											la.getWords().get(i + 1)
													.indexOf(")")));
							// re

						}
					}
				} else if (la.words.get(i - 1).contains("ID")
						&& la.words.get(i - 2).contains("KEYWORD")
						&& !la.words.get(i + 1).contains("ID")) {
					listId.add(new ID("$" + counterOpenId, la.words.get(i - 1),
							la.words.get(i + 1), la.words.get(i - 2)));
					counterOpenId++;
					mipsList.add("add "
							+ "$"
							+ counterOpenId
							+ " $0, "
							+ la.words.get(i + 1).substring(
									la.words.get(i + 1).indexOf("("),
									la.words.get(i + 1).indexOf(")")));
				} else if (la.words.get(i - 1).contains("ID")
						&& la.words.get(i - 2).contains("KEYWORD")
						&& la.words.get(i + 1).contains("ID")) {
					String value = null;
					String oldregist = null;
					for (int j = 0; j < listId.size(); j++) {
						if (listId.get(j).getIdentification()
								.equals(la.words.get(i + 1))) {
							value = listId.get(j).getValue();
							oldregist = listId.get(j).getRegistar();
						}

						//
					}
					listId.add(new ID("$" + counterOpenId, la.words.get(i - 1),
							value, la.words.get(i - 2)));
					mipsList.add("add " + "$" + counterOpenId + " $0, "
							+ oldregist);

				} else if (la.words.get(i - 1).contains("ID")
						&& la.words.get(i + 2).contains("ID")) {
					int ka = 0;
					while (ka <= checkHowMuchSumsMustToDo(i)) {
						checkHowMuchSumsMustToDo(i);
						ka++;
					}
				}
			}
		}
		return cp;
	}

	public int checkHowMuchSumsMustToDo(int i) {
		int counter = 0;
		if (la.words.get(i + 2).equals("SEMICOLON"))
			return 0;
		for (int j = i + 1; j < la.words.get(j + 1).indexOf("SEMICOLON"); j++) {
			if (la.words.get(j).equals("+") || la.words.get(j).equals("-")) {
				counter++;
				if (counter == 1) {
					for (int j2 = 0; j2 < listId.size(); j2++) {
						if (listId.get(j2).getIdentification()
								.equals(la.words.get(i - 1))) {
							int uha = Integer.parseInt(la.words.get(j - 1)
									.substring(
											la.words.get(j - 1).indexOf("("),
											la.words.get(i + 1).indexOf(")")))
									+ Integer.parseInt(la.words.get(j + 1)
											.substring(
													la.words.get(j + 1)
															.indexOf("("),
													la.words.get(i + 1)
															.indexOf(")")));
							listId.get(j2).setValue(Integer.toString(uha));
							mipsList.add("addi" + listId.get(j2).getRegistar()
									+ ", " + la.words.get(j - 1).toString()
									+ ", " + la.words.get(j + 1).toString());
						}
					}
				}
			}
		}
		return counter;
	}

	public LinkedList<String> changeOperation1() {
		LinkedList<String> cp = new LinkedList<String>();
		for (int i = 0; i < la.words.size(); i++) {
			if (la.words.get(i).contains("ID")
					&& idInt.contains(la.words.get(i))) {
				if (!la.words.get(i - 1).equals("KEYWORD(INT)")) {
					for (int j = 0; j < listId.size(); j++) {
						if (listId.get(j).getIdentification()
								.equals(la.words.get(i))) {
							String[] a = {
									listId.get(j).getRegistar(),
									listId.get(j).getIdentification(),
									la.words.get(i + 2).substring(
											la.getWords().get(i + 2)
													.indexOf("(") + 1,
											la.getWords().get(i + 2)
													.indexOf(")")),
									listId.get(j).getType() };
							listId.remove(j);
							listId.add(j, new ID(a[0], a[1], a[2], a[3]));
							mipsList.add("addi "
									+ listId.get(j).getRegistar()
									+ ", $0, "
									+ la.words.get(i + 2).substring(
											la.getWords().get(i + 2)
													.indexOf("(") + 1,
											la.getWords().get(i + 2)
													.indexOf(")")));
						}

					}
				}

			}
		}
		return cp;
	}

	public void finalOutput() {
		Iterator<String> itermipsList = mipsList.iterator();
		while (itermipsList.hasNext()) {
			String string = (String) itermipsList.next();
			System.out.println(string);
		}
//		abiha:	for (int i = 0; i < mipslist.size(); i++) {
//			if(i>2 ){
//				if(mipslist.get(i).equals(mipslist.get(i-2))){
//					continue abiha;
//				}
//			} 
//			System.out.println(mipslist.get(i));
//		}
	}

	// // int b;
	// //// int a = 1+b-c;
	// //// add $ $1 $b; 1+b
	// add $ $1+b $c

	public void definePlus() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("OPERATOR(=)")) {
				if (la.getWords().get(i + 2).equals("OPERATOR(+)")) {
					for (int j = 0; j < listId.size(); j++) {
						if (listId.get(j).getIdentification()
								.equals(la.getWords().get(i + 1))
								|| la.getWords().get(i + 1).contains("NUM")) {
							if (listId.get(j).getIdentification()
									.equals(la.getWords().get(i + 3))
									|| la.getWords().get(i + 3).contains("NUM")) {
								mipsList.add("add "
										+ listId.get(j).getRegistar());
								System.out.println("s");
							}
						}
					}
				}
			}
		}
	}

	// public int inputParameters(String id){
	// int inputint = -1;
	// // for(int i = 0; i<la.words.size(); i++){
	// if(id.contains("ID")){
	// if(id.contains("KEYWORD") || la.words.get(i-2).contains("KEYWORD"))
	// if(la.words.get(i+1).equals("LPAREN")){
	// for (int j = i+2; j < la.words.indexOf("RPAREN"); j++) {
	// if(la.words.get(j).equals("COMMA")) inputint++;
	// }
	// }
	// // }
	// }
	// return inputint;
	// }

	public int startTranslate() {
		for (int i = 0; i < la.words.size(); i++) {
			if (la.words.get(i).equals("LBRACE")) {
				return i + 1;
			}
		}
		return 0;
	}

	public LinkedList<String> checkit() {
		LinkedList<String> regists = new LinkedList<String>();
		for (int i = startTranslate(); i < la.words.size(); i++) {
			if (la.words.get(i).equals("KEYWORD(INT)")) {
				if (la.words.get(i + 1).contains("ID")) {
					regists.add("$9");
					if (la.words.get(i + 2).equals("OPERATOR(=)")) {
						if (la.words.get(i + 2).contains("NUM")) {
							regists.addLast("addi");
							regists.addLast("$0");
							regists.addLast("10");
						}
					}
				}
			}

		}
		return regists;
	}

}
