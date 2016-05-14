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
public class TokenizerTest extends UnitTest {

  private Tokenizer testObject;

  TokenizerTest() {
    super();

    testObject = new Tokenizer();
  }

  @Override
  public void runTests() {

    checkTokenize_ReturnTokenStream("int", "{{BasicType:int}}", "tokenize(): TokenStream: basic type");
    checkTokenize_ReturnTokenStream("int ", "{{BasicType:int}}", "tokenize(): TokenStream: ends with space");
    checkTokenize_ReturnTokenStream("circle", "{{Command:circle}}", "tokenize(): TokenStream: circle command");
    checkTokenize_ReturnTokenStream("rect", "{{Command:rect}}", "tokenize(): TokenStream: rect command");
    checkTokenize_ReturnTokenStream("(", "{{Separator:(}}", "tokenize(): TokenStream: '(' separator");
    checkTokenize_ReturnTokenStream(")", "{{Separator:)}}", "tokenize(): TokenStream: ')' separator");
    checkTokenize_ReturnTokenStream("#", "{{Comment:#}}", "tokenize(): TokenStream: '#' separator");
    checkTokenize_ReturnTokenStream("+", "{{BinaryOperator:+}}", "tokenize(): TokenStream: + binary operator");
    checkTokenize_ReturnTokenStream("-", "{{BinaryOperator:-}}", "tokenize(): TokenStream: - binary operator");
    checkTokenize_ReturnTokenStream("/", "{{BinaryOperator:/}}", "tokenize(): TokenStream: / binary operator");
    checkTokenize_ReturnTokenStream("*", "{{BinaryOperator:*}}", "tokenize(): TokenStream: * binary operator");
    checkTokenize_ReturnTokenStream("=", "{{BinaryOperator:=}}", "tokenize(): TokenStream: = binary operator");
    checkTokenize_ReturnTokenStream("var", "{{Identifier:var}}", "tokenize(): TokenStream: identifier");
    checkTokenize_ReturnTokenStream("int var = 5 ;", "{{BasicType:int}{Identifier:var}{BinaryOperator:=}{LiteralInteger:5}{Separator:;}}",
            "tokenize(): TokenStream: multiple tokens");
    checkTokenize_ReturnTokenStream("  int  var = 5 ;  ", "{{BasicType:int}{Identifier:var}{BinaryOperator:=}{LiteralInteger:5}{Separator:;}}",
            "tokenize(): TokenStream: multiple whitespace");
    checkTokenize_ReturnTokenStream("int var = 5 ; int a = 6 ;", "{{BasicType:int}{Identifier:var}{BinaryOperator:=}{LiteralInteger:5}{Separator:;}"
            + "{BasicType:int}{Identifier:a}{BinaryOperator:=}{LiteralInteger:6}{Separator:;}}",
            "tokenize(): TokenStream: multiple statements");

    System.out.println("TokenizerTest tests run: " + getTotalTestsRun());
    System.out.println("TokenizerTest tests failed: " + getTotalTestsFailed());
    System.out.println("---------------------------------------------------");
  }

  private void checkTokenize_ReturnTokenStream(String input, String expectedOutput, String error) {
    totalTestsRun++;

    testObject.setInput(input);
    TokenStream stream = testObject.getTokenStream();
    String actualOutput = stream.toString();

    if (!actualOutput.equals(expectedOutput)) {
      totalTestsFailed++;

      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + input + "' : Expected - '" + expectedOutput + "' : Actual - '" + actualOutput + "'");
    }
  }
}
