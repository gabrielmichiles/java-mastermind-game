/*
 Name: Gabriel Oliveira Michiles
 Date: November 30th 2022
 Purpose: draw a rectangle within a Cartesian quadrant based on user input
 Input: numbers
 Output: a filled-in rectangle positioned within a Cartesian quadrant of size 40 x 20 (width x height)
*/

import java.util.Scanner;

public class MastermindGame {

  static Scanner input = new Scanner(System.in);
  static final boolean CHEATERMODE = false;

  public static void main(String[] args) {
    printIdentification();
    callMethods();
  }

  /**
   * prints student identification
   */
  private static void printIdentification() {
    System.out.println(
      "### App: Mastermind Game   Author: Gabriel Oliveira Michiles  ###"
    );
    System.out.println();
  }

  /**
   * validates user input
   * so only non-negative integers can be stored
   * @return a non-negative integer
   */
  private static int getValidInt() {
    int integer;
    while (!input.hasNextInt() || (integer = input.nextInt()) < 0) {
      System.out.println();
      System.out.println("Invalid input. Please enter a non-negative integer.");
      input.nextLine();
    }
    input.nextLine();
    return integer;
  }

  /**
   * gets how many digits should be guessed
   * @return amount of digits to be guessed
   */
  private static int getDigitsAmount() {
    System.out.println("How many digits would you like?");
    int amount = getValidInt();
    while (amount < 2 || amount > 10) {
      System.out.println(
        "Invalid amount. Please enter an integer from 2 to 10."
      );
      System.out.println();
      amount = getValidInt();
    }
    return amount;
  }

  /**
   * generates a set of secret digits to be guessed
   * @param digitAmount determines how many digits number has to have
   * @return number with a random set of digits
   */
  private static String generateDigits(int digitAmount) {
    String number = "";

    for (int i = 0; i < digitAmount; i++) {
      int randomInt = (int) (Math.random() * 10);
      char randomChar = (char) (randomInt + '0');

      while (number.indexOf(randomChar) != -1) {
        randomInt = (int) (Math.random() * 10);
        randomChar = (char) (randomInt + '0');
      }

      number += randomChar;
    }

    return number;
  }

