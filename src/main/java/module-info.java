module SimpleSudoku {
	exports de.ifdgmbh.mad.SimpleSudoku.controller;
	exports de.ifdgmbh.mad.SimpleSudoku.main;
	
	requires transitive javafx.graphics;
	requires transitive java.sql;
	requires javafx.base;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	
	
	opens de.ifdgmbh.mad.SimpleSudoku.controller;
}