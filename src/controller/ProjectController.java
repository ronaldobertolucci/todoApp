package controller;

import model.Project;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    private String today = LocalDate.now().toString();

    public void save(Project project) {
        String sql = "INSERT INTO projects (" +
                "name," +
                "description," +
                "createdAt," +
                "updatedAt) VALUES (?,?,?,?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setString(3, today);
            statement.setString(4, today);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o projeto ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET " +
                "name = ?, " +
                "description = ?, " +
                "updatedAt = ? " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setString(3, today);
            statement.setInt(4, project.getId());
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o projeto ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar o projeto ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Project> projects = new ArrayList<Project>();

        try {
            conn = ConnectionFactory.getConnection("src/db/tododb.db");
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getString("createdAt"));
                project.setUpdatedAt(resultSet.getString("updatedAt"));

                projects.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar os projetos ", e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        return projects;
    }
}