  /**
   * checks if user entered only digits
   * @param input the string user entered
   * @return if user input was only digits or not
   */
  private static boolean checkInputDigitsOnly(String input) {
    for (int i = 0; i < input.length(); i++) {
      if (!Character.isDigit(input.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * checks if user entered different digits
   * @param input the string user entered
   * @return if user input was only different digits or not
   */
  private static boolean checkInputDifferentDigits(String input) {
    for (int i = 0; i < input.length(); i++) {
      for (int k = i + 1; k < input.length(); k++) {
        if (input.charAt(i) == input.charAt(k)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * validates user input to enter only different digits
   * @param input amount of digits user has to input
   * @return a set of valid digits user input
   */
  private static String getValidUserDigits(int digitAmount) {
    System.out.println(
      "Please input a number of " +
      digitAmount +
      " digits. All digits have to be different."
    );

    String userDigits = input.next();

    if (userDigits.length() != digitAmount) {
      System.out.println(
        "Wrong amount of digits. Please input " + digitAmount + " digits."
      );
    } else {
      while (checkInputDigitsOnly(userDigits) == false) {
        System.out.println("Invalid input. Please input only digits.");
        userDigits = input.next();
      }

      while (checkInputDifferentDigits(userDigits) == false) {
        System.out.println(
          "Input has repeated digits. Please input only different digits."
        );
        userDigits = input.next();
      }
    }

    return userDigits;
  }

  /**
   * compares computer generated digits and user digits
   * to check if they're the same
   * @param secretDigits computer generated digits
   * @param userDigits user input digits
   * @return if both parameters are the same or not
   */
  private static boolean checkIfDigitsAreEqual(
    String secretDigits,
    String userDigits
  ) {
    if (secretDigits.equals(userDigits)) {
      return true;
    }
    return false;
  }

  /**
   * checks how many user input digits are correct
   * and how many are at the right position
   * @param secretDigits computer generated digits
   * @param userDigits user input digits
   * @return how many digits are the right position
   */
  private static int howManyCorrectDigitsRightPlace(
    String secretDigits,
    String userDigits
  ) {
    int rightPlace = 0;

    for (int i = 0; i < userDigits.length(); i++) {
      if (userDigits.charAt(i) == secretDigits.charAt(i)) {
        rightPlace++;
      }
    }

    return rightPlace;
  }

  /**
   * checks how many user input digits are correct
   * and how many are at the wrong position
   * @param secretDigits computer generated digits
   * @param userDigits user input digits
   * @return how many digits are the wrong position
   */
  private static int howManyCorrectDigitsWrongPlace(
    String secretDigits,
    String userDigits
  ) {
    int wrongPlace = 0;

    for (int i = 0; i < userDigits.length(); i++) {
      if (secretDigits.indexOf(userDigits.charAt(i)) != -1) {
        char temp = userDigits.charAt(i);

        if (secretDigits.indexOf(temp) != userDigits.indexOf(temp)) {
          wrongPlace++;
        }
      }
    }

    return wrongPlace;
  }

  /**
   * joins all previous methods to run program
   */
  private static void callMethods() {
    char response = 'y';

    System.out.println("Welcome to the Mastermind Game.");
    System.out.println();

    while (response == 'y') {
      int digitsAmount = getDigitsAmount();
      String secretDigits = generateDigits(digitsAmount);
      String userDigits = getValidUserDigits(digitsAmount);
      int correctDigitsRightPlace = howManyCorrectDigitsRightPlace(
        secretDigits,
        userDigits
      );
      int correctDigitsWrongPlace = howManyCorrectDigitsWrongPlace(
        secretDigits,
        userDigits
      );
      int guesses = 1;
      final int MAXGUESSES = 12;

      if (checkIfDigitsAreEqual(secretDigits, userDigits) == true) {
        System.out.print(
          "Congratulations! You guessed the number in just 1 attempt!"
        );
      } else {
        char tryAgain = 'k';

        while (checkIfDigitsAreEqual(secretDigits, userDigits) == false) {
          guesses++;

          System.out.println(
            "Nice try but it's incorrect!. You got " +
            correctDigitsRightPlace +
            " correct digit(s) in the right place and " +
            correctDigitsWrongPlace +
            " correct digit(s) in the wrong place."
          );
          System.out.println();
          System.out.println("Would you like to try again?");
          System.out.println(
            "Input 'k' if yes. Otherwise input any other key."
          );
          tryAgain = input.next().toLowerCase().charAt(0);
          System.out.println();

          if (tryAgain != 'k') {
            System.out.println("Correct number was " + secretDigits + ".");
            System.out.println();
            break;
          } else if (guesses == MAXGUESSES) {
            System.out.println("You reached maximum number of attempts!");
            System.out.println();
            break;
          } else {
            userDigits = getValidUserDigits(digitsAmount);
            correctDigitsRightPlace =
              howManyCorrectDigitsRightPlace(secretDigits, userDigits);
            correctDigitsWrongPlace =
              howManyCorrectDigitsWrongPlace(secretDigits, userDigits);
          }
        }

        if (checkIfDigitsAreEqual(secretDigits, userDigits) == true) {
          System.out.println(
            "Congratulations! Correct number was " +
            secretDigits +
            ". You found it out in " +
            guesses +
            " attempts!"
          );
          System.out.println();
        }
      }

      System.out.println("Would you like to try again with a new number?");
      System.out.println("Input 'y' if yes. Otherwise input any other key.");
      response = input.next().toLowerCase().charAt(0);
      System.out.println();

      if (response != 'y') {
        System.out.println("Program finished. Thanks for playing!");
      }
    }
  }
}
/*
SAMPLE OUTPUT:
>java MastermindGame
### App: Mastermind   Author: Gabriel Oliveira Michiles  ###


Welcome to the Mastermind Game.

How many digits would you like?
2
Please input a number of 2 digits. All digits have to be different.
12
Nice try but it's incorrect!. You got 1 correct digit(s) in the right place and 0 correct digit(s) in the wrong place.

Would you like to try again?
Input 'k' if yes. Otherwise input any other key.
k

Please input a number of 2 digits. All digits have to be different.
13
Nice try but it's incorrect!. You got 0 correct digit(s) in the right place and 1 correct digit(s) in the wrong place.

Would you like to try again?
Input 'k' if yes. Otherwise input any other key.
k

Please input a number of 2 digits. All digits have to be different.
32
Congratulations! Correct number was 32. You found it out in 3 attempts!

Would you like to try again with a new number?
Input 'y' if yes. Otherwise input any other key.
n

Program finished. Thanks for playing!

*/
