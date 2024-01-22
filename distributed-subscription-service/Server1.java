import java.io.*;
import java.net.*;
import java.util.List;

public class Server1 {
    
    private static final int PORT = 5001; // Server1's port
    static Aboneler aboneler = new Aboneler();
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server1 is running on port " + PORT);
        try {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }


    private static String operation(String message,Aboneler aboneler,Socket socket){
        String output = "Error";
        String operation = "";
        int user = -1;
        System.out.println("İşlem yapılacak mesaj " + message);
        String[] parts = message.split(" ", 2);
        List<Integer> girisYapanlar = aboneler.getGirisYapanlarListesi();
        List<Integer> aboneOlanlar = aboneler.getAboneler();
        long currentTimeMillis = System.currentTimeMillis();

        if (parts.length == 2) {
            operation = parts[0];
            try {
                user = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println(parts[1] + " geçerli bir tam sayi değil.");
            }
            if(operation.equals("ABONOL")){
                output = "55 TAMM Abone oldu " + user;
                if(aboneOlanlar.contains(user))
                {
                    output = "50 HATA Zaten abone olmuş" + user;
                }else{
                    aboneOlanlar.add(user);
                    aboneler.setAboneler(aboneOlanlar);
                    aboneler.setEpochMiliSeconds(currentTimeMillis);
                    sendAndReceiveMessage("localhost",5002,""+PORT,aboneler,socket);
                    sendAndReceiveMessage("localhost",5003,""+PORT,aboneler,socket);
                    output = output + " Abonelik başarili";
                }
            }else if(operation.equals("GIRIS")){
                output = "Abonelik kontrol ediliyor " + user;
                if(aboneOlanlar.contains(user)){
                    output = "55 TAMM Giris yapti " + user;
                    if(girisYapanlar.contains(user))
                    {
                        output = "Zaten giriş yapmiş" + user;
                    }else{
                        girisYapanlar.add(user);
                        aboneler.setGirisYapanlarListesi(girisYapanlar);
                        aboneler.setEpochMiliSeconds(currentTimeMillis);
                        sendAndReceiveMessage("localhost",5002,""+PORT,aboneler,socket);
                        sendAndReceiveMessage("localhost",5003,""+PORT,aboneler,socket);
                        output = output + " İlk giriş";
                    }
                }else{
                    output = "50 HATA Abone olmadan giriş yapmazsin " + user;
                } 
            }else if(operation.equals("ABONIPTAL")){
                output = "Abonelik kontrol ediliyor " + user;
                if(aboneOlanlar.contains(user)){
                    output = "55 TAMM Abonelik varmis " + user;
                    aboneOlanlar.remove(Integer.valueOf(user));
                    girisYapanlar.remove(Integer.valueOf(user));
                    aboneler.setAboneler(aboneOlanlar);
                    aboneler.setGirisYapanlarListesi(girisYapanlar);
                    aboneler.setEpochMiliSeconds(currentTimeMillis);
                    sendAndReceiveMessage("localhost",5002,""+PORT,aboneler,socket);
                    sendAndReceiveMessage("localhost",5003,""+PORT,aboneler,socket);
                    output = output + " İptal edildi. Cikis yapildi";
                }else{
                    output = "50 HATA Abone olmadan abone iptal yapmazsin " + user;
                } 
            }else if(operation.equals("CIKIS")){
                output = "55 TAMM Giris kontrol ediliyor " + user;
                if(girisYapanlar.contains(user)){
                    output = "55 TAMM Giris varmis " + user;
                    girisYapanlar.remove(Integer.valueOf(user));
                    aboneler.setGirisYapanlarListesi(girisYapanlar);
                    aboneler.setEpochMiliSeconds(currentTimeMillis);
                    sendAndReceiveMessage("localhost",5002,""+PORT,aboneler,socket);
                    sendAndReceiveMessage("localhost",5003,""+PORT,aboneler,socket);
                    output = output + " Çikis yapildi"; 
                }else{
                    output = "50 HATA Giris yapilmamis " + user;
                } 
            }
        }else{                                                                                
            output = "Hatalı gönderim tipi";
        }
        return output;
    }

    private static void sendAndReceiveMessage(String host, int port, String message,Aboneler aboneler,Socket socket) {
        try (Socket socket2 = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
             PrintWriter out = new PrintWriter(socket2.getOutputStream(), true);
             ) {
            out.println(message);
            String response = in.readLine();
            if(response.equals("OK")){
                sendObject(host, port, message, socket2);
            }
        } catch (IOException e) {
            System.out.println("99 HATA " + port + ": " + e.getMessage());
        }
    }

    private static void sendObject(String host, int port, String message,Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
             ) {
            objectOutputStream.writeObject(aboneler);
            System.out.println("Nesne gönderildi. " + "Şuraya: "+port+" "+host+" Tarihi: " + aboneler.getEpochMiliSeconds());
        } catch (IOException e) {
            System.out.println("99 HATA " + port + ": " + e.getMessage());
        } 
    }

    private static Aboneler receiveObject(Socket socket) {
        Aboneler incomingAbone = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                incomingAbone = (Aboneler) objectInputStream.readObject();
                System.out.println("Gelen abonenin zamanı: " + incomingAbone.getEpochMiliSeconds());
        } catch (IOException e) {
            System.out.println("99 HATA " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return incomingAbone;
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket; 
        }

        public void run() {
            BufferedReader in = null;
            String message = null;
            PrintWriter out = null;
                try {
                    System.out.println("Client Handler " + clientSocket.getPort());
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    message = in.readLine(); //Mesaj sunucu port numarası ise sunucudan mesaj gelmiştir. Değiilse client
                    if(message.equals("5002") || message.equals("5003") || message.equals("5001")){
                    out.println("OK"); //Ok yanıtı verildiğinde nesne alma hazırlığı yapılıyor
                    Aboneler tempAboneler = receiveObject(clientSocket);
                    if (tempAboneler != null) {
                        aboneler = tempAboneler;
                }
                    }else {
                    String output = operation(message,aboneler,clientSocket);
                    out.println(output);
                    }
                    clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Zaman: "+ aboneler.getEpochMiliSeconds());
            System.out.println("Abone olanlar listesi: "+ aboneler.getAboneler());
            System.out.println("Giriş yapanlar listesi: "+ aboneler.getGirisYapanlarListesi());
        }
    }
}