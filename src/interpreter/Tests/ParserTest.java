/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.Tests;

import interpreter.Parser;


/**
 *
 * @author Flex
 */
public class ParserTest extends UnitTest {

  private Parser testObject;

  public ParserTest() {
    super();

    testObject = new Parser();
  }

  @Override
  public void runTests() {

    //initializations
    checkParse_ReturnBoolean("int var = 5 ;", true, "parse() : boolean : initialization with literal integer");
    checkParse_ReturnBoolean("int var = abc ;", true, "parse() : boolean : initialization with identifier");
    checkParse_ReturnBoolean("int var = 1 + 2 ;", true, "parse() : boolean : initialization with addition expression");
    checkParse_ReturnBoolean("int var = 1 - 2 ;", true, "parse() : boolean : initialization with subtraction expression");
    checkParse_ReturnBoolean("int var = 1 * 2 ;", true, "parse() : boolean : initialization with multiplication expression");
    checkParse_ReturnBoolean("int var = 1 / 2 ;", true, "parse() : boolean : initialization with division expression");
    checkParse_ReturnBoolean("int var = a + b ;", true, "parse() : boolean : initialization with multiple identifiers in expression");
    checkParse_ReturnBoolean("int var = abc + 2 ;", true, "parse() : boolean : initialization with mixed identifiers/literals in expression");
    checkParse_ReturnBoolean("int var = 1 + 2 - 3 * 4 / 5 ;", true, "parse() : boolean : initialization with multiple expressions");
    checkParse_ReturnBoolean("int var = 1 + ( 2 * 3 ) ;", true, "parse() : boolean : initialization with parenthesized expressions (literals)");
    checkParse_ReturnBoolean("int var = a + ( 2 - b ) ;", true, "parse() : boolean : initialization with parenthesized expressions (identifiers)");

    //assignments
    checkParse_ReturnBoolean("var = 5 ;", true, "parse() : boolean : assignment with literal integer");
    checkParse_ReturnBoolean("var = abc ;", true, "parse() : boolean : assignment with identifier");
    checkParse_ReturnBoolean("var = var ;", true, "parse() : boolean : assignment with self");
    checkParse_ReturnBoolean("var = 1 + 2 ;", true, "parse() : boolean : assignment with addition expression");
    checkParse_ReturnBoolean("var = abc + 2 ;", true, "parse() : boolean : assignment with mixed identifiers/literals in expression");
    checkParse_ReturnBoolean("var = abc + def ;", true, "parse() : boolean : assignment with mixed multiple identifiers in expression");
    checkParse_ReturnBoolean("var = ( 1 * 2 ) + 3 ;", true, "parse() : boolean : assignment with parenthesized expressions (literals)");
    checkParse_ReturnBoolean("var = ( a + ( 2 - b ) ) ;", true, "parse() : boolean : assignment with parenthesized expressions (identifiers)");

    //Circle commands
    checkParse_ReturnBoolean("circle 1 2 3 4 ;", true, "parse() : boolean : circle command with literal arguments");
    checkParse_ReturnBoolean("circle a bc def g ;", true, "parse() : boolean : circle command with identifier arguments");
    checkParse_ReturnBoolean("circle 1 ab 2 cd ;", true, "parse() : boolean : circle command with mixed arguments");
    checkParse_ReturnBoolean("circle 1 + 2 a * 4 b - 6 7 / c ;", true, "parse() : boolean : circle command with expressions");
    checkParse_ReturnBoolean("circle ( 1 + 2 ) ( 3 * 4 ) 5 - 6 7 / 8 ;", true, "parse() : boolean : circle command with parentheses");

    //Rect Commands
    checkParse_ReturnBoolean("rect 1 2 3 4 5 ;", true, "parse() : boolean : rect command with literal arguments");
    checkParse_ReturnBoolean("rect a bc def g h ;", true, "parse() : boolean : rect command with identifier arguments");
    checkParse_ReturnBoolean("rect 1 ab 2 cd 3 ;", true, "parse() : boolean : rect command with mixed arguments");
    checkParse_ReturnBoolean("rect ( 1 ) ( ab ) ( ( 3 * c ) ) 4 5 ;", true, "parse() : boolean : rect command with parentheses");

    System.out.println("ParserTest tests run: " + getTotalTestsRun());
    System.out.println("ParserTest tests failed: " + getTotalTestsFailed());
    System.out.println("---------------------------------------------------");
  }

  private void checkParse_ReturnBoolean(String input, boolean expectedOutput, String error) {
    totalTestsRun++;

    boolean actualOutput = testObject.parse(input);

    if (actualOutput != expectedOutput) {
      totalTestsFailed++;

      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + input + "' : Expected - '" + expectedOutput + "' : Actual - '" + actualOutput + "'");
    }
  }
}
