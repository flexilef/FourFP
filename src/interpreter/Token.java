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
public class Token {
  
  private String name;
  private String value;
  
  Token(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  public String getType() {
    return "";
  }
  
  public String getValue() {
    return "";
  }
}
