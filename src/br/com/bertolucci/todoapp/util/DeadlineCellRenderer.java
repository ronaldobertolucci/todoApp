package br.com.bertolucci.todoapp.util;

import br.com.bertolucci.todoapp.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineCellRenderer extends DefaultTableCellRenderer {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate today = LocalDate.now();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
                                                                    hasFocus, row, column);
        label.setHorizontalAlignment(CENTER);
        TaskTableModel taskModel = (TaskTableModel) table.getModel();
        Task task = taskModel.getTasks().get(row);

        LocalDate deadline = LocalDate.parse(task.getDeadline(), formatter);
        if (!today.isAfter(deadline)) {
            label.setBackground(new Color(179, 243, 202));
        } else {
            label.setBackground(new Color(255, 205, 205, 255));
        }
        return label;
    }
}
