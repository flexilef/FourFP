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
public class AssignmentNode extends ASTreeNode {

  public ASTreeNode right;
  public ASTreeNode left;

  AssignmentNode(ASTreeNode leftNode, ASTreeNode rightNode) {
    nodeType = "Assignment";

    right = rightNode;
    left = leftNode;
  }

  @Override
  public String toString() {
    return "{" + nodeType + ": [Left:" + left.toString() + "]" + "[Right:" + right.toString() + "]";
  }
}
