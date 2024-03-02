import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("231RDB120 Aivita Rižova 13.grupa");

        String fileName;
        System.out.println("input file name:");
        fileName = sc.nextLine().trim();

        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }
        sc.close();

        try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
            System.out.println("result:");

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Pārbaudam, kura funkcija izmantot atkarībā no faila nosaukuma
                if (fileName.contains("Students1")) {
                    processStudentData(line, false);
                } else {
                    processStudentData(line, true);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file:" + e.getMessage());
        }
    }

    private static void processStudentData(String data, boolean isStudentsFile) {
        String[] studentInfo = data.split(" ");

        if (studentInfo.length >= 3) {
            String surname = studentInfo[0];
            String firstName = studentInfo[1];
            double videjaAtzime;

            // Determine the starting index for grades based on the number of names and surnames
            int gradeStartIndex = 2 + countNamesAndSurnames(studentInfo);

            if (isStudentsFile) {
                videjaAtzime = calculateAverageGrade(studentInfo, gradeStartIndex);
            } else {
                videjaAtzime = calculateAverageGradeForStudents1(studentInfo, gradeStartIndex);
            }

            int paraduDaudzums = getParaduDaudzums(studentInfo, gradeStartIndex);
            if (videjaAtzime <= 5 && paraduDaudzums > 0) {
                System.out.println(firstName + " " + surname + " " + paraduDaudzums);
            }
        } else {
            System.out.println("Invalid data format:" + data);
        }
    }

    private static int countNamesAndSurnames(String[] studentInfo) {
        int count = 0;
        for (int i = 2; i < studentInfo.length; i++) {
            try {
                Integer.parseInt(studentInfo[i]);
            } catch (NumberFormatException e) {
                // If it's not a number, consider it a name or surname
                count++;
            }
        }
        return count;
    }

    private static double calculateAverageGrade(String[] studentInfo, int startIndex) {
        double sum = 0;
        int validGradeCount = 0;
        for (int i = startIndex; i < studentInfo.length; i++) {
            try {
                int grade = Integer.parseInt(studentInfo[i]);
                sum += grade;
                validGradeCount++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid grade format:" + studentInfo[i]);
            }
        }
        return validGradeCount > 0 ? sum / validGradeCount : 0;
    }

    private static double calculateAverageGradeForStudents1(String[] studentInfo, int startIndex) {
        double sum = 0;
        int validGradeCount = 0;
        // Start from the specified index
        for (int i = startIndex; i < studentInfo.length; i++) {
            try {
                int grade = Integer.parseInt(studentInfo[i]);
                sum += grade;
                validGradeCount++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid grade format:" + studentInfo[i]);
            }
        }
        return validGradeCount > 0 ? sum / validGradeCount : 0;
    }

    private static int getParaduDaudzums(String[] studentInfo, int startIndex) {
        int paraduDaudzums = 0;
        try {
            for (int i = startIndex; i < studentInfo.length; i++) {
                if (Integer.parseInt(studentInfo[i]) < 4) {
                    paraduDaudzums++;
                }
            }
        } catch (NumberFormatException ignored) {
        }

        return paraduDaudzums;
    }
}
