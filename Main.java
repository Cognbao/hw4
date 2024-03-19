import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            StudentManager studentManager = new StudentManager();
            studentManager.run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
