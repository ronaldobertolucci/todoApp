package br.com.bertolucci.todoapp.main;

import br.com.bertolucci.todoapp.util.DatabaseFactory;
import br.com.bertolucci.todoapp.view.MainScreen;

public class Main {
    public static void main(String[] args) {
        // Create db
        DatabaseFactory databaseFactory = new DatabaseFactory();
        databaseFactory.checkSQLiteDatabase();

        MainScreen mainScreen = new MainScreen();
        mainScreen.initializeScreen();
    }
}
