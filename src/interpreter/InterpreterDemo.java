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
public class InterpreterDemo {

  public static void main(String[] args) {

    UnitTest tests[] = new UnitTest[2];
    tests[0] = new CharStreamTest();
    tests[1] = new TokenStreamTest();

    for (UnitTest test : tests) {
      test.runTests();
    }
  }
}
