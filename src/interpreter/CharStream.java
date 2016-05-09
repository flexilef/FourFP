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
public class CharStream {
  
  private List<Character> stream;
  private int position;
  
  CharStream() {
    stream = new ArrayList();
    position = 0;
  }
  
  CharStream(String input) {
    
  }
  
  public boolean hasNext() {
    return false;
  }
  
  public char getNext() {
    return 'a';
  }
}
