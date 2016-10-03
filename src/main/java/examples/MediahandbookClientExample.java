package examples;

import de.apileipzig.mediahandbook.MediahandbookClient;

/**
 * @author cpieloth
 */
public class MediahandbookClientExample {
    public static void main(String[] args) {
        MediahandbookClient client = new MediahandbookClient();
        // client.setApiKey("my_api_key");
        client.open();

        System.out.println("Company count: " + client.getCompanies().size());
        System.out.println("Branches count: " + client.getBranches().size());
        System.out.println("People count: " + client.getPeople().size());

        client.close();
    }
}
