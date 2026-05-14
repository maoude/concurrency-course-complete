package edu.lu.concurrency.week10.day1.part2_socket_queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class Demo03_LoopbackSocketQueue {

    public static String roundTrip(String payload) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(4);
        try (ServerSocket server = new ServerSocket(0)) {
            Thread serverThread = Thread.ofVirtual().start(() -> serveOne(server, queue));
            try (Socket client = new Socket("127.0.0.1", server.getLocalPort());
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true, StandardCharsets.UTF_8)) {
                out.println("PUT " + payload);
                String putAck = in.readLine();
                out.println("TAKE");
                String takeAck = in.readLine();
                serverThread.join();
                return putAck + " | " + takeAck;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Socket queue demo failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for socket demo", e);
        }
    }

    private static void serveOne(ServerSocket server, BlockingQueue<String> queue) {
        try (Socket socket = server.accept();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            String put = in.readLine();
            if (put != null && put.startsWith("PUT ")) {
                queue.offer(put.substring(4));
                out.println("ACK put");
            }
            String take = in.readLine();
            if ("TAKE".equals(take)) {
                String value = queue.poll();
                out.println(value == null ? "EMPTY" : "ACK " + value);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Socket server failed", e);
        }
    }

    private Demo03_LoopbackSocketQueue() {
    }
}
