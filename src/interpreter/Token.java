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

  private String type;
  private String value;

  public Token(String type, String value) {
    this.type = type;
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public boolean equals(Token rhs) {
    if (rhs == null) {
      return false;
    }

    final Token other = (Token) rhs;

    if (!this.type.equals(other.getType())
            && !this.value.equals(other.getValue()))
    {
      return false;
    }
    
    return true;
  }
  
  @Override
  public String toString() {
    
    StringBuilder sb = new StringBuilder();
    
    sb.append("{");
    sb.append(type);
    sb.append(":");
    sb.append(value);
    sb.append("}");
    
    return sb.toString();
  }
}
