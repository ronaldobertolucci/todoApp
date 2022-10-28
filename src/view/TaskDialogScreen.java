package view;

import controller.TaskController;
import model.Project;
import model.Task;

import javax.swing.*;
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
    private TaskController taskController = new TaskController();
    private Project project;
    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TaskDialogScreen(Project project) {
        this.setProject(project);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        dialogInitialization();
    }

    private void onOK() {
        clearMessages();
        if (validateDeadline(deadlineTextField.getText()) & validateName()) {
            try {
                Task task = new Task();
                task.setProjectId(project.getId());
                task.setName(nameTextField.getText());
                task.setDescription(descriptionTextArea.getText());
                task.setNotes(notesTextArea.getText());
                task.setDeadline(getDefaultDateFormat(deadlineTextField.getText()));
                task.setStatus(0);
                taskController.save(task);
                JOptionPane.showMessageDialog(rootPane, "Tarefa salva com sucesso!");
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

    private boolean validateDeadline(String inputDate) {
        if (!Objects.equals(deadlineTextField.getText(), "")) {
            try {
                LocalDate deadline = LocalDate.parse(deadlineTextField.getText(), formatter);
                LocalDate date = LocalDate.parse(inputDate, formatter);
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

    private String getDefaultDateFormat(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate, formatter);
        return date.toString();
    }

    private void dialogInitialization() {
        this.setContentPane(contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.pack();
        this.setVisible(true);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void clearMessages() {
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
}
