/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.ASTNodes.ASTreeNode;
import interpreter.ASTNodes.MultiplyNode;
import interpreter.ASTNodes.DeclarationNode;
import interpreter.ASTNodes.PlusNode;
import interpreter.ASTNodes.EqualNode;
import interpreter.ASTNodes.IdentifierNode;
import interpreter.ASTNodes.RectCommandNode;
import interpreter.ASTNodes.MinusNode;
import interpreter.ASTNodes.CircleCommandNode;
import interpreter.ASTNodes.DivideNode;
import interpreter.ASTNodes.LiteralIntegerNode;

/**
 *
 * @author Flex
 */
public class Interpreter {

  private ParserTree parser;
  private SymbolTable symbolTable;

  public Interpreter() {
    parser = new ParserTree();
    symbolTable = SymbolTable.getInstance();
  }

  public void interpret(String input) {
    ASTreeNode root = parser.parse(input);

    interpret(root);
  }

  private ASTreeNode interpret(ASTreeNode node) {

    if (node.nodeType.equals("CircleCommandNode")) {

      int x = 0, y = 0, r = 0, s = 0;

      CircleCommandNode circleNode = (CircleCommandNode) node;
      ASTreeNode arg1Node = interpret(circleNode.arguments[0]);
      ASTreeNode arg2Node = interpret(circleNode.arguments[1]);
      ASTreeNode arg3Node = interpret(circleNode.arguments[2]);
      ASTreeNode arg4Node = interpret(circleNode.arguments[3]);

      if (arg1Node.nodeType.equals("IdentifierNode")) {
        System.out.println(arg1Node.nodeType);
        x = symbolTable.get(((IdentifierNode) arg1Node).name);
      } else if (arg1Node.nodeType.equals("LiteralIntegerNode")) {
        x = ((LiteralIntegerNode) arg1Node).value;
      }

      if (arg2Node.nodeType.equals("IdentifierNode")) {
        y = symbolTable.get(((IdentifierNode) arg2Node).name);
      } else if (arg2Node.nodeType.equals("LiteralIntegerNode")) {
        y = ((LiteralIntegerNode) arg2Node).value;
      }

      if (arg3Node.nodeType.equals("IdentifierNode")) {
        r = symbolTable.get(((IdentifierNode) arg3Node).name);
      } else if (arg3Node.nodeType.equals("LiteralIntegerNode")) {
        r = ((LiteralIntegerNode) arg3Node).value;
      }

      if (arg4Node.nodeType.equals("IdentifierNode")) {
        s = symbolTable.get(((IdentifierNode) arg4Node).name);
      } else if (arg4Node.nodeType.equals("LiteralIntegerNode")) {
        s = ((LiteralIntegerNode) arg4Node).value;
      }

      drawCircle(x, y, r, s);
    } else if (node.nodeType.equals("RectCommandNode")) {

      int x1 = 0, y1 = 0, x2 = 0, y2 = 0, s = 0;

      RectCommandNode rectNode = (RectCommandNode) node;
      ASTreeNode arg1Node = interpret(rectNode.arguments[0]);
      ASTreeNode arg2Node = interpret(rectNode.arguments[1]);
      ASTreeNode arg3Node = interpret(rectNode.arguments[2]);
      ASTreeNode arg4Node = interpret(rectNode.arguments[3]);
      ASTreeNode arg5Node = interpret(rectNode.arguments[4]);

      if (arg1Node.nodeType.equals("IdentifierNode")) {
        x1 = symbolTable.get(((IdentifierNode) arg1Node).name);
      } else if (arg1Node.nodeType.equals("LiteralIntegerNode")) {
        x1 = ((LiteralIntegerNode) arg1Node).value;
      }

      if (arg2Node.nodeType.equals("IdentifierNode")) {
        y1 = symbolTable.get(((IdentifierNode) arg2Node).name);
      } else if (arg2Node.nodeType.equals("LiteralIntegerNode")) {
        y1 = ((LiteralIntegerNode) arg2Node).value;
      }

      if (arg3Node.nodeType.equals("IdentifierNode")) {
        x2 = symbolTable.get(((IdentifierNode) arg3Node).name);
      } else if (arg3Node.nodeType.equals("LiteralIntegerNode")) {
        x2 = ((LiteralIntegerNode) arg3Node).value;
      }

      if (arg4Node.nodeType.equals("IdentifierNode")) {
        y2 = symbolTable.get(((IdentifierNode) arg4Node).name);
      } else if (arg4Node.nodeType.equals("LiteralIntegerNode")) {
        y2 = ((LiteralIntegerNode) arg4Node).value;
      }

      if (arg5Node.nodeType.equals("IdentifierNode")) {
        s = symbolTable.get(((IdentifierNode) arg5Node).name);
      } else if (arg4Node.nodeType.equals("LiteralIntegerNode")) {
        s = ((LiteralIntegerNode) arg4Node).value;
      }

      drawRectangle(x1, y1, x2, y2, s);
    } else if (node.nodeType.equals("EqualNode")) {

      EqualNode equalNode = (EqualNode) node;
      LiteralIntegerNode rightNode = null;
      String identifier = "";
      int value;

      if (equalNode.left.nodeType.equals("DeclarationNode")) {
        identifier = ((DeclarationNode) (equalNode.left)).identifier;

      } else if (equalNode.left.nodeType.equals("IdentifierNode")) {
        identifier = ((IdentifierNode) (equalNode.left)).name;
      }

      rightNode = (LiteralIntegerNode) interpret(equalNode.right);
      value = rightNode.value;

      //add entry to the symboltable
      symbolTable.put(identifier, value);

      System.out.println("initialized '" + identifier + "' to '" + value + "'");
    } else if (node.nodeType.equals("PlusNode")) {

      PlusNode plusNode = (PlusNode) node;

      ASTreeNode leftNode = interpret(plusNode.left);
      ASTreeNode rightNode = interpret(plusNode.right);

      int left = 0, right = 0;

      if (leftNode.nodeType.equals("IdentifierNode")) {
        left = symbolTable.get(((IdentifierNode) leftNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        left = ((LiteralIntegerNode) leftNode).value;
      }

      if (rightNode.nodeType.equals("IdentifierNode")) {
        right = symbolTable.get(((IdentifierNode) rightNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        right = ((LiteralIntegerNode) rightNode).value;
      }

      int result = left + right;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("MinusNode")) {

      MinusNode minusNode = (MinusNode) node;

      ASTreeNode leftNode = interpret(minusNode.left);
      ASTreeNode rightNode = interpret(minusNode.right);

      int left = 0, right = 0;

      if (leftNode.nodeType.equals("IdentifierNode")) {
        left = symbolTable.get(((IdentifierNode) leftNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        left = ((LiteralIntegerNode) leftNode).value;
      }

      if (rightNode.nodeType.equals("IdentifierNode")) {
        right = symbolTable.get(((IdentifierNode) rightNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        right = ((LiteralIntegerNode) rightNode).value;
      }

      int result = left - right;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("MultiplyNode")) {

      MultiplyNode multiplyNode = (MultiplyNode) node;

      ASTreeNode leftNode = interpret(multiplyNode.left);
      ASTreeNode rightNode = interpret(multiplyNode.right);

      int left = 0, right = 0;

      if (leftNode.nodeType.equals("IdentifierNode")) {
        left = symbolTable.get(((IdentifierNode) leftNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        left = ((LiteralIntegerNode) leftNode).value;
      }

      if (rightNode.nodeType.equals("IdentifierNode")) {
        right = symbolTable.get(((IdentifierNode) rightNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        right = ((LiteralIntegerNode) rightNode).value;
      }

      int result = left * right;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("DivideNode")) {

      DivideNode divideNode = (DivideNode) node;

      ASTreeNode leftNode = interpret(divideNode.left);
      ASTreeNode rightNode = interpret(divideNode.right);

      int left = 0, right = 1;

      if (leftNode.nodeType.equals("IdentifierNode")) {
        left = symbolTable.get(((IdentifierNode) leftNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        left = ((LiteralIntegerNode) leftNode).value;
      }

      if (rightNode.nodeType.equals("IdentifierNode")) {
        right = symbolTable.get(((IdentifierNode) rightNode).name);
      } else if (leftNode.nodeType.equals("LiteralIntegerNode")) {
        right = ((LiteralIntegerNode) rightNode).value;
      }

      int result = 0;
      if (right != 0) {
        result = left / right;
      }

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("LiteralIntegerNode")) {

      return node;
    } else if (node.nodeType.equals("IdentifierNode")) {

      int value = symbolTable.get(((IdentifierNode) node).name);

      return new LiteralIntegerNode(value);
    }

    return null;
  }

  private void drawCircle(int x, int y, int r, int style) {
    System.out.println("Drawing circle: \nx:" + x + "\ny:" + y
            + "\nradius:" + r + "\nstyle:" + style);
  }

  private void drawRectangle(int x1, int y1, int x2, int y2, int style) {
    System.out.println("Drawing rectangle: \nx1:" + x1 + "\ny1:" + y1
            + "\nx2::" + x2 + "\ny2:" + y2 + "\nstyle:" + style);
  }
}
