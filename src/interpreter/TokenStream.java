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
    for (Iterator it = tokens.iterator(); it.hasNext();) {
      this.tokens.add((Token) it.next());
    }

    position = 0;
  }

  public boolean hasNext() {
    if (position < this.tokens.size()) {
      return true;
    }

    return false;
  }

  public Token getNext() {
    Token next = null;

    if (hasNext()) {
      next = this.tokens.get(position);
      position++;
    } else {
      throw new TokenStreamRuntimeException("getNext(): Attempted to get token outside the bounds of stream");
    }

    return next;
  }

  public void setTokens(List<Token> tokens) {

    //reset
    this.tokens.clear();
    position = 0;

    for (Iterator it = tokens.iterator(); it.hasNext();) {
      this.tokens.add((Token) it.next());
    }
  }

  //move position, n number of places back
  public void pushBack(int n) {

    if (n < 0) {
      throw new TokenStreamRuntimeException("pushBack(): Attempted to push back negative positions");
    }

    if (position - n - 1 >= 0) {
      //n+1 so position subtracts an extra position. Thus when getNext() is called position points to the intended tokens
      this.position -= n + 1;
    } else {
      throw new TokenStreamRuntimeException("pushBack(): position is negative");
    }
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    Token token;

    sb.append("{");

    for (Iterator it = tokens.iterator(); it.hasNext();) {
      token = (Token) it.next();

      sb.append(token.toString());
    }

    sb.append("}");

    return sb.toString();
  }
}
