package holloway.nate.hurtlocker;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanielholloway on 10/17/16.
 * This class model's the grocery items in the JerkSON file.
 */
public class GroceryItems {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String itemPrice) {
        price=itemPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration =expiration;
    }

    private String name;
    private String price ;
    private String type;
    private String expiration;
    List<String> priceList = new ArrayList<>();

    static GroceryItems createGroceryItem(){return new GroceryItems();}
    @Override
    public String toString() {
        return "name: " + name + '\n' +
                "price: " + price + '\n' +
                "type: " + type + '\n' +
                "expiration: '" + expiration;
    }
}
