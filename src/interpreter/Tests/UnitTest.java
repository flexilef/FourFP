/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.Tests;

/**
 *
 * @author Flex
 */
public abstract class UnitTest {
  
  protected int totalTestsRun;
  protected int totalTestsFailed;
  
  public UnitTest() {
    totalTestsRun = 0;
    totalTestsFailed = 0;
  }
  
  public abstract void runTests();
  
  public int getTotalTestsRun() {
    return totalTestsRun;
  }
  
  public int getTotalTestsFailed() {
    return totalTestsFailed;
  }
}
