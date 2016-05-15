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
public class DeclarationIntegerNode extends ASTreeNode {
  
  public String name;
  
  DeclarationIntegerNode(String name) {
    nodeType = "DeclarationIntegerNode";
    this.name = name;
  }
  
  @Override
  public String toString() {
    return "{"+ nodeType + name +"}";
  }
}
