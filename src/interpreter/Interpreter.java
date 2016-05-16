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

      CircleCommandNode circleNode = (CircleCommandNode) node;
      ASTreeNode arg1Node = interpret(circleNode.arguments[0]);
      ASTreeNode arg2Node = interpret(circleNode.arguments[1]);
      ASTreeNode arg3Node = interpret(circleNode.arguments[2]);
      ASTreeNode arg4Node = interpret(circleNode.arguments[3]);

      int x = ((LiteralIntegerNode) arg1Node).value;
      int y = ((LiteralIntegerNode) arg2Node).value;
      int r = ((LiteralIntegerNode) arg3Node).value;
      int style = ((LiteralIntegerNode) arg4Node).value;

      drawCircle(x, y, r, style);
    } else if (node.nodeType.equals("RectCommandNode")) {

      RectCommandNode rectNode = (RectCommandNode) node;
      LiteralIntegerNode arg1Node = (LiteralIntegerNode) interpret(rectNode.arguments[0]);
      LiteralIntegerNode arg2Node = (LiteralIntegerNode) interpret(rectNode.arguments[1]);
      LiteralIntegerNode arg3Node = (LiteralIntegerNode) interpret(rectNode.arguments[2]);
      LiteralIntegerNode arg4Node = (LiteralIntegerNode) interpret(rectNode.arguments[3]);
      LiteralIntegerNode arg5Node = (LiteralIntegerNode) interpret(rectNode.arguments[4]);

      int x1 = arg1Node.value;
      int y1 = arg2Node.value;
      int x2 = arg3Node.value;
      int y2 = arg4Node.value;
      int style = arg5Node.value;

      drawRectangle(x1, y1, x2, y2, style);
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
      LiteralIntegerNode leftNode = (LiteralIntegerNode) interpret(plusNode.left);
      LiteralIntegerNode rightNode = (LiteralIntegerNode) interpret(plusNode.right);

      int result = leftNode.value + rightNode.value;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("MinusNode")) {

      MinusNode minusNode = (MinusNode) node;
      LiteralIntegerNode leftNode = (LiteralIntegerNode) interpret(minusNode.left);
      LiteralIntegerNode rightNode = (LiteralIntegerNode) interpret(minusNode.right);

      int result = leftNode.value - rightNode.value;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("MultiplyNode")) {

      MultiplyNode multiplyNode = (MultiplyNode) node;
      LiteralIntegerNode leftNode = (LiteralIntegerNode) interpret(multiplyNode.left);
      LiteralIntegerNode rightNode = (LiteralIntegerNode) interpret(multiplyNode.right);

      int result = leftNode.value * rightNode.value;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("DivideNode")) {

      DivideNode divideNode = (DivideNode) node;
      LiteralIntegerNode leftNode = (LiteralIntegerNode) interpret(divideNode.left);
      LiteralIntegerNode rightNode = (LiteralIntegerNode) interpret(divideNode.right);

      int result = leftNode.value * rightNode.value;

      return new LiteralIntegerNode(result);
    } else if (node.nodeType.equals("LiteralInteger")) {

      return node;
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
