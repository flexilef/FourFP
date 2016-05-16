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
public class DivideNode extends BinaryOperatorNode {

  DivideNode(ASTreeNode left, ASTreeNode right) {
    if (right != 0) {
      this.left = left;
      this.right = right;
      nodeType = "DivideNode";
    }
    else {
      
    }
  }
}
