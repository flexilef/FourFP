/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author Flex
 */
public class ParserTest extends UnitTest {

  private Parser testObject;

  ParserTest() {
    super();

    testObject = new Parser();
  }

  @Override
  public void runTests() {

    checkParse_ReturnBoolean("int var = 5 ;", true, "parse() : boolean : initialization with literal integer");
    checkParse_ReturnBoolean("int var = abc ;", true, "parse() : boolean : initialization with identifier");
    checkParse_ReturnBoolean("int var = 1 + 2 ;", true, "parse() : boolean : initialization with addition expression");
    checkParse_ReturnBoolean("int var = 1 - 2 ;", true, "parse() : boolean : initialization with subtraction expression");
    checkParse_ReturnBoolean("int var = 1 * 2 ;", true, "parse() : boolean : initialization with multiplication expression");
    checkParse_ReturnBoolean("int var = 1 / 2 ;", true, "parse() : boolean : initialization with division expression");
    checkParse_ReturnBoolean("int var = a + b ;", true, "parse() : boolean : initialization with multiple identifiers in expression");
    checkParse_ReturnBoolean("int var = abc + 2 ;", true, "parse() : boolean : initialization with mixed identifiers/literals in expression");
    checkParse_ReturnBoolean("int var = 1 + 2 - 3 * 4 / 5 ;", true, "parse() : boolean : initialization with multiple expressions");
    checkParse_ReturnBoolean("int var = 1 + ( 2 * 3 ) ;", true, "parse() : boolean : initialization with parenthesized expressions");

    checkParse_ReturnBoolean("var = 5 ;", true, "parse() : boolean : assignment with literal integer");
    checkParse_ReturnBoolean("var = abc ;", true, "parse() : boolean : assignment with identifier");
    checkParse_ReturnBoolean("var = var ;", true, "parse() : boolean : assignment with self");
    checkParse_ReturnBoolean("var = 1 + 2 ;", true, "parse() : boolean : assignment with addition expression");
    checkParse_ReturnBoolean("var = abc + 2 ;", true, "parse() : boolean : assignment with mixed identifiers/literals in expression");
    checkParse_ReturnBoolean("var = abc + def ;", true, "parse() : boolean : assignment with mixed multiple identifiers in expression");

    //checkParse_ReturnBoolean("int var = 1 + ( 2 * 3 ) ;", true, "parse() : boolean : initialization with parenthesized expressions");

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
