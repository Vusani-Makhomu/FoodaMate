import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("Fetching data from API...");
        try(java.io.InputStream is = new java.net.URL("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/").openStream()) {
            String contents = new String(is.readAllBytes());
            JsonElement element = JsonParser.parseString(contents);
            Object[] apiData = element.getAsJsonObject().entrySet().toArray();
            System.out.println("Printing out datat...");

            for (int index=0; index<apiData.length;index++) {
                System.out.println(apiData[index]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
