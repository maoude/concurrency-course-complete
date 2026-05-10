/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;


public class AsyncServer {
    public static void main(String[] args) throws IOException {
        // One acceptor socket: all clients connect through this port.
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Async server listening on port 8080");
        
        while (true) {
            // Accept returns a connected client socket.
            Socket client = serverSocket.accept();
            
            // Handle each client on the common pool so the accept loop keeps moving.
            CompletableFuture.supplyAsync(() -> {
                try {
                    // Simulate I/O-bound work asynchronously.
                    Thread.sleep(100);
                    return "Hello, Async World!";
                } catch (InterruptedException e) {
                    // Preserve interruption semantics for teaching best practice.
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }).thenAccept(response -> {
                try {
                    // Send a tiny HTTP response body back to the client.
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println("HTTP/1.1 200 OK\r\n\r\n" + response);
                    // Always close per-client socket to avoid descriptor leaks.
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
