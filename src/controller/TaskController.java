package controller;

import model.Task;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    private String today = LocalDate.now().toString();

    public void save(Task task) {
        String sql = "INSERT INTO tasks (" +
                "projectId," +
                "name," +
                "description," +
                "notes," +
                "status," +
                "deadline," +
                "createdAt," +
                "updatedAt) VALUES (?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setInt(1, task.getProjectId());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setInt(5, task.getStatus());
            statement.setString(6, task.getDeadline());
            statement.setString(7, today);
            statement.setString(8, today);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a tarefa ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET " +
                "projectId = ?, " +
                "name = ?, " +
                "description = ?, " +
                "notes = ?, " +
                "status = ?, " +
                "deadline = ?, " +
                "updatedAt = ? " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setInt(1, task.getProjectId());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setInt(5, task.getStatus());
            statement.setString(6, task.getDeadline());
            statement.setString(7, today);
            statement.setInt(8, task.getId());
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar a tarefa ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql); // protejer de SQL Injection
            statement.setInt(1, taskId); // trocar o primeiro ? por taskId
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar a tarefa ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int projectId) {
        String sql = "SELECT * FROM tasks WHERE projectId = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null; // classe que representa a resposta do SELECT
        List<Task> tasks = new ArrayList<Task>();

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setInt(1, projectId);
            resultSet = statement.executeQuery();

            while(resultSet.next()) { // enquanto houver um próximo
                Task task = new Task();

                task.setId(resultSet.getInt("id"));
                task.setProjectId(resultSet.getInt("projectId"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setStatus(resultSet.getInt("status"));
                task.setDeadline(resultSet.getString("deadline"));
                task.setCreatedAt(resultSet.getString("createdAt"));
                task.setUpdatedAt(resultSet.getString("updatedAt"));

                tasks.add(task);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a tarefa ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        return tasks;
    }
}
