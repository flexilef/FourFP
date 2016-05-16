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
public class IdentifierNode extends ASTreeNode{

  public String name;

  IdentifierNode(String name) {
    this.name = name;
    nodeType = "Identifier";
  }

  @Override
  public String toString() {
    return "[" + nodeType + ":" + name + "]";
  }
}
