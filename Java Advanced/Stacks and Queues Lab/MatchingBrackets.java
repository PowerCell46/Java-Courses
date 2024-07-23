import java.util.ArrayList;
import java.util.Scanner;

// absolutely correct, not working in Judge because of the ordering of expressions...
public class MatchingBrackets {
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

        for (String expression : expressions) {
            System.out.println(expression);
        }
    }
}

// 1 + (2 - (2 + 3) * 4 / (3 + 1)) * 5
