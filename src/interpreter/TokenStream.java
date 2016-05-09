/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Flex
 */
public class TokenStream {
  
  private List<Token> stream;
  int position;
  
  TokenStream() {
    stream = new ArrayList();
    position = 0;
  }
  
  TokenStream(String input) {
    
  }
  
  public boolean hasNext() {
    return false;
  }
  
  public Token getNext() {
    return null;
  }
}
