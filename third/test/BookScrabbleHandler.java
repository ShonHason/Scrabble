package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;


public class BookScrabbleHandler implements ClientHandler {
    private DictionaryManager dm;

    // Default constructor
    public BookScrabbleHandler() {
        dm = new DictionaryManager(); // Initialize DictionaryManager
    }

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        String[] args;
        boolean result = false; // Initialize result to a default value

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inFromClient));
             PrintWriter writer = new PrintWriter(outToClient, true)) {

            String clientMessage = reader.readLine();
            if (clientMessage != null) {
                String[] words = clientMessage.split(",");

                if (words.length > 0) {
                    if ("Q".equals(words[0])) {
                        args = Arrays.copyOfRange(words, 1, words.length);
                        result = dm.query(args); // Use instance method
                    } else if ("C".equals(words[0])) {
                        args = Arrays.copyOfRange(words, 1, words.length);
                        result = dm.challenge(args); // Use instance method
                    }
                }
                writer.println(result ? "true" : "false");
            }
        } catch (IOException e) {
          //  System.err.println("Error handling client: " + e.getMessage());
        } finally {
            close(); 
        }
    }

    @Override
    public void close() {
     
    }
}
