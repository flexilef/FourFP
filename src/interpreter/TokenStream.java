/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Flex
 */
public class TokenStream {
  
  private List<Token> tokens;
  private int position;
  
  TokenStream() {
    tokens = new ArrayList();
    position = 0;
  }
  
  TokenStream(List<Token> tokens) {
    
    this.tokens = new ArrayList();

    //create a copy
    for(Iterator it = tokens.iterator(); it.hasNext();) {
      this.tokens.add((Token) it.next());
    }
    
    position = 0;
  }
  
  public boolean hasNext() {
    if(position < this.tokens.size()) {
      return true;
    }
    
    return false;
  }
  
  public Token getNext() {
    Token next = null;
    
    if(hasNext()) {
      next = this.tokens.get(position);
      position++;
    }
    else {
      throw new EmptyTokenStreamRuntimeException("TokenStream went past last element in stream");
    }
    
    return next;
  }
  
  public void setTokens(List<Token> tokens) {
    
    //reset
    this.tokens.clear();
    position = 0;
    
    for(Iterator it = tokens.iterator(); it.hasNext();) {
      this.tokens.add((Token) it.next());
    }
  }
}
