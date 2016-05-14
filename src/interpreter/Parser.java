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
    tokens.add(new Token("LiteralInteger", "5"));
    //tokens.add(new Token("Identifier", "abc"));
    //tokens.add(new Token("LiteralInteger", "1"));
    tokens.add(new Token("BinaryOperator", "+"));
    //tokens.add(new Token("LiteralInteger", "2"));
    tokens.add(new Token("Identifier", "cde"));
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
    if (!parseInitialization()) {
      if (!parseAssignment()) {
        return false;
      }
      return true;
    }

    return true;
  }

  public boolean parseInitialization() {
    if (!parseBasicType()) {
      return false;
    }

    currentToken = tokenStream.getNext();
    if (!parseAssignment()) {
      System.out.println("Error! parseInitialization: parseAssignment");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseAssignment() {

    if (!parseIdentifier()) {
      return false;
    }

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return false;
    }
    if (!parseEqualSymbol()) {
      System.out.println("Error! parseAssignment: parseEqualSymbol");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    currentToken = tokenStream.getNext();
    if (!parseExpression()) {
      //pushBack previous 2 tokens
      tokenStream.pushBack(2);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }
    /*
     currentToken = tokenStream.getNext();
     if (!parseExpression()) {
     if (!parseLiteralInteger()) {
     if (!parseIdentifier()) {
     //pushBack previous 2 tokens
     tokenStream.pushBack(2);
     //set current token to the previous token
     currentToken = tokenStream.getNext();

     return false;
     }
     }
     }
     */

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseAssignment: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseExpression() {

    if (!parseTerm()) {
      return false;
    }

    currentToken = tokenStream.getNext();
    if (!parseExpressionFactored()) {
      //pushback one tokens
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();
      return false;
    }

    return true;
  }

  public boolean parseExpressionFactored() {

    //epsilon
    if (tokenStream.hasNext()) {
      if (!parsePlusSymbol()) {
        if (!parseMinusSymbol()) {
          return false;
        }
      }
    } else {
      //token push back last token because we are at the end
      //pushback one token
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();

      //found epsilon
      return true;
    }

    currentToken = tokenStream.getNext();
    if (!parseExpression()) {
      //pushback one token
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseTerm() {

    if (!parseFactor()) {
      return false;
    }

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return false;
    }
    if (!parseTermFactored()) {
      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseTermFactored() {

    //cheating: to apply the epsilon rule, we cheat knowing that 
    //if term were replaced by factor (because of epsilon),
    //then the following symbols can follow term/factor:
    //; if Expression -> Term ;
    //+ or - Term -> ExpressionFactored -> + | -
    if (currentToken.getValue().equals("+")
            || currentToken.getValue().equals("-")
            || currentToken.getValue().equals(";")) {

      //push back one token because we didn't consume it, we just peeked
      tokenStream.pushBack(1);
      currentToken = tokenStream.getNext();

      return true;
    }

    if (!parseMultiplySymbol()) {
      if (!parseDivideSymbol()) {
        return false;
      }
    }

    currentToken = tokenStream.getNext();
    if (!parseTerm()) {
      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseFactor() {

    if (!parseLiteralInteger()) {
      if (!parseIdentifier()) {
        return false;
      }
      return true;
    }

    return true;
  }

  public boolean parseDivideSymbol() {
    if (currentToken.getValue().equals("/")) {
      return true;
    }

    return false;
  }

  public boolean parseMultiplySymbol() {
    if (currentToken.getValue().equals("*")) {
      return true;
    }

    return false;
  }

  public boolean parsePlusSymbol() {
    if (currentToken.getValue().equals("+")) {
      return true;
    }

    return false;
  }

  public boolean parseMinusSymbol() {
    if (currentToken.getValue().equals("-")) {
      return true;
    }

    return false;
  }

  public boolean parseEqualSymbol() {
    if (currentToken.getValue().equals("=")) {
      return true;
    }

    return false;
  }

  public boolean parseBasicType() {
    if (currentToken.getType().equals("BasicType")) {
      return true;
    }

    return false;
  }

  /* Terminal parsing functions */
  public boolean parseSemicolon() {
    if (currentToken.getValue().equals(";")) {
      return true;
    }

    return false;
  }

  public boolean parseLiteralInteger() {
    if (currentToken.getType().equals("LiteralInteger")) {
      return true;
    }

    return false;
  }

  public boolean parseIdentifier() {
    if (currentToken.getType().equals("Identifier")) {
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
