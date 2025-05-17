import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ToDoApp {
    private JFrame frame;
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;
    private JTextField taskField;
    private final String FILE_NAME = "tasks.txt";

    public ToDoApp() {
        frame = new JFrame("To-Do List");
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");

        loadTasks();

        addButton.addActionListener(e -> {
            String task = taskField.getText().trim();
            if (!task.isEmpty()) {
                taskModel.addElement(task);
                taskField.setText("");
                saveTasks();
            }
        });

        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskModel.remove(index);
                saveTasks();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(deleteButton, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskModel.getSize(); i++) {
                writer.println(taskModel.getElementAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                taskModel.addElement(line);
            }
        } catch (IOException e) {
            // Ignore if file not found
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }
}