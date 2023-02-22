package br.com.bertolucci.todoapp.util;

import br.com.bertolucci.todoapp.model.Task;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// define um model padrão para a JTable extendendo AbstractTableModel
public class TaskTableModel extends AbstractTableModel {
    private String[] columns = {"Nome", "Descrição", "Prazo", "Concluída", "Editar", "Excluir"};
    private List<Task> tasks = new ArrayList<Task>();

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String[] getColumns() {
        return columns;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return tasks.get(rowIndex).getName();
            case 1:
                return tasks.get(rowIndex).getDescription();
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(tasks.get(rowIndex).getDeadline(), formatter);
                return date.getDayOfMonth()+ "/" + date.getMonthValue() + "/" + date.getYear();
            case 3:
                if (tasks.get(rowIndex).getStatus() == 1) {
                    return true;
                } else {
                    return false;
                }
            case 4:
            case 5:
                return "";
            default:
                return "Dados não encontrados";
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (tasks.isEmpty()) {
            return Object.class;
        }
        return this.getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value.equals(true)) {
            tasks.get(rowIndex).setStatus(1);
        } else {
            tasks.get(rowIndex).setStatus(0);
        }
    }
}
