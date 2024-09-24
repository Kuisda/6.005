/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;

/**
 * TODO
 */
public class MinesweeperServerTest {
    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 4000 + new Random().nextInt(1 << 15);
    private static final int MAX_CONNECTION_ATTEMPTS = 10;

    private static Thread startServer() throws IOException {
        final String[] args = new String[]{
                "--debug",
                "--port",Integer.toString(PORT)
        };
        Thread serverThread = new Thread(()->MinesweeperServer.main(args));
        serverThread.start();
        return serverThread;
    }

    private static Socket connectToServer(Thread server) throws IOException {
        int attempts = 0;
        while(true){
            try{
                Socket socket = new Socket(LOCALHOST, PORT);

            }catch(ConnectException ce){
                if(!server.isAlive()){
                    throw new IOException("Server has stopped");
                }
                if(++attempts >= MAX_CONNECTION_ATTEMPTS){
                    throw new IOException("Exceeded max connection attempts", ce);
                }
                try{
                    Thread.sleep(attempts*10);
                }catch (InterruptedException ignored){

                }
            }
        }
    }
    /*The Socket will keep when connected to server correctly, when connected to server, it will send "Welcome to Minesweeper Server!" to client.
    *
    *
    * */
    @Test(timeout = 10000)
    public void publishTest() throws IOException {
        Thread thread = startServer();

        for(int i=0;i<3;i++){
            new Thread(()->{
                try {
                    Socket socket = connectToServer(thread);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    assertEquals("Welcome to Minesweeper Server!",in.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
