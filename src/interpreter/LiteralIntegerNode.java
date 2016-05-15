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
public class LiteralIntegerNode extends ASTreeNode {

  public int value;

  LiteralIntegerNode(int value) {
    this.value = value;
    nodeType = "LiteralInteger";
  }

  @Override
  public String toString() {
    return "{" + nodeType + ":" + value + "}";
  }
}
