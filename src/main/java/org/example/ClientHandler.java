package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            output.println("Привіт");

            String clientMessage = input.readLine();
            System.out.println("Received from client: " + clientMessage);

            if (Utils.containsRussianCharacters(clientMessage)) {
                output.println("що таке паляниця?");
                String clientAnswer = input.readLine();
                System.out.println("Received answer from client: " + clientAnswer);

                if ("паляниця це хліб".equalsIgnoreCase(clientAnswer.trim())) {

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    output.println("Правильно! Поточна дата та час: " + now.format(formatter));
                } else {
                    output.println("Неправильна відповідь! Відключення...");
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
