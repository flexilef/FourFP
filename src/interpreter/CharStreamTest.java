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
public class CharStreamTest extends UnitTest {
  
  private CharStream testObject;
  
  CharStreamTest() {
    super();
    
    testObject = new CharStream();
  } 
  
  @Override
  public void runTests() {
    
    checkGetNext_ReturnFirstChar("abc", 'a', "getNext() : first character : regular");
    checkGetNext_ReturnFirstChar(" abc", ' ', "getNext() : first character : empy char");
    checkGetNext_ReturnFirstChar("1bc", '1', "getNext() : first character : numeral");
    checkGetNext_ReturnFirstChar("a", 'a', "getNext() : first character : single char");
    
    checkHasNext_ReturnsBoolean("abc", true, "hasNext() : boolean : regular");
    checkHasNext_ReturnsBoolean(" ", true, "hasNext() : boolean : empty char");
    checkHasNext_ReturnsBoolean("a", true, "hasNext() : boolean : single char");
    checkHasNext_ReturnsBoolean("", false, "hasNext() : boolean : empty input");

    //checkGetNext_ReturnFirstChar("", ' ', "getNext() : first character : empty input"); should throw an exception and it does

    System.out.println("Total tests run: "+ getTotalTestsRun());
    System.out.println("Total tests failed: "+ getTotalTestsFailed());
  }
  
  private void checkGetNext_ReturnFirstChar(String input, char expectedOutput, String error) {
    
    totalTestsRun++;
    
    testObject.setInput(input);
    char actualOutput = testObject.getNext();
    
    if(actualOutput != expectedOutput) {
      
      totalTestsFailed++;
      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + input + "' : Expected - '" + expectedOutput + "' : Actual - '" + actualOutput + "'");
    }
  }
  
  private void checkHasNext_ReturnsBoolean(String input, boolean expectedOutput, String error) {
    
    totalTestsRun++;
    
    testObject.setInput(input);
   
    boolean actualOutput = testObject.hasNext();
    
    if(actualOutput != expectedOutput) {
      
      totalTestsFailed++;
      System.out.println("Failed: " + error);
      System.out.println("  Input - '" + input + "' : Expected - '" + expectedOutput + "' : Actual - '" + actualOutput + "'");
    }
  }
}
