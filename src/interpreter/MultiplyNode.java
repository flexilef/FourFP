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
public class MultiplyNode extends BinaryOperatorNode {

  MultiplyNode(ASTreeNode left, ASTreeNode right) {
    this.left = left;
    this.right = right;

    nodeType = "MultiplyNode";
  }

  public void setLeftNode(ASTreeNode node) {
    left = node;
  }

  public ASTreeNode getLeftNode() {
    return left;
  }

  public void setRightNode(ASTreeNode node) {
    right = node;
  }

  public ASTreeNode getRightNode() {
    return right;
  }
}
