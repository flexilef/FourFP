import java.util.HashMap;

public class SymbolTable {
    private HashMap symbol_table = new HashMap();

    public void put(String key, int value) {
        symbol_table.put(key, value);
    }

    public int get(String key)
    { 
        return symbol_table.get(key); 
    }
}

/* Example of SymbolTable being used

SymbolTable symbol_table = new SymbolTable();
symbol_table.put("a" , "100")

// prints out 100
System.out.println(symbol_table.get("a");
*/
