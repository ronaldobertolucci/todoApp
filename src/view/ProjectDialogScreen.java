package view;

import controller.ProjectController;
import model.Project;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class ProjectDialogScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JPanel JPanelFooter;
    private JPanel JPanelButtonOK;
    private javax.swing.JPanel JPanelMain;
    private JPanel JPanelHeader;
    private JLabel JLabelHeaderTitle;
    private JLabel JLabelHeaderIcon;
    private JPanel JPanelFields;
    private JLabel JLabelNameTitle;
    private JLabel JLabelDescriptionTitle;
    private JPanel JPanelTextArea;
    private JLabel JLabelBlankNameMessage;

    private ProjectController projectController;

    public ProjectDialogScreen() {
        projectController = new ProjectController();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        dialogInitialization();
    }

    private void onOK() {
        if (JLabelBlankNameMessage.isVisible()) {
            JLabelBlankNameMessage.setVisible(false);
        }
        if (validateName()) {
            try {
                Project project = new Project();
                project.setName(nameTextField.getText());
                project.setDescription(descriptionTextArea.getText());
                projectController.save(project);
                JOptionPane.showMessageDialog(rootPane, "Projeto salvo com sucesso!");
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

    private void dialogInitialization() {
        this.setContentPane(contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(buttonOK);
        this.pack();
        this.setVisible(true);
    }

}
