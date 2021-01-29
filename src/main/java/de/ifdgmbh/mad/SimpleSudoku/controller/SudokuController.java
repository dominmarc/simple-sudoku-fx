package de.ifdgmbh.mad.SimpleSudoku.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
	private Button newVal;
	List<Button> felder = new ArrayList<>();

	public void initialize() {
		for (Node c : mypane.getChildren()) {
			if (c instanceof Button) {
				felder.add((Button) c);
			}
		}
//		int i = 1;
//		for (Button b : felder) {
//			b.setText(i + "");
//			i++;
//		}
		fillFields();
	}

	public void fillFields() {
		SecureRandom rand = new SecureRandom();
		int[][] array = new int[7][7];
		boolean feld_valid = false;
		List<Integer> integers = null;
		int k = 0;
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
					//System.out.print(array[x][y] + " ");
					felder.get(k).setText(array[x][y]+"");;
					k++;
				}
			}
	}

	public void ExitClicked() {
		Stage temp = (Stage) mypane.getScene().getWindow();
		temp.close();
		System.exit(0);
	}
	
	public void newValClick() {
		fillFields();
	}
	
	// ROW 1
	public void Feld1Click() {

	}

	public void Feld2Click() {

	}

	public void Feld3Click() {

	}

	public void Feld4Click() {

	}

	public void Feld5Click() {

	}

	public void Feld6Click() {

	}

	// ROW 2
	public void Feld7Click() {

	}

	public void Feld8Click() {

	}

	public void Feld9Click() {

	}

	public void Feld10Click() {

	}

	public void Feld11Click() {

	}

	public void Feld12Click() {

	}

	// ROW 3
	public void Feld13Click() {

	}

	public void Feld14Click() {

	}

	public void Feld15Click() {

	}

	public void Feld16Click() {

	}

	public void Feld17Click() {

	}

	public void Feld18Click() {

	}

	// ROW 4
	public void Feld19Click() {

	}

	public void Feld20Click() {

	}

	public void Feld21Click() {

	}

	public void Feld22Click() {

	}

	public void Feld23Click() {

	}

	public void Feld24Click() {

	}

	// ROW 5
	public void Feld25Click() {

	}

	public void Feld26Click() {

	}

	public void Feld27Click() {

	}

	public void Feld28Click() {

	}

	public void Feld29Click() {

	}

	public void Feld30Click() {

	}

	// ROW 6
	public void Feld31Click() {

	}

	public void Feld32Click() {

	}

	public void Feld33Click() {

	}

	public void Feld34Click() {

	}

	public void Feld35Click() {

	}

	public void Feld36Click() {

	}
}
