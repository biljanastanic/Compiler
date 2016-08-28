package main;
import java.util.LinkedList;

public class SyntaxAnalyzer {
	LexicalAnalyzer la = new LexicalAnalyzer();
	LinkedList<String> patternIf = new LinkedList<String>();
	LinkedList<String> patternFor = new LinkedList<String>();
	LinkedList<String> patternWhile = new LinkedList<String>();
	LinkedList<String> patternElse = new LinkedList<String>();

	
	//check for ----int [] a;
	public boolean checkNewArray(int g) {
		if(la.getWords().get(g - 1).equals("KEYWORD(NEW)")) {  
			return false;										
		}														
		if (la.getWords().get(g+1).equals("LARRAY")) {
			if (la.getWords().get(g + 2).equals("RARRAY")) {
				if(la.getWords().get(g + 3).contains("ID")){
					return true;
				} else {
					System.out.println("Check id after RARRAY " + la.getWords().get(g + 2) + " " + (g+2));
					return false;
				}
			} else {
				System.out.println("Please close RARRAY " + la.getWords().get(g + 2) + " " + (g+2));
				return false;
			}
		} else if(!la.getWords().get(g).equals("LARRAY") && la.getWords().get(g + 1).equals("RARRAY")) {
			System.out.println("Please check LARRAY after " + la.getWords().get(g) + " " + g);
			return false;
		}
		return false;
	}
	
	public boolean checkRegularInt(int g){
		if(la.getWords().get(g+1).contains("ID")){
			return true;
		} else {
			System.out.println("Mising id after " + la.getWords().get(g) + " " + (g));
			return false;
		}
	}
	
	//check for example a[10] = 10;
	public boolean checkArray(int g) { 
		if (la.getWords().get(g+1).equals("LARRAY")) {
			if (la.getWords().get(g + 2).contains("NUM")) {
				if (la.getWords().get(g + 3).equals("RARRAY")) {
					g = g + 3;
					return true;
				} else {
					System.out.println("Please check LARRAY after " + la.getWords().get(g+2) + " " + (g+2));
				}
			} else {
				System.out.println("Define value in array parent " + (g+2));
			}
		} else if(!la.getWords().get(g).equals("LARRAY") && la.getWords().get(g + 1).contains("NUM") && la.getWords().get(g + 2).equals("RARRAY")){
			System.out.println("Please check LARRAY after " + la.getWords().get(g) + " " + g);
		}
		return false;
	}

