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
    String s1 = "int var = 5 ;";
    String s2 = "circle 4 var 6 7 ;";
    String s3 = "var = 666 ;";
    String s4 = "rect var var 6 6 6 ;";

    Interpreter interpreter = new Interpreter();
    interpreter.interpret(s1);
    interpreter.interpret(s2);
    interpreter.interpret(s3);
    interpreter.interpret(s4);
  }
}
