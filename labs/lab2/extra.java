import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class extra {

    public static int start() throws IOException, InterruptedException {

        String cookie;
        int high = 100;
        int low = 0;
        int mid = (low + high) / 2;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:3001/?guess=" + mid))
            .GET()
            //.header("Cookie", "SESSID=7")
            .build();

        var client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> asString = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, asString);

        int statusCode = response.statusCode();
        //System.out.println("Status code: " + statusCode);
        HttpHeaders headers = response.headers();
        //System.out.println("Response Headers: " + headers);
        //System.out.println("Cookie: " + getCookie(headers.toString()));
        //System.out.println(response.body());
        cookie = getCookie(headers.toString());

        int tries = 1;
        while (true) {
            //<P>The number is lower</P><P>Number of tries: 2</P><form action=/ method='GET'><input type='text' name='guess'><input type='submit' value='Guess'></form
            String res = response.body().split("</P>")[0];
            if (res.equals("<P>The number is lower")) {
                high = mid - 1;
            } else if ( res.equals("<P>The number is higher")) {
                low = mid + 1;
            } else {
                break;
            }

            mid = (low + high) / 2;


            request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3001/?guess=" + mid))
                .GET()
                .header("Cookie", "SESSID=" + cookie)
                .build();

            response = client.send(request, asString);
            tries++;
        }

        return tries;
    }

    public static String getCookie(String header) {
        return header.split(" ")[3].split("=")[2].split("]")[0];
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        int sum = 0;
        for (int i = 0; i < 100; i++) { 
            Integer result = start();
            sum += result;
        } 

        System.out.println(sum / 100);

            
    } 
}
