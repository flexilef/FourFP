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
public class BinaryOperatorNode extends ASTreeNode {

  public ASTreeNode left;
  public ASTreeNode right;

  public BinaryOperatorNode() {
    this.left = null;
    this.right = null;
    nodeType = "BinaryOp";
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

  @Override
  public String toString() {
    return "{" + "["+nodeType+"]" + ":" + "LEFTNODE:" + left.toString() + "-RIGHTNODE:" + right.toString() + "}";
  }
}
