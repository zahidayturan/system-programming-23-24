import java.io.*;
import java.net.Socket;

public class Client {  
    private static final String HOST = "localhost";
    private static final int SERVER1_PORT = 5001;
    private static final int SERVER2_PORT = 5002;
    private static final int SERVER3_PORT = 5003;

    public static void main(String[] args) {
        long baslangicZamani = System.nanoTime();
        sendAndReceiveMessage(HOST, SERVER1_PORT, "GIRIS 1");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "CIKIS 1");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "ABONIPTAL 1");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "ABONOL 1");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "GIRIS 1");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "CIKIS 1");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "ABONIPTAL 1");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "ABONOL 2");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "GIRIS 2");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "ABONIPTAL 2");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "ABONOL 3");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "GIRIS 3");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "CIKIS 3");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "GIRIS 4");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "GIRIS 5");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "ABONOL 4");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "ABONIPTAL 5");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "GIRIS 4");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "CIKIS 4");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "CIKIS 5");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "ABONOL 6");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "ABONOL 7");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "ABONOL 8");
        sendAndReceiveMessage(HOST, SERVER1_PORT, "GIRIS 6");
        sendAndReceiveMessage(HOST, SERVER2_PORT, "GIRIS 7");
        sendAndReceiveMessage(HOST, SERVER3_PORT, "GIRIS 8");
        long bitisZamani = System.nanoTime();
    
        long gecenSureNano = bitisZamani - baslangicZamani;
        double gecenSureSaniye = (double) gecenSureNano / 1_000_000_000.0;
        System.out.println("İşlem süresi: " + gecenSureSaniye + " saniye");
    }

    private static void sendAndReceiveMessage(String host, int port, String message) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(message);

            String response = in.readLine();
            System.out.println("Response from server on port " + port + ": " + response);
        } catch (IOException e) {
            System.out.println("Error connecting to server on port " + port + ": " + e.getMessage());
        }
    }
}
