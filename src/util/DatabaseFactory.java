package util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseFactory {
    private String path;

    public DatabaseFactory(String path) {
        this.path = path;
    }

    public void checkSQLiteDatabase() {
        File file = new File (path);

        if (file.exists()) {
            System.out.print("Conectando ao banco de dados existente.");
        }
        else{
            // crie o diretório e o db para o projeto
            boolean dir = new File("src/db/").mkdir();
            createProjectsTable();
            createTasksTable();
        }
    }

    public void createProjectsTable() {
        String sql = "CREATE TABLE \"projects\" (" +
                "\"id\"	INTEGER NOT NULL, " +
                "\"name\" TEXT NOT NULL UNIQUE, "+
                "\"description\" TEXT, " +
                "\"createdAt\" TEXT NOT NULL, " +
                "\"updatedAt\" TEXT NOT NULL, " +
                "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                ")";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar a tabela projects ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void createTasksTable() {
        String sql = "CREATE TABLE \"tasks\" (" +
                "\"id\"	INTEGER NOT NULL, " +
                "\"projectId\"	INTEGER NOT NULL, " +
                "\"name\" TEXT NOT NULL, "+
                "\"description\" TEXT, " +
                "\"notes\" TEXT, " +
                "\"status\" NUMERIC NOT NULL, " +
                "\"deadline\" TEXT NOT NULL, " +
                "\"createdAt\" TEXT NOT NULL, " +
                "\"updatedAt\" TEXT NOT NULL, " +
                "FOREIGN KEY (\"projectId\") REFERENCES \"projects\"(\"id\") " +
                "ON DELETE CASCADE, " +
                "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                ")";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar a tabela tasks ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
}
