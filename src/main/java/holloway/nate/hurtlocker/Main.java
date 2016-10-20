package holloway.nate.hurtlocker;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by nathanielholloway on 10/17/16.
 * This program is for reading a Jerk file
 */
public class Main {


    public static void main(String[] args) throws Exception {
        JerkSONParser jParser = new JerkSONParser();
        String str = "";
        try {
           str = jParser.readRawDataToString();
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Matcher> matcherArrayList = jParser.splitObjects(str);

        jParser.matchElements(matcherArrayList);

        jParser.displayResults();




    }


}