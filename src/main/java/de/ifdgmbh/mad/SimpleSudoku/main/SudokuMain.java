package de.ifdgmbh.mad.SimpleSudoku.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SudokuMain extends Application{
	private double xOffset = 0;
	private double yOffset = 0;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Sudoku.fxml"));
		Scene myScene = new Scene(root);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		myScene.setFill(Color.TRANSPARENT);
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});

		primaryStage.setScene(myScene);
		primaryStage.setTitle("SimpleSudoku");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
