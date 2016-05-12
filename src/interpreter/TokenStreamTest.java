/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Flex
 */
public class TokenStreamTest extends UnitTest {

  private TokenStream testObject;

  TokenStreamTest() {
    super();

    testObject = new TokenStream();
  }

  @Override
  public void runTests() {
    List<Token> tokens = new ArrayList();
    tokens.add(new Token("BasicType", "int"));
    tokens.add(new Token("Identifier", "var"));
    tokens.add(new Token("Command", "Circle"));
    checkGetNext_ReturnFirstToken(tokens, tokens.get(0), "getNext() : first token : regular");

    tokens.clear();
    tokens.add(new Token("BasicType", "int"));
    checkGetNext_ReturnFirstToken(tokens, tokens.get(0), "getNext() : first token : single token");

    tokens.clear();
    tokens.add(new Token("BasicType", "int"));
    tokens.add(new Token("Identifier", "var"));
    tokens.add(new Token("Command", "Circle"));
    checkHasNext_ReturnBoolean(tokens, true, "hasNext() : return boolean : regular");
    
    tokens.clear();
    tokens.add(new Token("BasicType", "int"));
    checkHasNext_ReturnBoolean(tokens, true, "hasNext() : return boolean : single token");

    tokens.clear();
    checkHasNext_ReturnBoolean(tokens, false, "hasNext() : return boolean : empty tokens");

    System.out.println("TokenStreamTest tests run: " + getTotalTestsRun());
    System.out.println("TokenStreamTest tests failed: " + getTotalTestsFailed());
    System.out.println("---------------------------------------------------");
  }

  private void checkGetNext_ReturnFirstToken(List<Token> tokens, Token expectedOutput, String error) {
    totalTestsRun++;

    testObject.setTokens(tokens);
    Token actualOutput = testObject.getNext();
    String tokenListStr = getTokenListString(tokens);
    String expectedOutputStr = getTokenString(expectedOutput);
    String actualOutputStr = getTokenString(actualOutput);

    if (!actualOutput.equals(expectedOutput)) {

      totalTestsFailed++;
      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + tokenListStr + "' : Expected - '" + expectedOutputStr + "' : Actual - '" + actualOutputStr + "'");
    }
  }
  
  private void checkHasNext_ReturnBoolean(List<Token> tokens, boolean expectedOutput, String error) {
    totalTestsRun++;

    testObject.setTokens(tokens);
    String tokenListStr = getTokenListString(tokens);
    boolean actualOutput = testObject.hasNext();
    
    if(actualOutput != expectedOutput) {
      totalTestsFailed++;
      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + tokenListStr + "' : Expected - '" + expectedOutput + "' : Actual - '" + actualOutput + "'");
    }
  }

  private String getTokenListString(List<Token> tokens) {

    StringBuilder sb = new StringBuilder();
    Token token;

    for (Iterator it = tokens.iterator(); it.hasNext();) {
      token = (Token) it.next();

      sb.append("{");
      sb.append(token.getType());
      sb.append(":");
      sb.append(token.getValue());
      sb.append("}");
      sb.append(" ");
    }

    return sb.toString();
  }

  private String getTokenString(Token token) {
    StringBuilder sb = new StringBuilder();

    sb.append("{");
    sb.append(token.getType());
    sb.append(":");
    sb.append(token.getValue());
    sb.append("}");

    return sb.toString();
  }
}
