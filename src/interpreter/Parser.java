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

  private TokenStream tokenStream;
  private Token currentToken;

  Parser() {

  }

  public boolean parse(String input) {

    Tokenizer tk = new Tokenizer(input);
    tokenStream = tk.getTokenStream();

    return parse();
  }

  private boolean parse() {

    boolean valid = false;

    while (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();

      valid = parseStatement();
    }

    if (valid) {
      //System.out.println("Legal Syntax");
      return true;
    }

    //System.out.println("Illegal Syntax");
    return false;

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
      System.out.println("Error! parseAssignment: parseExpression");

      //pushBack previous 2 tokens
      tokenStream.pushBack(2);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

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

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return false;
    }

    //handle epsilon case where ; and ) can follow an expression
    if (currentToken.getValue().equals(";")
            || currentToken.getValue().equals(")")) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return true;
    }

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

    if (!parsePlusSymbol()) {
      if (!parseMinusSymbol()) {
        return false;
      }
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

    //handle epsilon case where ; + - ) can follow a term
    if (currentToken.getValue().equals(";")
            || currentToken.getValue().equals("+")
            || currentToken.getValue().equals("-")
            || currentToken.getValue().equals(")")) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return true;
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
        if (!parseLeftParenthesis()) {
          return false;
        }

        currentToken = tokenStream.getNext();
        if (!parseExpression()) {
          //error()
          System.out.println("Error! parseFactor: parseExpression");

          //pushBack previous token
          tokenStream.pushBack(1);
          //set current token to the previous token
          currentToken = tokenStream.getNext();

          return false;
        }

        currentToken = tokenStream.getNext();
        if (!parseRightParenthesis()) {
          //error()
          System.out.println("Error! parseFactor: parseRightParenthesis");

          //pushBack previous 2 tokens
          tokenStream.pushBack(2);
          //set current token to the previous token
          currentToken = tokenStream.getNext();
          return false;
        }
      }
      return true;
    }

    return true;
  }

  /* Terminal parsing functions */
  public boolean parseLeftParenthesis() {
    if (currentToken.getValue().equals("(")) {
      return true;
    }

    return false;
  }

  public boolean parseRightParenthesis() {
    if (currentToken.getValue().equals(")")) {
      return true;
    }

    return false;
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
}
