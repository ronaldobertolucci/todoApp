package view;

import controller.TaskController;
import model.Project;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TaskDialogScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField nameTextField;
    private JTextField deadlineTextField;
    private JTextArea descriptionTextArea;
    private JTextArea notesTextArea;
    private JPanel JPanelFooter;
    private JPanel JPanelButtonOK;
    private JPanel JPanelMain;
    private JPanel JPanelHeader;
    private JLabel JLabelHeaderTitle;
    private JLabel JLabelHeaderIcon;
    private JPanel JPanelFields;
    private JLabel JLabelNameTitle;
    private JLabel JLabelDeadlineTitle;
    private JLabel JLabelDescriptionTitle;
    private JLabel JLabelNotesTitle;
    private JPanel JPanelNotes;
    private JPanel JPanelDescription;
    private JLabel JLabelBlankNameMessage;
    private JLabel JLabelBlankDeadlineMessage;
    private JLabel JLabelWrongFormatDeadlineMessage;
    private JLabel JLabelWrongDateDeadlineMessage;
    private JLabel JLabelEditTaskTitle;
    private TaskController taskController;
    private Project project;
    private DateTimeFormatter formatter;
    private Task task;

    public TaskDialogScreen(Project project) {
        taskController = new TaskController();
        this.setProject(project);
        showCorrectTitles();
        initSaveListener();
        dialogInitialization();
    }

    public TaskDialogScreen(Task task) {
        taskController = new TaskController();
        this.setTask(task);
        this.nameTextField.setText(task.getName());
        this.descriptionTextArea.setText(task.getDescription());
        this.notesTextArea.setText(task.getNotes());
        this.deadlineTextField.setText(getBrazilianStringDateFormat(task.getDeadline()));

        showCorrectTitles();
        initUpdateListener();
        dialogInitialization();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private void showCorrectTitles() {
        if (this.task != null) {
            this.JLabelHeaderTitle.setVisible(false);
            this.JLabelEditTaskTitle.setVisible(true);
            this.JLabelHeaderIcon.setVisible(false);
        } else {
            this.JLabelHeaderTitle.setVisible(true);
            this.JLabelEditTaskTitle.setVisible(false);
            this.JLabelHeaderIcon.setVisible(true);
        }
    }
    private void initSaveListener() {
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void initUpdateListener() {
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onUpdateOK();
            }
        });
    }

    private void dialogInitialization() {
        this.setContentPane(contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.pack();
        centreWindow(this);
        this.setVisible(true);
    }

    private void onOK() {
        clearMessages();
        if (validateDeadline() & validateName()) {
            try {
                Task task = new Task();
                task.setProjectId(project.getId());
                task.setName(nameTextField.getText());
                task.setDescription(descriptionTextArea.getText());
                task.setNotes(notesTextArea.getText());
                task.setDeadline(getDefaultStringDateFormat(deadlineTextField.getText()));
                task.setStatus(0);
                taskController.save(task);
                JOptionPane.showMessageDialog(rootPane, "Tarefa salva com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
            this.dispose();
        }
    }

    private void onUpdateOK() {
        clearMessages();
        if (validateDeadline() & validateName()) {
            try {
                this.task.setName(nameTextField.getText());
                this.task.setDescription(descriptionTextArea.getText());
                this.task.setNotes(notesTextArea.getText());
                this.task.setDeadline(getDefaultStringDateFormat(deadlineTextField.getText()));
                taskController.update(this.task);
                JOptionPane.showMessageDialog(rootPane, "Tarefa atualizada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
            this.dispose();
        }
    }

    private boolean validateName() {
        if (!Objects.equals(nameTextField.getText(), "")) {
            return true;
        } else {
            JLabelBlankNameMessage.setVisible(true);
            return false;
        }
    }

    private boolean validateDeadline() {
        if (!Objects.equals(deadlineTextField.getText(), "")) {
            try {
                LocalDate deadline = getDate(deadlineTextField.getText());
                LocalDate today = LocalDate.now();
                if (!today.isAfter(deadline)) {
                    return true;
                } else {
                    JLabelWrongDateDeadlineMessage.setVisible(true);
                    return false;
                }
            } catch (Exception e) {
                JLabelWrongFormatDeadlineMessage.setVisible(true);
                return false;
            }
        } else {
            JLabelBlankDeadlineMessage.setVisible(true);
            return false;
        }
    }

    private String getDefaultStringDateFormat(String inputDate) {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(inputDate, formatter);
        return date.toString();
    }

    private String getBrazilianStringDateFormat(String inputDate) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);
        return date.getDayOfMonth()+ "/" + date.getMonthValue() + "/" + date.getYear();
    }

    public LocalDate getDate(String stringDate) {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(stringDate, formatter);
    }

    private void clearMessages() {
        if (JLabelBlankNameMessage.isVisible()) {
            JLabelBlankNameMessage.setVisible(false);
        }
        if (JLabelBlankDeadlineMessage.isVisible()) {
            JLabelBlankDeadlineMessage.setVisible(false);
        }
        if (JLabelWrongFormatDeadlineMessage.isVisible()) {
            JLabelWrongFormatDeadlineMessage.setVisible(false);
        }
        if (JLabelWrongDateDeadlineMessage.isVisible()) {
            JLabelWrongDateDeadlineMessage.setVisible(false);
        }
    }

    private void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
