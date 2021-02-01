package de.ifdgmbh.mad.SimpleSudoku.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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
	private Button newVal, giveHint;
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
		//shuffle columns and rows
		//shuffle row:1,2 & column 1,2 and row 5,6 & column 5,6
		//row 1,2
		int[] temp = array[2];
		array[2]=array[1];
		array[1]=temp;
		//column 1,2
		for (int i=1;i<=6;i++) {
			int t = array[1][i];
			array[1][i]=array[2][i];
			array[2][i]=t;
		}
		//row 5,6
		temp = array[6];
		array[6]=array[5];
		array[5]=temp;
		//column 5,6
		for (int i=1;i<=6;i++) {
			int t = array[4][i];
			array[4][i]=array[5][i];
			array[5][i]=t;
		}
		//set button text
		int k = 0;
		for (int y=1;y<=6;y++) {
			for (int x=1;x<=6;x++) {
				felder.get(k).setText(array[x][y]+"");
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

	public void ExitClicked() {
		Stage temp = (Stage) mypane.getScene().getWindow();
		temp.close();
		System.exit(0);
	}

	public void newValClick() {
		fillAllFields();
		deleteSomeFields();
		running = true;
	}

	public void giveHintClicked() {
		if (hints<=0) {
			myAlert = new Alert(Alert.AlertType.WARNING,"Please continue!");
			myAlert.setHeaderText("No hints left!");
			myAlert.show();
			return;
		}
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
				hints-=1;
				giveHint.setText("Hints left: "+hints);
				next = false;
				return;
			}
		}
	}

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

	public int checkEnd() {
		if (mistakes>3) {
			return 1;
		}
		for (Button o:felder) {
			if (o.getText().isBlank()) {
				return 2;
			}
		}
		return 3;
	}

	public void feldInput(int feldNum) {
		if (!running) {
			myAlert = new Alert(Alert.AlertType.WARNING,"Please start the game!");
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
		if (temp==1) {
			myAlert = new Alert(Alert.AlertType.WARNING,"Thank you for playing!");
			myAlert.setHeaderText("Too many mistakes!");
			myAlert.show();
			resetGame();
		}else if (temp==2) {
			//nothing
		}else if (temp==3) {
			myAlert = new Alert(Alert.AlertType.INFORMATION,"Game is over!"+"\n"+"Thank you for playing!");
			myAlert.setHeaderText("You made "+mistakes+" mistakes!");
			myAlert.show();
			resetGame();
		}
	}
	
	public void resetGame() {
		mistakes = 0;
		mistakesLabel.setText("Mistakes: "+mistakes);
		running = false;
		hints = 3;
		giveHint.setText("Hints left: "+hints);
	}
	
	// ROW 1
	public void Feld1Click() {
		feldInput(1);
	}

	public void Feld2Click() {
		feldInput(2);
	}

	public void Feld3Click() {
		feldInput(3);
	}

	public void Feld4Click() {
		feldInput(4);
	}

	public void Feld5Click() {
		feldInput(5);
	}

	public void Feld6Click() {
		feldInput(6);
	}

	// ROW 2
	public void Feld7Click() {
		feldInput(7);
	}

	public void Feld8Click() {
		feldInput(8);
	}

	public void Feld9Click() {
		feldInput(9);
	}

	public void Feld10Click() {
		feldInput(10);
	}

	public void Feld11Click() {
		feldInput(11);
	}

	public void Feld12Click() {
		feldInput(12);
	}

	// ROW 3
	public void Feld13Click() {
		feldInput(13);
	}

	public void Feld14Click() {
		feldInput(14);
	}

	public void Feld15Click() {
		feldInput(15);
	}

	public void Feld16Click() {
		feldInput(16);
	}

	public void Feld17Click() {
		feldInput(17);
	}

	public void Feld18Click() {
		feldInput(18);
	}

	// ROW 4
	public void Feld19Click() {
		feldInput(19);
	}

	public void Feld20Click() {
		feldInput(20);
	}

	public void Feld21Click() {
		feldInput(21);
	}

	public void Feld22Click() {
		feldInput(22);
	}

	public void Feld23Click() {
		feldInput(23);
	}

	public void Feld24Click() {
		feldInput(24);
	}

	// ROW 5
	public void Feld25Click() {
		feldInput(25);
	}

	public void Feld26Click() {
		feldInput(26);
	}

	public void Feld27Click() {
		feldInput(27);
	}

	public void Feld28Click() {
		feldInput(28);
	}

	public void Feld29Click() {
		feldInput(29);
	}

	public void Feld30Click() {
		feldInput(30);
	}

	// ROW 6
	public void Feld31Click() {
		feldInput(31);
	}

	public void Feld32Click() {
		feldInput(32);
	}

	public void Feld33Click() {
		feldInput(33);
	}

	public void Feld34Click() {
		feldInput(34);
	}

	public void Feld35Click() {
		feldInput(35);
	}

	public void Feld36Click() {
		feldInput(36);
	}
}
