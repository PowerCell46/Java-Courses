import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// with this implementation the code works with 100/100 in Judge
public class MatchingBrackets2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> expressions = new ArrayList<>();

        String text = scanner.nextLine();
        int index = text.indexOf("(") < 0 ? text.length() : text.indexOf("(");


        for (; index < text.length(); index++) {
            char currentCharacter = text.charAt(index);
            StringBuilder currentExpression = new StringBuilder();
            int currentCounter = 1;

            if (currentCharacter == '(') {
                currentExpression.append(text.charAt(index));

                for (int i = index + 1; i < text.length(); i++) {
                    currentExpression.append(text.charAt(i));
                    if (text.charAt(i) == '(') {
                        currentCounter++;

                    } else if (text.charAt(i) == ')') {
                        currentCounter--;

                        if (currentCounter == 0) {
                            expressions.add(currentExpression.toString());
                            break;
                        }
                    }
                }
            }
        }

        index = text.indexOf(")") < 0 ? text.length() : text.indexOf("(");


        for (; index < text.length(); index++) {
            int currentCounter = 0;
            List<Character> currentExpression = new ArrayList<>();

            if (text.charAt(index) == ')') {
                for (int i = index; i > -1; i--) {
                    currentExpression.addFirst(text.charAt(i));
                    if (text.charAt(i) == '(') {
                        currentCounter--;
                        if (currentCounter == 0) {
                            currentExpression.forEach(x -> System.out.print(x));
                            System.out.println();
                            break;
                        }
                    } else if (text.charAt(i) == ')') {
                        currentCounter++;
                    }
                }
            }
        }
    }
}

// 1 + (2 - (2 + 3) * 4 / (3 + 1)) * 5
