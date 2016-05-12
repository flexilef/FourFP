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
class EmptyCharStreamRuntimeException extends RuntimeException {

  public EmptyCharStreamRuntimeException() {
    super();
  }
  
  public EmptyCharStreamRuntimeException(String message) {
    super(message);
  }
}
