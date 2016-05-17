/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.Error;

/**
 *
 * @author Flex
 */
public class CharStreamRuntimeException extends RuntimeException {

  public CharStreamRuntimeException() {
    super();
  }
  
  public CharStreamRuntimeException(String message) {
    super(message);
  }
}
