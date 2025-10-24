package com.StudentGradeTracker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}
                                                                                      
public class StudentGradeTrackerGUI extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameField, gradeField;
    private JTextArea reportArea;
    private JButton addButton, showButton, clearButton;
    private ArrayList<Student> students;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        students = new ArrayList<>();

        // ----- Input Panel -----
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student Details"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        addButton = new JButton("Add Student");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        clearButton = new JButton("Clear All");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        // ----- Report Area -----
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(reportArea);

        // ----- Bottom Button -----
        showButton = new JButton("Show Report");
        showButton.addActionListener(this);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(showButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (name.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and grade.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double grade = Double.parseDouble(gradeText);
                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100.", "Invalid Grade", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                students.add(new Student(name, grade));
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for grade.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == showButton) {
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            double total = 0, highest = students.get(0).grade, lowest = students.get(0).grade;
            StringBuilder report = new StringBuilder();
            report.append(String.format("%-20s %-10s\n", "Student Name", "Grade"));
            report.append("-----------------------------------\n");

            for (Student s : students) {
                report.append(String.format("%-20s %-10.2f\n", s.name, s.grade));
                total += s.grade;
                if (s.grade > highest) highest = s.grade;
                if (s.grade < lowest) lowest = s.grade;
            }

            double average = total / students.size();
            report.append("\nAverage Grade: " + String.format("%.2f", average));
            report.append("\nHighest Grade: " + String.format("%.2f", highest));
            report.append("\nLowest Grade: " + String.format("%.2f", lowest));

            reportArea.setText(report.toString());

        } else if (e.getSource() == clearButton) {
            students.clear();
            reportArea.setText("");
            nameField.setText("");
            gradeField.setText("");
            JOptionPane.showMessageDialog(this, "All data cleared!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTrackerGUI::new);
    }
}
