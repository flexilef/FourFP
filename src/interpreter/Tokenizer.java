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
public class Tokenizer {
  
  private CharStream input;
  private TokenStream output;
  
  Tokenizer(String input) {
    //convert to CharStream
    //call tokenize()
  }  
  
  Tokenizer(CharStream input) {
    //call tokenize()
  }
  
  public TokenStream getTokenStream() {
    return output;
  }
  
  private void tokenize() {
    //tokenize the input
  }
}
