import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends Frame implements ActionListener {

    TextField nameField, ageField, idField, percentageField;
    TextArea displayArea;
    Button submitButton;
    ArrayList<GraduateStudent> studentList = new ArrayList<>();

    public Main() {
        setTitle("Student Information System");
        setSize(600, 600);
        setLayout(new FlowLayout());

        add(new Label("Name:"));
        nameField = new TextField(20);
        add(nameField);

        add(new Label("Age:"));
        ageField = new TextField(20);
        add(ageField);

        add(new Label("Student ID:"));
        idField = new TextField(20);
        add(idField);

        add(new Label("Percentage:"));
        percentageField = new TextField(20);
        add(percentageField);

        submitButton = new Button("Add Student");
        submitButton.addActionListener(this);
        add(submitButton);

        displayArea = new TextArea(20, 60);
        displayArea.setEditable(false);
        add(displayArea);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String studentId = idField.getText().trim();
            double percentage = Double.parseDouble(percentageField.getText().trim());

            GraduateStudent student = new GraduateStudent(name, age, studentId, percentage);
            studentList.add(student);

            displayArea.setText("");
            for (GraduateStudent gs : studentList) {
                gs.displayDetails(displayArea);
            }

            StudentBase.showStudentCount(displayArea);

            nameField.setText("");
            ageField.setText("");
            idField.setText("");
            percentageField.setText("");

        } catch (Exception ex) {
            displayArea.setText("Invalid input. Please check values.\n");
        }
    }

    // Interface
    interface Grading {
        String calculateGrade(double percentage);
    }

    // Base classes
    class Person {
        String name;
        int age;

        Person() {
            name = "Unknown";
            age = 0;
        }

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    class StudentBase extends Person {
        String studentId;
        static int studentCount = 0;

        StudentBase(String name, int age, String studentId) {
            super(name, age);
            this.studentId = studentId;
            studentCount++;
        }

        static void showStudentCount(TextArea displayArea) {
            displayArea.append("\nTotal Students: " + studentCount + "\n");
        }
    }

    class GraduateStudent extends StudentBase implements Grading {
        double percentage;

        GraduateStudent(String name, int age, String studentId, double percentage) {
            super(name, age, studentId);
            this.percentage = percentage;
        }

        @Override
        public String calculateGrade(double percentage) {
            if (percentage >= 85)
                return "A";
            else if (percentage >= 70)
                return "B";
            else if (percentage >= 50)
                return "C";
            else
                return "F";
        }

        void displayDetails(TextArea displayArea) {
            displayArea.append("\nStudent ID : " + studentId);
            displayArea.append("\nName       : " + name);
            displayArea.append("\nAge        : " + age);
            displayArea.append("\nPercentage : " + percentage);
            displayArea.append("\nGrade      : " + calculateGrade(percentage) + "\n");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}