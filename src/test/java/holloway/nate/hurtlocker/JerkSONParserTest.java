package holloway.nate.hurtlocker;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nathanielholloway on 10/17/16.
 * This is the test class for the JerkSON parser.
 */
public class JerkSONParserTest {
    @Test
    public void isValidObjectTest(){
        JerkSONParser jParser = new JerkSONParser();
        GroceryItems groceryItem = GroceryItems.createGroceryItem();
        boolean actual = jParser.isValidObject(groceryItem);
        Assert.assertFalse(actual);
    }

    @Test
    public void createGroceryItemTest(){
        GroceryItems groceryItem = GroceryItems.createGroceryItem();
        Assert.assertNotNull(groceryItem);
    }

    @Test
    public void calculateTimesSeenTest(){

        //calculateTimesSeenTest("Milk");
    }

    @Test
    public void splitObjectsTest(){

    }

    @Test
    public void switchNameTest(){

    }
}
