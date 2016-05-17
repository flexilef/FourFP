/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ASTNodes;

/**
 *
 * @author Flex
 */
public class RectCommandNode extends ASTreeNode {

  public ASTreeNode arguments[];
  public int argCount = 5;

  public RectCommandNode(ASTreeNode args[]) {
    nodeType = "RectCommandNode";

    arguments = new ASTreeNode[argCount];

    if (args != null) {
      for (int i = 0; i < argCount; i++) {
        arguments[i] = args[i];
      }
    }
  }

  @Override
  public String toString() {
    return "[" + nodeType + ":ARGUMENTS:" + arguments[0].toString() + ","
            + arguments[1].toString() + ","
            + arguments[2].toString() + ","
            + arguments[3].toString() + ","
            + arguments[4].toString() + "]";
  }
}
