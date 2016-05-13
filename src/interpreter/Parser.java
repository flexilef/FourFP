/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flex
 */
public class Parser {

  private List<Token> tokens;
  private TokenStream tokenStream;
  private Token currentToken;

  Parser() {
    tokens = new ArrayList();
    tokens.add(new Token("BasicType", "int"));
    tokens.add(new Token("Identifier", "var"));
    tokens.add(new Token("Terminal", "="));
    //tokens.add(new Token("LiteralInteger", "5"));
    tokens.add(new Token("Identifier", "abc"));
    tokens.add(new Token("Terminal", ";"));

    tokenStream = new TokenStream(tokens);
  }

  public void parse() {

    boolean valid = false;

    while (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();

      valid = parseStatement();
    }

    if (valid) {
      System.out.println("Legal Syntax");
    } else {
      System.out.println("Illegal Syntax");
    }
  }

  public boolean parseStatement() {
    if (parseInitialization()) {
      return true;
    }

    return false;
  }

  public boolean parseInitialization() {
    if (parseBasicType()) {
      if (parseAssignment()) {

        return true;
      }
    }

    return false;
  }

  public boolean parseAssignment() {

    if (parseIdentifier()) {
      if (parseEqualSign()) {
        if (parseLiteralInteger() || parseIdentifier()) {
          if (parseSemicolon()) {
            return true;
          }
        }
      }
    }

    return false;
  }
  
  public boolean parseEqualSign() {
    if (currentToken.getValue().equals("=")) {
      currentToken = tokenStream.getNext();
      return true;
    }

    return false;
  }

  public boolean parseLiteralInteger() {
    if (currentToken.getType().equals("LiteralInteger")) {
      currentToken = tokenStream.getNext();
      return true;
    }

    return false;
  }
  
  public boolean parseSemicolon() {
    if(currentToken.getValue().equals(";")) {
      //currentToken = tokenStream.getNext();
      return true;
    }
    
    return false;
  }

  public boolean parseBasicType() {
    if (currentToken.getType().equals("BasicType")) {
      currentToken = tokenStream.getNext();
      return true;
    }

    return false;
  }

  public boolean parseIdentifier() {
    if (currentToken.getType().equals("Identifier")) {
      currentToken = tokenStream.getNext();
      return true;
    }

    return false;
  }

  private boolean same(Token symbol, Token next) {
    boolean match = false;

    if (symbol.equals(next)) {
      currentToken = tokenStream.getNext();
      match = true;
    } else {
      match = false;
    }

    return match;
  }
}
