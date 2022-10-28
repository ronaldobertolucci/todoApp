package main;

import util.DatabaseFactory;
import view.MainScreen;

public class Main {
    public static void main(String[] args) {
        // Create db
        DatabaseFactory databaseFactory = new DatabaseFactory("src/db/tododb.db");
        databaseFactory.checkSQLiteDatabase();

        MainScreen mainScreen = new MainScreen();
        mainScreen.initializeScreen();
    }
}
