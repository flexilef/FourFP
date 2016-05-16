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

    /*
     System.out.println(litint.toString());
     System.out.println(declint.toString());
     System.out.println(assnode.toString());

     System.out.println(interpret(assnode));
     */
  }

  public static int interpret(ASTreeNode root) {

    if (root.nodeType.equals("Assignment")) {
      int right = interpret(((AssignmentNode) root).right);
      //set the symbol table with left.name and int type with right value
      //          t->leftchild->symbtable_entry->value=right_value;
      System.out.println("Initialized " + ((DeclarationNode) ((AssignmentNode) root).left).identifier + " to " + right);

      return right;
    } else if (root.nodeType.equals("LiteralInteger")) {
      return ((LiteralIntegerNode) root).value;
    }

    return 0;
  }
}
