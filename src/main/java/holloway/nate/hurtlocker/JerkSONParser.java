package holloway.nate.hurtlocker;

import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Created by nathanielholloway on 10/17/16.
 * this class is the parser
 */
class JerkSONParser {

    private String pattern = "(\\w+):([^\\d]\\w+)|(\\w+):(\\d+.\\d+/?([0-9]{4})?)";
    private Pattern pattern1 = Pattern.compile(pattern);
    private String patternValidator = "(;\\w+)|()";
    private Pattern invalidPattern = Pattern.compile(patternValidator);
    private int errorCount = 0;
    private List<GroceryItems> groceryObjects = new ArrayList<>();
    private HashSet<GroceryItems> itemSet = new HashSet<>();


    String readRawDataToString() throws Exception {
        Path path;
        String string = null;
        try {
            path = get(ClassLoader.getSystemResource("RawData.txt").toURI());
            string = new String(readAllBytes(get(path.toUri())));
        } catch (Exception e) {
            System.out.println("Can not get path.");
        }
        return string;
    }

    ArrayList<Matcher> splitObjects(String str) {
        ArrayList<Matcher> matchList = new ArrayList<>();
        for (String obj : str.split("##")) {
            Matcher matched = pattern1.matcher(obj);
            matchList.add(matched);
        }
            return matchList;
    }

   void matchElements(List<Matcher> matcherArrayList) {

       for (Matcher match : matcherArrayList) {

           GroceryItems groceryItem = GroceryItems.createGroceryItem();

           if (match.find()) {
               String[] s = match.group().split(":");
               String name = switchName(s[1].toUpperCase().toCharArray()[0]);
               groceryItem.setName(name);
           }
           if (match.find()) {
               String[] s = match.group().split(":");
               String price = s[1];
               groceryItem.setPrice(price);
           }
           if (match.find()) {
               String[] s = match.group().split(":");
               String type = s[1].toUpperCase();
               groceryItem.setType(type);
           }
           if (match.find()) {
               String[] s = match.group().split(":");
               String expiration = s[1];
               groceryItem.setExpiration(expiration);
           }

           if (isValidObject(groceryItem)) {
               groceryObjects.add(groceryItem);

               addPriceToList(groceryItem);}
           }
       }


    private String switchName(Character character){
        switch (character){
            case 'C':
                return "Cookies";
            case 'B':
                return "Bread";
            case 'A':
                return "Apples";
            case 'M':
                return "Milk";
        }
        return "";
    }



    public boolean isValidObject(GroceryItems groceryItem){

        boolean isValid = true;
        try {
            Matcher m1 = invalidPattern.matcher(groceryItem.getName());
            Matcher m2 = invalidPattern.matcher(groceryItem.getPrice());
            Matcher m3 = invalidPattern.matcher(groceryItem.getType());
            Matcher m4 = invalidPattern.matcher(groceryItem.getExpiration());
        } catch (Exception e){
            errorCount++;
            isValid =false;
        }

        return isValid;
    }

    private int calculateTimesSeen(String attribute){
        int timesSeen = 0;
        for (GroceryItems item : groceryObjects) {
            if(attribute.equals(item.getName())){
                timesSeen++;
            }
        }
        return timesSeen;
    }

    private int calculateTimesSeen(String nameAttribute, String priceAttribute){
        int timesSeen = 0;
        for (GroceryItems item : groceryObjects) {
            if(priceAttribute.equals(item.getPrice()) && nameAttribute.equals(item.getName())){
                timesSeen++;
            }
        }
        return timesSeen;
    }


    private boolean addPriceToList(GroceryItems groceryItem) {

        boolean nameAlreadyAdded = false;
        boolean priceAlreadyAdded = false;
        boolean itemAddedToList = false;

        for (GroceryItems grocery : itemSet) {

            if (grocery.getName().equals(groceryItem.getName())) {
                nameAlreadyAdded = true;

                for (int i = 0; i < grocery.priceList.size(); i++) {
                    if (grocery.priceList.get(i).equals(groceryItem.getPrice())) {
                        priceAlreadyAdded = true;
                        break;
                    }
                }
                if (!priceAlreadyAdded) {
                    grocery.priceList.add(groceryItem.getPrice());
                    itemAddedToList = true;
                }
            }
        }

        if (!nameAlreadyAdded) {
            itemSet.add(groceryItem);
            groceryItem.priceList.add(groceryItem.getPrice());
            itemAddedToList = true;
        }

        return itemAddedToList;
    }

    void displayResults() {
        String times = "Times seen:";


        for (GroceryItems itemInSet : itemSet) {
            int nameCount;
            int priceCount;
            System.out.printf("Name: %3s", itemInSet.getName());
            nameCount = calculateTimesSeen(itemInSet.getName());

            System.out.printf("%19s %3d\n", times, nameCount);
            System.out.println("============\t\t=============");


            for (String itemPrice : itemInSet.priceList) {
                priceCount = calculateTimesSeen(itemInSet.getName(), itemPrice);
                System.out.printf("Price: %3s",itemPrice);
                System.out.printf("%20s %3d\n", times, priceCount);
                System.out.println("------------\t\t------------\n");
            }
        }
        errorCount -= 1;
        System.out.printf("Error seen: %15s%3s\n\n", times, errorCount);
    }

}