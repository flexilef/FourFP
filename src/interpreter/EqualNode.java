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
public class EqualNode extends BinaryOperatorNode {

  EqualNode(ASTreeNode left, ASTreeNode right) {
    this.left = left;
    this.right = right;

    nodeType = "EqualNode";
  }
}
