package view;

import controller.TaskController;
import model.Task;
import util.TaskTableModel;

import javax.swing.*;
import java.awt.event.*;

public class DeleteConfirmationDialogScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel JLabelConfirmationMessage;
    private Task task;
    private TaskTableModel taskModel;
    private TaskController taskController;

    public DeleteConfirmationDialogScreen(Task task, TaskTableModel taskModel) {
        this.setTask(task);
        this.setTaskModel(taskModel);
        this.taskController = new TaskController();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        dialogInitialization();
    }

    private void onOK() {
        // add your code here
        taskController.removeById(task.getId());
        taskModel.getTasks().remove(task);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void dialogInitialization() {
        this.setContentPane(contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.setLocation(200,200);
        this.pack();
        this.setVisible(true);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTaskModel(TaskTableModel taskModel) {
        this.taskModel = taskModel;
    }
}
