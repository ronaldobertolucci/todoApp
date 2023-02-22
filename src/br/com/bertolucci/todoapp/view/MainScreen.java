package br.com.bertolucci.todoapp.view;

import br.com.bertolucci.todoapp.controller.ProjectController;
import br.com.bertolucci.todoapp.controller.TaskController;
import br.com.bertolucci.todoapp.model.Project;
import br.com.bertolucci.todoapp.model.Task;
import br.com.bertolucci.todoapp.util.ButtonCellRenderer;
import br.com.bertolucci.todoapp.util.DeadlineCellRenderer;
import br.com.bertolucci.todoapp.util.TaskTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MainScreen extends Container {
    private JList JListProjects;
    private JPanel JPanelToolbar;
    private JLabel JLabelToolbarTitle;
    private JLabel JLabelToolbarSubtitle;
    private JPanel JPanelMain;
    private JPanel JPanelProjects;
    private JLabel JLabelProjectsTitle;
    private JLabel JLabelProjectsAdd;
    private JPanel JPanelTasks;
    private JLabel JLabelTasksTitle;
    private JLabel JLabelTasksAdd;
    private JPanel JPanelEmptyList;
    private JLabel JLabelEmptyListIcon;
    private JLabel JLabelEmptyTaskListTitle;
    private JLabel JLabelEmptyTaskListSubtitle;
    private JTable taskTable;
    private JPanel contentPane;
    private JScrollPane JPanelProjectList;
    private JPanel JPanelTaskTable;
    private JScrollPane JScrollPaneTasks;
    private JLabel JLabelEmptyProjectListTitle;
    private JLabel JLabelEmptyProjectListSubtitle;
    private ProjectController projectController;
    private TaskController taskController;
    private DefaultListModel<Project> projectsModel;
    private TaskTableModel taskModel;

    public MainScreen() {
        initDataControllers();
        initComponentsModel();
        checkProjects();
        initListeners();
    }

    public void initDataControllers() {
        projectController = new ProjectController();
        taskController = new TaskController();
    }

    public void initComponentsModel() {
        projectsModel = new DefaultListModel<Project>();
        loadProjects();
        createTaskModel();
    }

    public void initListeners() {
        /*
         mousePressed é uma alternativa melhor ao mouseClicked, pois o mouseClicked não funciona quando
         o usuário aperta o botão, move o mouse e solta o botão.
         */
        this.JLabelProjectsAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ProjectDialogScreen projectDialogScreen = new ProjectDialogScreen();
                projectDialogScreen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        loadProjects();
                        checkProjects(projectDialogScreen.getActionPerformedOnClose());
                    }
                });
            }
        });
        this.JLabelTasksAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int projectIndex = JListProjects.getSelectedIndex();
                Project project = projectsModel.get(projectIndex);
                TaskDialogScreen taskDialogScreen = new TaskDialogScreen(project);
                taskDialogScreen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        int projectIndex = JListProjects.getSelectedIndex();
                        Project project = projectsModel.get(projectIndex);
                        loadTasks(project.getId());
                    }
                });
            }
        });
        // O update do status só funcionou com mouseClicked
        this.taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int rowIndex = taskTable.rowAtPoint(e.getPoint());
                int columnIndex = taskTable.columnAtPoint(e.getPoint());
                Task task;
                switch (columnIndex) {
                    case 3:
                        task = taskModel.getTasks().get(rowIndex); // busque a task na linha
                        taskController.update(task); // salve a atualização da task no db
                        break;
                    case 4:
                        task = taskModel.getTasks().get(rowIndex);
                        TaskDialogScreen taskDialogScreen = new TaskDialogScreen(task);
                        loadTasks(task.getProjectId());
                        break;
                    case 5:
                        task = taskModel.getTasks().get(rowIndex);
                        DeleteConfirmationDialogScreen deleteScreen =
                                new DeleteConfirmationDialogScreen(task, taskModel);
                        loadTasks(task.getProjectId());
                        break;
                }
            }
        });
        this.JListProjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int projectIndex = JListProjects.getSelectedIndex();
                Project project = projectsModel.get(projectIndex);
                loadTasks(project.getId());
            }
        });
        this.JListProjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int projectIndex = JListProjects.getSelectedIndex();
                    Project project = projectsModel.get(projectIndex);
                    ProjectDialogScreen projectDialogScreen =
                            new ProjectDialogScreen(project, projectsModel, projectIndex);
                    projectDialogScreen.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            loadProjects();
                            checkProjects(projectDialogScreen.getActionPerformedOnClose(), projectIndex);
                        }
                    });

                }
            }
        });
    }

    public void customizeTaskTable() {
        taskTable.setSelectionMode(SINGLE_SELECTION);
        taskTable.getTableHeader().setFont(new Font("Inconsolata", Font.BOLD, 12));
        taskTable.getTableHeader().setBackground(new Color(31,112,60));
        taskTable.getTableHeader().setForeground(new Color(244,253,255));

        taskTable.getColumnModel().getColumn(2).setCellRenderer(new DeadlineCellRenderer());
        taskTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer("edit"));
        taskTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonCellRenderer("delete"));
    }

    public void initializeScreen() {
        JFrame frame = new JFrame("Gerenciador de tarefas");
        frame.setContentPane(new MainScreen().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        centreWindow(frame);
        frame.setVisible(true);
    }

    public void loadProjects() {
        List<Project> projects = projectController.getAll();
        projectsModel.clear(); // caso haja dados, sejam removidos (loadProjects será usado várias vezes)
        for(int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            projectsModel.addElement(project);
        }
        JListProjects.setModel(projectsModel);
        hasProjects(!projects.isEmpty());
    }

    public void loadTasks(int projectId) {
        List<Task> tasks = taskController.getAll(projectId);
        createTaskModel();
        taskModel.setTasks(tasks);
        showTasksTable(!tasks.isEmpty());
    }

    private void hasProjects(boolean hasProjects) {
        if (hasProjects) {
            JLabelEmptyProjectListSubtitle.setVisible(false);
            JLabelEmptyProjectListTitle.setVisible(false);
            JLabelEmptyTaskListTitle.setVisible(true);
            JLabelEmptyTaskListSubtitle.setVisible(true);
        } else {
            JPanelEmptyList.setVisible(true);
            JLabelEmptyProjectListSubtitle.setVisible(true);
            JLabelEmptyProjectListTitle.setVisible(true);
            JLabelEmptyTaskListTitle.setVisible(false);
            JLabelEmptyTaskListSubtitle.setVisible(false);
            JScrollPaneTasks.setVisible(false);
        }
    }

    private void showTasksTable(boolean projectHasTasks) {
        if (projectHasTasks) {
            if (JPanelEmptyList.isVisible()) {
                JPanelEmptyList.setVisible(false);
            }
            JScrollPaneTasks.setVisible(true);
            JScrollPaneTasks.setSize(JPanelTaskTable.getWidth() - 3, JPanelTaskTable.getHeight() - 3);
        } else {
            if (JScrollPaneTasks.isVisible()) {
                JScrollPaneTasks.setVisible(false);
            }
            JPanelEmptyList.setVisible(true);
            JPanelEmptyList.setSize(JPanelTaskTable.getWidth() - 4, JPanelTaskTable.getHeight() - 4);
        }
    }

    public void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void createTaskModel() {
        taskModel = new TaskTableModel();
        taskTable.setModel(taskModel);
        customizeTaskTable();
    }

    public void checkProjects() {
        if (!projectsModel.isEmpty()) {
            JListProjects.setSelectedIndex(0);
            Project project = projectsModel.get(0);
            loadTasks(project.getId());
        }
    }

    public void checkProjects(String actionPerformed) {
        if (!projectsModel.isEmpty()) {
            Project project;
            if (actionPerformed == "save") {
                JListProjects.setSelectedIndex(JListProjects.getLastVisibleIndex());
                project = projectsModel.get(JListProjects.getLastVisibleIndex());
            } else {
                JListProjects.setSelectedIndex(0);
                project = projectsModel.get(0);
            }
            loadTasks(project.getId());
        }
    }

    public void checkProjects(String actionPerformed, int projectIndex) {
        if (!projectsModel.isEmpty()) {
            Project project;
            if (actionPerformed == "update") {
                JListProjects.setSelectedIndex(projectIndex);
                project = projectsModel.get(JListProjects.getSelectedIndex());
            } else {
                JListProjects.setSelectedIndex(0);
                project = projectsModel.get(0);
            }
            loadTasks(project.getId());
        }
    }
}
