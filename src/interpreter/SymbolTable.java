import java.util.HashMap;
import java.util.Iterator;

public class SymbolTable {
    private HashMap symbol_table = new HashMap();

    public void put(String key, Object value) {
        symbol_table.put(key, value);
    }

    public Object get(String key)
    { 
        return symbol_table.get(key); 
    }
}