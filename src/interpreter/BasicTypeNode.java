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
public class BasicTypeNode extends ASTreeNode {

  public String type;

  BasicTypeNode(String type) {
    this.type = type;
    nodeType = "BasicType";
  }

  @Override
  public String toString() {
    return "{" + nodeType + ":" + type + "}";
  }
}
