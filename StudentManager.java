import java.io.*;
import java.util.*;

public class StudentManager {
    

    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        // Nhập số lượng học viên
        System.out.print("Nhập số lượng học viên: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Tạo danh sách học viên
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            students.add(inputStudent(scanner));
        }

        // Ghi danh sách học viên ra file
        writeToFile(students, FILE_NAME);

        System.out.println("Danh sách học viên đã được ghi ra file " + FILE_NAME);

        // Đọc danh sách học viên từ file
        List<Student> studentsFromFile = readFromFile(FILE_NAME);

        // Tính điểm trung bình và kết quả
        for (Student student : studentsFromFile) {
            student.setAverageScore((student.getJavaScore() * 2 + student.getPythonScore()) / 3);
            student.setResult(calculateResult(student.getAverageScore(), student.getGender()));
        }

        // In danh sách học viên
        printStudents(studentsFromFile);

        // Sắp xếp học viên theo điểm trung bình giảm dần
        Collections.sort(studentsFromFile, Comparator.comparingDouble(Student::getAverageScore).reversed());

        // Ghi danh sách học viên đã sắp xếp ra file
        writeToFile(studentsFromFile, "students_sorted.dat");

        System.out.println("Danh sách học viên đã được sắp xếp theo điểm trung bình giảm dần và ghi ra file students_sorted.dat");

        // Tìm kiếm học viên theo họ tên
        System.out.print("Nhập họ tên học viên cần tìm: ");
        String name = scanner.nextLine();
        List<Student> foundStudents = findStudentsByName(studentsFromFile, name);
        if (foundStudents.isEmpty()) {
            System.out.println("Học viên có họ tên " + name + " không tồn tại trong danh sách.");
        } else {
            System.out.println("Danh sách học viên có họ tên " + name + ":");
            for (Student student : foundStudents) {
                System.out.println(student);
            }
        }
    }

    private static Student inputStudent(Scanner scanner) {
        System.out.print("Nhập mã sinh viên: ");
        String studentCode = scanner.nextLine();

        System.out.print("Nhập họ tên: ");
        String name = scanner.nextLine();

        System.out.print("Nhập giới tính (Nam/Nữ): ");
        String gender = scanner.nextLine();

        System.out.print("Nhập điểm Python: ");
        double pythonScore = scanner.nextDouble();

        System.out.print("Nhập điểm Java: ");
        double javaScore = scanner.nextDouble();

        // Tạo học viên mới
        return new Student(studentCode, name, gender, pythonScore, javaScore);
    }

    private static void writeToFile(List<Student> students, String fileName) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(students);
        }
    }

    private static List<Student> readFromFile(String fileName) throws IOException, ClassNotFoundException {
        List<Student> students;
        try (
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            students = (List<Student>) ois.readObject();
        }
        return students;
    }

    private static List<Student> findStudentsByName(List<Student> students, String name) {
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }

    private static String calculateResult(double averageScore, String gender) {
        return averageScore >= 5 && gender.equalsIgnoreCase("Nam") ? "Đậu" : "Rớt";
    }

    private static void printStudents(List<Student> students) {
        System.out.println("Danh sách học viên:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void run() {
    }
}

class Student implements Serializable {

    private String studentCode;
    private String name;
    private String gender;
    private double pythonScore;
    private double javaScore;
    private double averageScore;
    private String result;

    public Student() {
    }

    public Student(String studentCode, String name, String gender, double pythonScore, double javaScore) {
        this.studentCode = studentCode;
        this.name = name;
        this.gender = gender;
        this.pythonScore = pythonScore;
        this.javaScore = javaScore;
    }

    // Getters and setters for averageScore and result

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Other getters

    public String getStudentCode() {
        return studentCode;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public double getPythonScore() {
        return pythonScore;
    }

    public double getJavaScore() {
        return javaScore;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentCode='" + studentCode + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", pythonScore=" + pythonScore +
                ", javaScore=" + javaScore +
                ", averageScore=" + averageScore +
                ", result='" + result + '\'' +
                '}';
    }
}
