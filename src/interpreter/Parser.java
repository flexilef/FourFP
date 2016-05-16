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
public class Parser {

  private TokenStream tokenStream;
  private Token currentToken;

  public Parser() {

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
      return true;
    }

    return false;
  }

  public boolean parseStatement() {
    if (!parseInitialization()) {
      if (!parseAssignment()) {
        if (!parseRectCommand()) {
          if (!parseCircleCommand()) {
            return false;
          }
          return true;
        }
        return true;
      }
      return true;
    }

    return true;
  }

  public boolean parseRectCommand() {

    if (!parseRectSymbol()) {
      return false;
    }

    int argumentCount = 5;
    for (int i = 1; i <= argumentCount; i++) {

      currentToken = tokenStream.getNext();
      if (!parseExpression()) {

        System.out.println("Error! parseRectCommand: parseExpression");

        //pushBack previous token
        tokenStream.pushBack(i);
        //set current token to the previous token
        currentToken = tokenStream.getNext();

        return false;
      }
    }

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseRectCommand: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
    }

    return true;
  }

  public boolean parseCircleCommand() {
    if (!parseCircleSymbol()) {
      return false;
    }

    int argumentCount = 4;
    for (int i = 1; i <= argumentCount; i++) {

      currentToken = tokenStream.getNext();
      if (!parseExpression()) {

        System.out.println("Error! parseCircleCommand: parseExpression");

        //pushBack previous token
        tokenStream.pushBack(i);
        //set current token to the previous token
        currentToken = tokenStream.getNext();

        return false;
      }
    }

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseCircleCommand: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return false;
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

    //handle epsilon case where the following can follow an Expression
    if (parseSemicolon()
            || parseRightParenthesis()
            || parseLeftParenthesis()
            || parseLiteralInteger()
            || parseIdentifier()) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      //returning true means Expression -> Term is true
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

    //handle epsilon case where the following can follow Term
    if (parseSemicolon()
            || parsePlusSymbol()
            || parseMinusSymbol()
            || parseRightParenthesis()
            || parseLeftParenthesis()
            || parseLiteralInteger()
            || parseIdentifier()) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      //returning true means Term -> Factor
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
    return currentToken.getValue().equals("(");
  }

  public boolean parseRightParenthesis() {
    return currentToken.getValue().equals(")");
  }

  public boolean parseDivideSymbol() {
    return currentToken.getValue().equals("/");
  }

  public boolean parseMultiplySymbol() {
    return currentToken.getValue().equals("*");
  }

  public boolean parsePlusSymbol() {
    return currentToken.getValue().equals("+");
  }

  public boolean parseMinusSymbol() {
    return currentToken.getValue().equals("-");
  }

  public boolean parseEqualSymbol() {
    return currentToken.getValue().equals("=");
  }

  public boolean parseBasicType() {
    return currentToken.getType().equals("BasicType");
  }

  public boolean parseSemicolon() {
    return currentToken.getValue().equals(";");
  }

  public boolean parseLiteralInteger() {
    return currentToken.getType().equals("LiteralInteger");
  }

  
public boolean parseIdentifier() {
    return currentToken.getType().equals("Identifier");
  }

  public boolean parseCircleSymbol() {
    return currentToken.getValue().equals("circle");

  }

  public boolean parseRectSymbol() {
    return currentToken.getValue().equals("rect");
  }
}