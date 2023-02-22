package br.com.bertolucci.todoapp.view;

import br.com.bertolucci.todoapp.controller.ProjectController;
import br.com.bertolucci.todoapp.model.Project;

import javax.swing.*;
import java.awt.*;
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
    private JLabel JLabelProjectDetailTitle;
    private JButton buttonDel;

    private ProjectController projectController;
    private DefaultListModel<Project> projectsModel;
    private Project project;
    private int projectIndex;
    private String actionPerformedOnClose;

    public ProjectDialogScreen() {
        projectController = new ProjectController();
        showCorrectTitles();
        initSaveListener();
        dialogInitialization();
    }

    public ProjectDialogScreen(Project project, DefaultListModel<Project> projectsModel, int projectIndex) {
        this.projectsModel = projectsModel;
        this.setProject(project);
        this.setProjectIndex(projectIndex);
        this.nameTextField.setText(project.getName());
        this.descriptionTextArea.setText(project.getDescription());
        projectController = new ProjectController();
        showCorrectTitles();
        initUpdateListeners();
        dialogInitialization();
    }

    private void showCorrectTitles() {
        if (this.project != null) {
            this.JLabelProjectDetailTitle.setVisible(true);
            this.JLabelHeaderTitle.setVisible(false);
            this.buttonDel.setVisible(true);
            this.JLabelHeaderIcon.setVisible(false);
        } else {
            this.JLabelProjectDetailTitle.setVisible(false);
            this.JLabelHeaderTitle.setVisible(true);
            this.buttonDel.setVisible(false);
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

    private void initUpdateListeners() {
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onUpdateOK();
            }
        });
        this.buttonDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });
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
                setActionPerformedOnClose("save");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
            this.dispose();
        }
    }

    private void onUpdateOK() {
        if (JLabelBlankNameMessage.isVisible()) {
            JLabelBlankNameMessage.setVisible(false);
        }
        if (validateName()) {
            try {
                this.project.setName(nameTextField.getText());
                this.project.setDescription(descriptionTextArea.getText());
                projectController.update(this.project);
                JOptionPane.showMessageDialog(rootPane, "Projeto atualizado com sucesso!");
                setActionPerformedOnClose("update");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
            this.dispose();
        }
    }

    public void onDelete() {
        projectController.removeById(project.getId());
        projectsModel.remove(projectIndex);
        setActionPerformedOnClose("delete");
        this.dispose();
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
        centreWindow(this);
        this.setVisible(true);
    }

    public void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setProjectsModel(DefaultListModel<Project> projectsModel) {
        this.projectsModel = projectsModel;
    }

    public void setProjectIndex(int projectIndex) {
        this.projectIndex = projectIndex;
    }

    public String getActionPerformedOnClose() {
        return actionPerformedOnClose;
    }

    public void setActionPerformedOnClose(String actionPerformedOnClose) {
        this.actionPerformedOnClose = actionPerformedOnClose;
    }
}
