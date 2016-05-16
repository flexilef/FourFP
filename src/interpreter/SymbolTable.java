package interpreter;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

  private Map<String, Integer> symbol_table = new HashMap();
  private static SymbolTable instance = new SymbolTable();

  private SymbolTable() {

  }

  public static SymbolTable getInstance() {
    return instance;
  }

  public void put(String key, int value) {
    symbol_table.put(key, value);
  }

  public int get(String key) {
    return (int) symbol_table.get(key);
  }

  public void clear() {
    symbol_table.clear();
  }
}

/* Example of SymbolTable being used

 SymbolTable symbol_table = new SymbolTable();
 symbol_table.put("a" , "100")

 // prints out 100
 System.out.println(symbol_table.get("a");
 */
