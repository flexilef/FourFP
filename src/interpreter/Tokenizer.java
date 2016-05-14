/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Flex
 */
public class Tokenizer {

  private String input;
  private TokenStream output;

  Tokenizer() {
    output = null;
  }

  Tokenizer(String input) {

    this.input = input;
    tokenize();
  }

  public void setInput(String input) {
    this.input = input;
    tokenize();
  }

  public TokenStream getTokenStream() {
    return output;
  }

  private void tokenize() {

    CharStream inputStream = new CharStream(input);
    StringBuilder sb = new StringBuilder();
    char nextChar;

    List<Token> tokenList = new ArrayList();
    Token token;
    String tokenStr;

    while (inputStream.hasNext()) {
      nextChar = inputStream.getNext();

      if (nextChar != ' ') {
        sb.append(nextChar);
      } else {

        if (sb.length() > 0) {
          tokenStr = sb.toString();

          token = makeToken(tokenStr);
          tokenList.add(token);

          //clear sb
          sb.setLength(0);
        }
      }
    }

    //make last token
    if (sb.length() > 0) {
      tokenStr = sb.toString();

      token = makeToken(tokenStr);
      tokenList.add(token);
    }

    output = new TokenStream(tokenList);
  }

  private Token makeToken(String tokenStr) {

    Token token;

    //Language Symbols Definition
    if (Pattern.matches("int", tokenStr)) {
      token = new Token("BasicType", tokenStr);
    } else if (Pattern.matches("[-+*/=]", tokenStr)) {
      token = new Token("BinaryOperator", tokenStr);
    } else if (Pattern.matches("(circle|rect)", tokenStr)) {
      token = new Token("Command", tokenStr);
    } else if (Pattern.matches("[\\(\\);]", tokenStr)) {
      token = new Token("Separator", tokenStr);
    } else if (Pattern.matches("#", tokenStr)) {
      token = new Token("Comment", tokenStr);
    } else if (Pattern.matches("[a-z]+", tokenStr)) {
      token = new Token("Identifier", tokenStr);
    } else if (Pattern.matches("\\d+", tokenStr)) {
      token = new Token("LiteralInteger", tokenStr);
    } else {
      throw new RuntimeException("Invalid Token Found! '" + tokenStr + "'");
    }

    return token;
  }

  @Override
  public String toString() {

    return output.toString();
  }
}
