import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String degreesInput = scanner.nextLine();
        int rotatingDegrees = Integer.parseInt(degreesInput.substring(7, degreesInput.length() - 1));
        while (rotatingDegrees >= 360) rotatingDegrees -= 360;

        List<String> dataKeeper = new ArrayList<>();
        int biggestLength = 0;

        while (true) {
            String currentInput = scanner.nextLine();
            if (currentInput.equals("END")) break;

            dataKeeper.add(currentInput);
            if (currentInput.length() > biggestLength) biggestLength = currentInput.length();
        }

        String[][] matrix = new String[dataKeeper.size()][biggestLength];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = j < dataKeeper.get(i).length() ? dataKeeper.get(i).charAt(j) + "" : " ";
            }
        }

        switch (rotatingDegrees) {
            case 0:
                for (String[] row : matrix) {
                    for (String element : row) {
                        System.out.print(element);
                    }
                    System.out.println();
                }
                break;
            case 90:
                String[][] matrix90 = new String[matrix[0].length][matrix.length];

                for (int i = 0; i < matrix.length; i++) {
                    String[] currentWord = matrix[i];
                    for (int j = 0; j < currentWord.length; j++) {
                        matrix90[j][matrix.length - 1 - i] = currentWord[j];
                    }
                }
                for (String[] row : matrix90) {
                    for (String element : row) {
                        System.out.print(element);
                    }
                    System.out.println();
                }
                break;
            case 180:
                String[][] matrix180 = new String[matrix.length][matrix[0].length];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix180[matrix.length - 1 - i][matrix[0].length - 1 - j] = matrix[i][j];
                    }
                }
                for (String[] row : matrix180) {
                    for (String element : row) {
                        System.out.print(element);
                    }
                    System.out.println();
                }
                break;
            case 270:
                String[][] matrix270 = new String[matrix[0].length][matrix.length];

                for (int i = 0; i < matrix.length; i++) {
                    String[] currentWord = matrix[i];
                    for (int j = 0; j < currentWord.length; j++) {
                        matrix270[matrix[0].length - 1 - j][i] = currentWord[j];
                    }
                }
                for (String[] row : matrix270) {
                    for (String element : row) {
                        System.out.print(element);
                    }
                    System.out.println();
                }
                break;
        }
    }
}
