package view;

import controller.TaskController;
import model.Task;
import util.TaskTableModel;

import javax.swing.*;
import java.awt.*;
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
        taskController = new TaskController();
        initListeners();
        dialogInitialization();
    }

    private void initListeners() {
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
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
        centreWindow(this);
        this.setVisible(true);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTaskModel(TaskTableModel taskModel) {
        this.taskModel = taskModel;
    }

    public void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
