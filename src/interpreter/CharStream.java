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
public class CharStream {
  
  private String input;
  private List<Character> output;
  private int position;

  CharStream() {
    output = new ArrayList();
    position = 0;
  }
  
  CharStream(String input) {
    this.input = input;
    output = new ArrayList();
    position = 0;
    
    convertInputToCharStream();
  }
  
  public boolean hasNext() {
    if(position < output.size()) {
      return true;
    }
    
    return false;
  }
  
  public char getNext() {
    char next = '\0';
    
    if(hasNext()) {
      next = output.get(position);
      position++;
    }
    else {
      throw new EmptyCharStreamRuntimeException("CharStream went past last element in stream");
    }
    
    return next;
  }
  
  public void setInput(String input) {
    this.input = input;
    convertInputToCharStream();
  }
  
  private void convertInputToCharStream() {
    
    //clear output collection
    output.clear();
    
    //reset position
    position = 0;
    
    int inputLength = input.length();
    for(int i=0; i < inputLength; i++) {
      output.add(input.charAt(i));
    }
  }
}
