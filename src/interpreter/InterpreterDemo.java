/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.Tests.ParserTest;
import interpreter.Tests.TokenStreamTest;
import interpreter.Tests.UnitTest;
import interpreter.Tests.TokenizerTest;
import interpreter.Tests.CharStreamTest;
import interpreter.Tests.ParserTreeTest;

/**
 *
 * @author Flex
 */
public class InterpreterDemo {

  public static void main(String[] args) {

    UnitTest tests[] = new UnitTest[5];
    tests[0] = new CharStreamTest();
    tests[1] = new TokenStreamTest();
    tests[2] = new TokenizerTest();
    tests[3] = new ParserTest();
    tests[4] = new ParserTreeTest();

    for (UnitTest test : tests) {
      test.runTests();
    }

    //String statement = "rect ( 1 ) ( 2 + 2 ) ( 3 - ( 3 * 2 ) ) ( 4 * 2 ) 2 ;";
    //String statement = "circle 1 2 3 4 ;";
    String statement = "var = 5 ;";

    Interpreter interpreter = new Interpreter();
    interpreter.interpret(statement);
  }
}
