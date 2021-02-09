package de.ifdgmbh.mad.SimpleSudoku.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SudokuController {
	@FXML
	private AnchorPane mypane;
	@FXML
	private Button Feld1, Feld2, Feld3, Feld4, Feld5, Feld6;
	@FXML
	private Button Feld7, Feld8, Feld9, Feld10, Feld11, Feld12;
	@FXML
	private Button Feld13, Feld14, Feld15, Feld16, Feld17, Feld18;
	@FXML
	private Button Feld19, Feld20, Feld21, Feld22, Feld23, Feld24;
	@FXML
	private Button Feld25, Feld26, Feld27, Feld28, Feld29, Feld30;
	@FXML
	private Button Feld31, Feld32, Feld33, Feld34, Feld35, Feld36;
	@FXML
	private Button Exit;
	@FXML
	private Button newVal, giveHint, solveGame;
	@FXML
	private TextField txtInput;
	@FXML
	private Label mistakesLabel;

	List<Button> felder = new ArrayList<>();
	int[][] array = new int[7][7];
	Alert myAlert;
	int mistakes = 0;
	int hints = 3;
	boolean running = false;

	public void initialize() {
		for (Node c : mypane.getChildren()) {
			if (c instanceof Button) {
				felder.add((Button) c);
			}
		}
	}

	public void fillAllFields() {
		SecureRandom rand = new SecureRandom();
		List<Integer> integers = null;
		// create feld
		for (int y = 1; y <= 6; y++) {
			if (y == 1) {
				// first line
				integers = new ArrayList<>();
				while (integers.size() < 6) {
					final int zahl = rand.nextInt(6) + 1;
					if (!integers.contains(zahl)) {
						integers.add(zahl);
					}
				}
			} else if (y == 4) {
				// shift by 1
				ArrayList<Integer> temp = new ArrayList<>();
				for (int z = 1; z <= 6; z++) {
					if (z >= 6) {
						temp.add(integers.get(z - 6));
					} else {
						temp.add(integers.get(z));
					}
				}
				integers.clear();
				integers.addAll(temp);
			} else {
				// shift by 2
				ArrayList<Integer> temp = new ArrayList<>();
				for (int z = 1; z <= 6; z++) {
					if (z >= 5) {
						temp.add(integers.get(z - 5));
					} else {
						temp.add(integers.get(z + 1));
					}
				}
				integers.clear();
				integers.addAll(temp);
			}
			for (int x = 1; x <= 6; x++) {
				array[x][y] = integers.get(x - 1);
			}
		}
		// shuffle columns and rows
		// shuffle row:1,2 & column 1,2 and row 5,6 & column 5,6
		// row 1,2
		int[] temp = array[2];
		array[2] = array[1];
		array[1] = temp;
		// column 1,2
		for (int i = 1; i <= 6; i++) {
			int t = array[1][i];
			array[1][i] = array[2][i];
			array[2][i] = t;
		}
		// row 5,6
		temp = array[6];
		array[6] = array[5];
		array[5] = temp;
		// column 5,6
		for (int i = 1; i <= 6; i++) {
			int t = array[4][i];
			array[4][i] = array[5][i];
			array[5][i] = t;
		}
		// set button text
		int k = 0;
		for (int y = 1; y <= 6; y++) {
			for (int x = 1; x <= 6; x++) {
				felder.get(k).setText(array[x][y] + "");
				k++;
			}
		}
	}

	public void deleteSomeFields() {
		SecureRandom rand = new SecureRandom();
		List<Integer> randInts = new ArrayList<>();
		int tempRand;
		for (int y = 1; y <= 6; y++) {
			tempRand = rand.nextInt(2) + 3;
			while (randInts.size() < tempRand) {
				final int zahl = rand.nextInt(6) + 1;
				if (!randInts.contains(zahl)) {
					randInts.add(zahl);
				}
			}
			for (Integer i : randInts) {
				felder.get((i + ((y - 1) * 6)) - 1).setText("");
			}
			randInts.clear();
		}
	}

	/**
	 * Exit game
	 */
	public void ExitClicked() {
		Stage temp = (Stage) mypane.getScene().getWindow();
		temp.close();
		System.exit(0);
	}

	/**
	 * Start game
	 */
	public void newValClick() {
		do {
			fillAllFields();
			deleteSomeFields();
		} while (!solveGame());
		running = true;
	}

	/**
	 * Show a hidden number
	 */
	public void giveHintClicked() {
		if (hints <= 0) {
			myAlert = new Alert(Alert.AlertType.WARNING, "Please continue!");
			myAlert.setHeaderText("No hints left!");
			myAlert.show();
			return;
		}
		if (!running)
			return;
		SecureRandom rand = new SecureRandom();
		boolean next = true;
		while (next) {
			int tempRand = rand.nextInt(35);
			if (felder.get(tempRand).getText().isBlank()) {
				int x = ((tempRand + 1) % 6);
				int y = ((tempRand + 1) / 6) + 1;
				if (x == 0) {
					x = 6;
					y -= 1;
				}
				felder.get(tempRand).setText("" + array[x][y]);
				hints -= 1;
				giveHint.setText("Hints left: " + hints);
				next = false;
				return;
			}
		}
	}

	/**
	 * Input check
	 * 
	 * @return boolean right or wrong input format
	 */
	public boolean checkInput() {
		if (txtInput.getText().length() > 1) {
			return false;
		}
		try {
			int x = Integer.valueOf(txtInput.getText());
			if (x > 0 && x < 7) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkSolution(int feldNum, int value) {
		int x = ((feldNum) % 6);
		int y = ((feldNum) / 6) + 1;
		if (x == 0) {
			x = 6;
			y -= 1;
		}
		int solution = array[x][y];
		if (solution == value) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return 1 to many mistakes
	 *         </p>
	 *         2 game is still not at the end
	 *         </p>
	 *         3 game end
	 */
	public int checkEnd() {
		if (mistakes > 3) {
			return 1;
		}
		for (Button o : felder) {
			if (o.getText().isBlank()) {
				return 2;
			}
		}
		return 3;
	}

	public int checkEnd(int[][] myField) {
		int x = 1;
		int y = 1;
		if (mistakes > 3) {
			return 1;
		}
		for (y = 1; y < 7; y++) {
			for (x = 1; x < 7; x++) {
				if (myField[x][y] == 0) {
					return 2;
				}
			}
		}

		return 3;
	}

	/**
	 * Inputs values 1 to 6 (depending on chosen number) to the corresponding field
	 * 
	 * @param feldNum Number of the corresponding field
	 */
	public void feldInput(int feldNum) {
		if (!running) {
			myAlert = new Alert(Alert.AlertType.WARNING, "Please start the game!");
			myAlert.show();
			return;
		}
		if (checkInput()) {
			felder.get(feldNum - 1).setText(txtInput.getText());
		} else {
			myAlert = new Alert(Alert.AlertType.WARNING, "Wrong input value!");
			myAlert.show();
			return;
		}
		if (checkSolution(feldNum, Integer.valueOf(felder.get(feldNum - 1).getText()))) {

		} else {
			felder.get(feldNum - 1).setText("");
			mistakes++;
			mistakesLabel.setText("Mistakes: " + mistakes);
		}
		int temp = checkEnd();
		if (temp == 1) {
			myAlert = new Alert(Alert.AlertType.WARNING, "Thank you for playing!");
			myAlert.setHeaderText("Too many mistakes!");
			myAlert.show();
			resetGame();
		} else if (temp == 2) {
			// nothing
		} else if (temp == 3) {
			myAlert = new Alert(Alert.AlertType.INFORMATION, "Game is over!" + "\n" + "Thank you for playing!");
			myAlert.setHeaderText("You made " + mistakes + " mistakes!");
			myAlert.show();
			resetGame();
		}
	}

	public void resetGame() {
		mistakes = 0;
		mistakesLabel.setText("Mistakes: " + mistakes);
		running = false;
		hints = 3;
		giveHint.setText("Hints left: " + hints);
	}

	public void solveGameClick() {
		if (solveGame()) {
			makeSolveMoves();
		} else {
			System.out.println("A problem occured!");
		}
	}

	/**
	 * Starts a new thread that makes a solving move every 1 second  
	 */
	public void makeSolveMoves() {
		Thread moves = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(()->{newVal.setDisable(true); giveHint.setDisable(true);});
				int[][] current_field = new int[7][7];
				ArrayList<Integer> myInts = new ArrayList<>();
				myInts.add(1);
				myInts.add(2);
				myInts.add(3);
				myInts.add(4);
				myInts.add(5);
				myInts.add(6);
				int x = 1;
				int y = 1;
				for (Button b : felder) {
					if (x >= 7) {
						x = 1;
						y++;
					}
					if (b.getText().isBlank()) {
						current_field[x][y] = 0;
					} else {
						current_field[x][y] = Integer.valueOf(b.getText());
					}
					x++;
				}

				int counter = 5;
				while (checkEnd(current_field) != 3 && counter > 0) {
					counter--;
					for (int i = 1; i < 7; i++) {
						for (int z = 1; z < 7; z++) {
							ArrayList<Integer> tempList = new ArrayList<>();
							tempList.addAll(myInts);
							if (current_field[z][i] == 0) {
								// check row (x)
								for (int k = 1; k < 7; k++) {
									if (current_field[k][i] != 0) {
										tempList.remove((Object) current_field[k][i]);
									}
								}

								// check column (y)
								for (int k = 1; k < 7; k++) {
									if (current_field[z][k] != 0) {
										tempList.remove((Object) current_field[z][k]);
									}
								}
								// check block
								int k = 1; // y
								int l = 1; // x
								if (z < 3) {
									if (i < 4) {
										// 1.
									} else if (i > 3) {
										// 4.
										k = 4;
									}
								} else if (z > 2 && z < 5) {
									if (i < 4) {
										// 2.
										l = 3;
									} else if (i > 3) {
										// 5.
										k = 4;
										l = 3;
									}
								} else if (z > 4) {
									if (i < 4) {
										// 3.
										l = 5;
									} else if (i > 3) {
										// 6.
										k = 4;
										l = 5;
									}
								}
								int tempk = k + 3;
								int templ = l + 2;
								for (k = tempk - 3; k < tempk; k++) {
									for (l = templ - 2; l < templ; l++) {
										if (current_field[l][k] != 0) {
											if (tempList.contains(current_field[l][k]))
												tempList.remove((Object) current_field[l][k]);
										}
									}
								}

								// assign
								if (tempList.size() == 1) {
									current_field[z][i] = tempList.get(0);
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									updateField(current_field);
								}
							}
						}
					}
				}
				Platform.runLater(()->{giveHint.setDisable(false); newVal.setDisable(false);});
				running = false;
			}
		});
		moves.start();
	}

	public boolean solveGame() {
		int[][] current_field = new int[7][7];
		ArrayList<Integer> myInts = new ArrayList<>();
		myInts.add(1);
		myInts.add(2);
		myInts.add(3);
		myInts.add(4);
		myInts.add(5);
		myInts.add(6);
		int x = 1;
		int y = 1;
		for (Button b : felder) {
			if (x >= 7) {
				x = 1;
				y++;
			}
			if (b.getText().isBlank()) {
				current_field[x][y] = 0;
			} else {
				current_field[x][y] = Integer.valueOf(b.getText());
			}
			x++;
		}

		int counter = 5;
		while (checkEnd(current_field) != 3 && counter > 0) {
			counter--;
			for (int i = 1; i < 7; i++) {
				for (int z = 1; z < 7; z++) {
					ArrayList<Integer> tempList = new ArrayList<>();
					tempList.addAll(myInts);
					if (current_field[z][i] == 0) {
						// check row (x)
						for (int k = 1; k < 7; k++) {
							if (current_field[k][i] != 0) {
								tempList.remove((Object) current_field[k][i]);
							}
						}

						// check column (y)
						for (int k = 1; k < 7; k++) {
							if (current_field[z][k] != 0) {
								tempList.remove((Object) current_field[z][k]);
							}
						}
						// check block
						int k = 1; // y
						int l = 1; // x
						if (z < 3) {
							if (i < 4) {
								// 1.
							} else if (i > 3) {
								// 4.
								k = 4;
							}
						} else if (z > 2 && z < 5) {
							if (i < 4) {
								// 2.
								l = 3;
							} else if (i > 3) {
								// 5.
								k = 4;
								l = 3;
							}
						} else if (z > 4) {
							if (i < 4) {
								// 3.
								l = 5;
							} else if (i > 3) {
								// 6.
								k = 4;
								l = 5;
							}
						}
						int tempk = k + 3;
						int templ = l + 2;
						for (k = tempk - 3; k < tempk; k++) {
							for (l = templ - 2; l < templ; l++) {
								if (current_field[l][k] != 0) {
									if (tempList.contains(current_field[l][k]))
										tempList.remove((Object) current_field[l][k]);
								}
							}
						}

						// assign
						if (tempList.size() == 1) {
							current_field[z][i] = tempList.get(0);
						}
					}
				}
			}
		}

		if (checkEnd(current_field) == 3) {
			return true;
		} else if (checkEnd(current_field) == 2) {
			return false;
		}
		return false;
	}

	public void updateField(int[][] field) {
		int o = 0;
		for (int s = 1; s <= 6; s++) {
			for (int j = 1; j <= 6; j++) {
				if (field[j][s] == 0) {
				} else {
					final int x = j;
					final int y = s;
					final int g = o;
					Platform.runLater(() -> felder.get(g).setText(field[x][y] + ""));
				}
				o++;
			}
		}
	}

	// ROW 1
	public void Feld1Click() {
		if (!running)
			return;
		feldInput(1);
	}

	public void Feld2Click() {
		if (!running)
			return;
		feldInput(2);
	}

	public void Feld3Click() {
		if (!running)
			return;
		feldInput(3);
	}

	public void Feld4Click() {
		if (!running)
			return;
		feldInput(4);
	}

	public void Feld5Click() {
		if (!running)
			return;
		feldInput(5);
	}

	public void Feld6Click() {
		if (!running)
			return;
		feldInput(6);
	}

	// ROW 2
	public void Feld7Click() {
		if (!running)
			return;
		feldInput(7);
	}

	public void Feld8Click() {
		if (!running)
			return;
		feldInput(8);
	}

	public void Feld9Click() {
		if (!running)
			return;
		feldInput(9);
	}

	public void Feld10Click() {
		if (!running)
			return;
		feldInput(10);
	}

	public void Feld11Click() {
		if (!running)
			return;
		feldInput(11);
	}

	public void Feld12Click() {
		if (!running)
			return;
		feldInput(12);
	}

	// ROW 3
	public void Feld13Click() {
		if (!running)
			return;
		feldInput(13);
	}

	public void Feld14Click() {
		if (!running)
			return;
		feldInput(14);
	}

	public void Feld15Click() {
		if (!running)
			return;
		feldInput(15);
	}

	public void Feld16Click() {
		if (!running)
			return;
		feldInput(16);
	}

	public void Feld17Click() {
		if (!running)
			return;
		feldInput(17);
	}

	public void Feld18Click() {
		if (!running)
			return;
		feldInput(18);
	}

	// ROW 4
	public void Feld19Click() {
		if (!running)
			return;
		feldInput(19);
	}

	public void Feld20Click() {
		if (!running)
			return;
		feldInput(20);
	}

	public void Feld21Click() {
		if (!running)
			return;
		feldInput(21);
	}

	public void Feld22Click() {
		if (!running)
			return;
		feldInput(22);
	}

	public void Feld23Click() {
		if (!running)
			return;
		feldInput(23);
	}

	public void Feld24Click() {
		if (!running)
			return;
		feldInput(24);
	}

	// ROW 5
	public void Feld25Click() {
		if (!running)
			return;
		feldInput(25);
	}

	public void Feld26Click() {
		if (!running)
			return;
		feldInput(26);
	}

	public void Feld27Click() {
		if (!running)
			return;
		feldInput(27);
	}

	public void Feld28Click() {
		if (!running)
			return;
		feldInput(28);
	}

	public void Feld29Click() {
		if (!running)
			return;
		feldInput(29);
	}

	public void Feld30Click() {
		if (!running)
			return;
		feldInput(30);
	}

	// ROW 6
	public void Feld31Click() {
		if (!running)
			return;
		feldInput(31);
	}

	public void Feld32Click() {
		if (!running)
			return;
		feldInput(32);
	}

	public void Feld33Click() {
		if (!running)
			return;
		feldInput(33);
	}

	public void Feld34Click() {
		if (!running)
			return;
		feldInput(34);
	}

	public void Feld35Click() {
		if (!running)
			return;
		feldInput(35);
	}

	public void Feld36Click() {
		if (!running)
			return;
		feldInput(36);
	}
}
