module ProjectLab {
	opens main;
	opens models;
	opens views;
	opens controllers;
	opens database;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
}