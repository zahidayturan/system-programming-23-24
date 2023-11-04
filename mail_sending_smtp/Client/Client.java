import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

public class Client {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final int SMTP_PORT = 465;
    private static final String YOUR_GMAIL_ADDRESS = "mail0@gmail.com"; //Enter not encoded-as-base64 mail address. Will be coded
    private static final String YOUR_APPLICATION_PASSWORD = "aaaa bbbb cccc dddd"; //Enter not encoded-as-base64 password. Will be coded
    private static final String RCPT_TO_GMAIL_ADDRESS = "mail@bil.omu.edu.tr"; //Editable
    private static final String MAIL_SUBJECT = "Mail Send For Homework"; //Editable
    private static final String MAIL_MESSAGE = "It is a test message. 243";  //Editable

    public static void main(String[] args) {
        System.out.println("Start");
        try (Socket socket = SSLSocketFactory.getDefault().createSocket(SMTP_SERVER, SMTP_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))
        {
                System.out.println("Connected!");
                // Reading the initial server response
                readResponse(reader);

                // EHLO localhost
                sendCommand(writer, "EHLO localhost");
                readResponse(reader);

                // AUTH LOGIN
                sendCommand(writer, "AUTH LOGIN");
                readResponse(reader);

                // Sending the encoded Gmail address
                sendCommand(writer, base64Encode(YOUR_GMAIL_ADDRESS));
                readResponse(reader);

                // Sending the encoded application password
                sendCommand(writer, base64Encode(YOUR_APPLICATION_PASSWORD));
                readResponse(reader);

                // MAIL FROM
                sendCommand(writer, "MAIL FROM: <"+YOUR_GMAIL_ADDRESS+">");
                readResponse(reader);

                // RCPT TO
                sendCommand(writer, "RCPT TO: <"+RCPT_TO_GMAIL_ADDRESS+">");
                readResponse(reader);

                // DATA
                sendCommand(writer, "DATA");
                readResponse(reader);

                // Sending email body
                sendCommand(writer, "Subject: "+ MAIL_SUBJECT);
                sendCommand(writer, MAIL_MESSAGE);
                sendCommand(writer, ".");
                readResponse(reader);

                // QUIT
                sendCommand(writer, "QUIT");
                readResponse(reader);

                System.out.println("Mail sended !!!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Not Connected!");
        }
    }

    private static void sendCommand(BufferedWriter writer, String command) throws IOException {
        System.out.println(System.lineSeparator()); //like \r\n, specific to the operating system
        writer.write(command + System.lineSeparator());
        System.out.println(command);
        writer.flush();
    }

    private static void readResponse(BufferedReader reader) {
        try {
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println("SMTP status code: " + response.substring(0,3));
                System.out.println("Server response: " + response);
                System.out.println(responseCheck(response));
                if (isValidResponseCode(response) || isEndOfResponse(response)) {
                    break;
                }
            }
            System.out.println("End of receive. Next -->");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String VALID_RESPONSE_TYPE = "^\\d{3} .*"; //check for status code
    private static final String END_OF_RESPONSE_SIGN = "."; //may not be necessary

    private static boolean isValidResponseCode(String response) {
        if (response.matches(VALID_RESPONSE_TYPE)) {
            return true;
        }
        return false;
    }

    private static boolean isEndOfResponse(String response) {
        if (END_OF_RESPONSE_SIGN.equals(response)) {
            return true;
        }
        return false;
    }

    private static String responseCheck(String response) {
        if(response.startsWith("2") || response.startsWith("3")){
            return "Successful"; // 2xy and 3xy are successful
        }else if(response.startsWith("4") || response.startsWith("5")){
            return "Error";// 4xy and 5xy are error
        }else return "Unknown server return";
    }

    private static String base64Encode(String text) {
        return java.util.Base64.getEncoder().encodeToString(text.getBytes());
    }
}