	public LinkedList<String> checkReturnIf() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(IF)")) {
				patternIf.add(la.getWords().get(i));
				if (la.getWords().get(i + 1).equals("LPAREN")) {
					patternIf.add(la.getWords().get(i + 1));
					if (la.getWords().get(i + 2).contains("ID")
							|| la.getWords().get(i + 2).contains("NUM")) {
						patternIf.add(la.getWords().get(i + 2));
						if (la.getWords().get(i + 3).contains("OPERATOR")) {
							patternIf.add(la.getWords().get(i + 3));
							if (la.getWords().get(i + 4).contains("ID")
									|| la.getWords().get(i + 4).contains("NUM")) {
								patternIf.add(la.getWords().get(i + 4));
								if (la.getWords().get(i + 5).equals("RPAREN")) {
									patternIf.add(la.getWords().get(i + 5));
									if (la.getWords().get(i + 6)
											.equals("LBRACE")) {
										patternIf.add(la.getWords().get(i + 6));

									}
								} else {
									System.out
											.println("Add argument after operator in IF condition");
								}
							} else {
								System.out
										.println("Add operator in IF condition");
							}
						} else {
							System.out
									.println("Add argument before operator in IF condition");
						}
					} else {
						System.out
								.println("You must add open bracket after IF");
					}
				}
			}
		}
		return patternIf;
	}

	public LinkedList<String> checkReturnElse() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(ELSE)")) {
				patternElse.add(la.getWords().get(i));
				if (la.getWords().get(i + 1).equals("LBRACE")) {
					patternElse.add(la.getWords().get(i + 1));

				} else {
					System.out.println("Define { after ELSE.");
				}
			}
		}
		return patternElse;
	}

	public void bracket() {
		int countL = 0;
		int countR = 0;
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("LPAREN")) {
				countL++;
			} else if (la.getWords().get(i).equals("RPAREN")) {
				countR++;
			}
		}
		if (countL == countR) {
			return;
		}
		if (countL > countR) {
			System.out.println(") is missing!");
		} else if (countL < countR) {
			System.out.println("( is missing!");
		}

	}

	public void bracketA() {
		int countL = 0;
		int countR = 0;
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("LBRACE")) {
				countL++;
			} else if (la.getWords().get(i).equals("RBRACE")) {
				countR++;
			}
		}
		if (countL == countR) {
			return;
		}
		if (countL > countR) {
			System.out.println("} is missing!");
		} else if (countL < countR) {
			System.out.println("{ is missing!");
		}

	}

	public void printPatternIf() {
		for (int i = 0; i < patternIf.size(); i++) {
			System.out.println(patternIf.get(i));
		}
	}

	public LinkedList<String> checkReturnFor() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("KEYWORD(FOR)")) {
				patternFor.add(la.getWords().get(i));
				if (la.getWords().get(i + 1).equals("LPAREN")) {
					patternFor.add(la.getWords().get(i + 1));
					if (la.getWords().get(i + 2).contains("KEYWORD")) {
						patternFor.add(la.getWords().get(i + 2));
						if (la.getWords().get(i + 3).contains("ID")) {
							patternFor.add(la.getWords().get(i + 3));
							if (la.getWords().get(i + 4).contains("OPERATOR")) {
								patternFor.add(la.getWords().get(i + 4));
								if (la.getWords().get(i + 5).contains("NUM")
										|| la.getWords().get(i + 5)
												.contains("ID")) {
									patternFor.add(la.getWords().get(i + 5));
									if (la.getWords().get(i + 6)
											.equals("SEMICOLON")) {
										patternFor
												.add(la.getWords().get(i + 6));
										if (la.getWords().get(i + 7)
												.contains("ID")) {
											patternFor.add(la.getWords().get(
													i + 7));
											if (la.getWords().get(i + 8)
													.contains("OPERATOR")) {
												patternFor.add(la.getWords()
														.get(i + 8));
												if (la.getWords().get(i + 9)
														.contains("NUM")
														|| la.getWords()
																.get(i + 9)
																.contains("ID")) {
													patternFor.add(la
															.getWords().get(
																	i + 9));
													if (la.getWords()
															.get(i + 10)
															.equals("SEMICOLON")) {
														patternFor.add(la
																.getWords()
																.get(i + 10));
														if (la.getWords()
																.get(i + 11)
																.contains("ID")) {
															patternFor
																	.add(la.getWords()
																			.get(i + 11));
															if (la.getWords()
																	.get(i + 12)
																	.contains(
																			"OPERATOR")) {
																patternFor
																		.add(la.getWords()
																				.get(i + 12));
																if (la.getWords()
																		.get(i + 13)
																		.equals("RPAREN")) {
																	patternFor
																			.add(la.getWords()
																					.get(i + 13));
																	if (la.getWords()
																			.get(i + 14)
																			.equals("LBRACE")) {
																		patternFor
																				.add(la.getWords()
																						.get(i + 14));

																	} else {
																		System.out
																				.println("Insert { after FOR loop to define FOR body.");
																	}
																} else {
																	System.out
																			.println("Insert ) after FOR conditions.");

																}
															} else {
																System.out
																		.println("Define operator ++ or -- in the third part of for loop.");

															}
														} else {
															System.out
																	.println("Define value before operator in the third part of FOR loop.");

														}
													} else {
														System.out
																.println("Insert ; after second part of FOR loop.");

													}
												} else {
													System.out
															.println("Define value after operator in the second part of FOR loop.");

												}
											} else {
												System.out
														.println("Define operator in the second part of FOR loop.");

											}
										} else {
											System.out
													.println("Define value before operator in the second part of FOR loop.");

										}
									} else {
										System.out
												.println("Insert ; after the first part of FOR loop.");

									}
								} else {
									System.out
											.println("Define integer value after operator in the first part of FOR loop.");

								}
							} else {
								System.out
										.println("Define operator after variable in the first part of FOR loop.");

							}
						} else {
							System.out
									.println("Define name of integer variable in the first part of FOR loop.");

						}
					} else {
						System.out
								.println("Define integer variable in the first part of FOR loop.");

					}
				} else {
					System.out.println("Add ( after FOR.");

				}
			}
		}
		return patternFor;
	}

	public LinkedList<String> checkWhile() {
		for (int i = 0; i < la.getWords().size(); i++) {
			try {
				if (la.getWords().get(i).equals("KEYWORD(WHILE)")) {
					patternWhile.add(la.getWords().get(i));
					if (la.getWords().get(i + 1).equals("LPAREN")) {
						patternWhile.add(la.getWords().get(i + 1));
						if (la.getWords().get(i + 2).contains("ID")
								|| la.getWords().get(i + 2).contains("NUM")) {
							patternWhile.add(la.getWords().get(i + 2));
							if (la.getWords().get(i + 3).contains("OPERATOR")) {
								patternWhile.add(la.getWords().get(i + 3));
								if (la.getWords().get(i + 4).contains("ID")
										|| la.getWords().get(i + 4)
												.contains("NUM")) {
									patternWhile.add(la.getWords().get(i + 4));
									if (la.getWords().get(i + 5)
											.equals("RPAREN")) {
										patternWhile.add(la.getWords().get(
												i + 5));
										if (la.getWords().get(i + 6)
												.equals("LBRACE")) {
											patternWhile.add(la.getWords().get(
													i + 6));

										} else {
											System.out.println("{ is missing!");
										}
									} else {
										System.out.println(") is missing!");
									}
								} else {
									System.out
											.println("ID or NUM  is missing!");
								}
							} else {
								System.out.println("OPERATOR is missing!");
							}
						} else {
							System.out.println("ID is missing!");
						}

					} else {
						System.out.println("( is missing!");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.print("Errorrrrr!!!");
			}
		}
		return patternWhile;
	}

	public void defineSemicolons() {
		for (int i = 0; i < la.getWords().size(); i++) {
			if (la.getWords().get(i).equals("LPAREN")
					|| la.getWords().get(i).equals("LBRACE")
					|| la.getWords().get(i).equals("OPERATOR(>)")
					|| la.getWords().get(i).equals("OPERATOR(<)")
					|| la.getWords().get(i).equals("OPERATOR(=)")      
					|| la.getWords().get(i).equals("POINT")
					|| la.getWords().get(i).equals("OPERATOR(+)")    
					|| la.getWords().get(i).equals("COMMA")
					|| la.getWords().get(i).equals("KEYWORD(FOR)")
					|| la.getWords().get(i).equals("KEYWORD(STRING)")
					|| la.getWords().get(i).equals("SEMICOLON")
					|| la.getWords().get(i).contains("KEYWORD(INT)")) {
				if (la.getWords().get(i + 1).equals("SEMICOLON")) {
					System.out.println("Remove ; after "
							+ la.getWords().get(i).toString() + " " + i);
				}
			}

			if (la.getWords().get(i).equals("KEYWORD(INT)")) {
				if (checkNewArray(i)) i=i+2;
				if (la.getWords().get(i + 1).contains("ID")) {
					if (la.getWords().get(i + 2).equals("OPERATOR(=)")) {
						if (la.getWords().get(i + 3).contains("NUM")
								|| la.getWords().get(i + 3).contains("ID")) {
							if (!la.getWords().get(i + 4).contains("SEMICOLON")) {
								int aba = 4;
								while (true) {
									if (la.getWords().get(i + aba)
											.contains("OPERATOR")) {
										if(!la.getWords().get(i + aba +1).contains("ID") && !la.getWords().get(i + aba +1).contains("NUM")){
											System.out.println("Mistake  " + (i+aba +1));
											break;
										}
									} else if((la.getWords().get(i + aba)
											.contains("NUM") || la.getWords().get(i + aba)
											.contains("ID"))){
										if(!la.getWords().get(i + aba +1).contains("OPERATOR") && !la.getWords().get(i + aba +1).equals("SEMICOLON")){
											System.out.println("Mistake");
											break;
										}
									} else if (!la.getWords().get(i + aba +1).equals("SEMICOLON")){
										break;
									}
									aba++;
								}
							}
						} 
					} else if (la.getWords().get(i + 2).equals("RPAREN")
							|| la.getWords().get(i + 2).equals("LPAREN")
							|| la.getWords().get(i + 2).equals("SEMICOLON")
							|| la.getWords().get(i + 2).equals("COMMA")) {
					} else {
						System.out.println("Add ;");
					}
				} else if(checkArray(i)){
					if (!la.getWords().get(i + 4).contains("SEMICOLON")) {
						System.out.println("add ; after " + la.getWords().get(i + 3) + " " + (i + 3));
					}
				}
			}

			if (la.getWords().get(i).equals("KEYWORD(STRING)")) {

				if (la.getWords().get(i + 1).contains("ID")) {

					if (la.getWords().get(i + 2).equals("OPERATOR(=)")) {

						if (la.getWords().get(i + 3).contains("STRING")
								|| la.getWords().get(i + 3)
										.equals("KEYWORD(NULL)")
								|| la.getWords().get(i + 3).contains("ID")) {

							if (!la.getWords().get(i + 4).contains("SEMICOLON")) {

								System.out.println("Add ; after "
										+ la.getWords().get(i + 3).toString());
							}
						} else {
							System.out
									.println("Define KEYWORD after operator.");
						}
					} else {
						System.out.println("Define OPERATOR.");
					}
				} else {
					System.out.println("Define variable.");
				}
			}

			if (la.getWords().get(i).equals("KEYWORD(RETURN)")) {
				if (la.getWords().get(i + 1).contains("ID")
						|| la.getWords().get(i + 1).contains("NUM")) {
					if (la.getWords().get(i + 2).contains("SEMICOLON")) {
					} else if (la.getWords().get(i + 2).equals("OPERATOR(+)")) {
						if (la.getWords().get(i + 3).contains("NUM")
								|| la.getWords().get(i + 3).contains("ID")) {
							if (la.getWords().get(i + 4).contains("SEMICOLON")) {
							} else {
								System.out
										.println("Add ; after RETURN expression.");
							}
						}
					} else {
						System.out.println("Add ; after RETURN expression.");
					}
				}
			}

			if (patternIf.size() != 0) {
				if (la.getWords().get(i).equals(patternIf.get(0))) {
					if (la.getWords().get(i + patternIf.size())
							.equals("SEMICOLON")) {
						System.out.println("Remove ; in IF statement");
					}
				}
			}

			if (patternElse.size() != 0) {
				if (la.getWords().get(i).equals(patternElse.get(0))) {
					if (la.getWords().get(i + patternElse.size())
							.equals("SEMICOLON")) {
						System.out.println("Remove ; in ELSE statement");
					}
				}
			}

			if (patternFor.size() != 0) {
				if (la.getWords().get(i).equals(patternFor.get(0))) {
					if (la.getWords().get(i + patternFor.size())
							.equals("SEMICOLON")) {
						System.out.println("Remove ; in FOR statement");
					}
				}
			}

			if (patternWhile.size() != 0) {
				if (la.getWords().get(i).equals(patternWhile.get(0))) {
					if (la.getWords().get(i + patternWhile.size())
							.equals("SEMICOLON")) {
						System.out.println("Remove ; after WHILE statement");
					}
				}
			}

			if (la.getWords().get(i).equals("RBRACE")
					&& la.getWords().get(i - 1).contains("STRING")) {
				System.out.println("Add ; after array defining.");
			}

			if ((la.getWords().get(i).equals("OPERATOR(++)") || la.getWords()
					.get(i).equals("OPERATOR(--)"))
					&& (!la.getWords().get(i + 1).equals("SEMICOLON") && !la
							.getWords().get(i - 2).equals("SEMICOLON"))) {
				System.out.println("Add ; after "
						+ la.getWords().get(i).toString());
			}

		}
	}
}
