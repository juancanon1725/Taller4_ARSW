package edu.escuelaing.arsw.ASE.app;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class WebServer {

    public static String path = "src/main/resources/public/";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            System.out.println("Server started at port 35000");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                } catch (IOException e) {
                    System.err.println("Error handling client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000. " + e.getMessage());
            System.exit(1);
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String inputLine = in.readLine();
            if (inputLine == null || inputLine.isEmpty()) {
                return;
            }

            System.out.println("Received: " + inputLine);
            String[] requestParts = inputLine.split(" ");
            if (requestParts.length < 2) {
                return;
            }

            String requestedPath = requestParts[1];
            if (requestedPath.startsWith("/doc?val=")) {
                String name = requestedPath.split("=")[1];
                String response = "Hello, " + name + "!";
                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "\r\n" +
                        response;
                out.write(httpResponse.getBytes());
            } else {
                if (requestedPath.equals("/")) {
                    requestedPath = "/index.html";
                }

                String filePath = path + requestedPath;
                if (Files.exists(Paths.get(filePath))) {
                    String fileType = Files.probeContentType(Paths.get(filePath));
                    byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

                    String responseHeader = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + (fileType != null ? fileType : "application/octet-stream") + "\r\n" +
                            "Content-Length: " + fileContent.length + "\r\n" +
                            "\r\n";
                    out.write(responseHeader.getBytes());
                    out.write(fileContent);
                } else {
                    String response = "HTTP/1.1 404 Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "\r\n" +
                            "<h1>404 Not Found</h1>";
                    out.write(response.getBytes());
                }
            }

            out.flush();
        }
    }
}
