package minesweeper.server;

import minesweeper.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private final Socket socket;
    private final Board board;

    public ServerHandler(Board board,Socket socket) {
        this.socket = socket;
        this.board = board;
    }
    /**
     * Handle a single client connection. Returns when client disconnects.
     *
     * @param socket socket where the client is connected
     * @throws IOException if the connection encounters an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("Welcome to Minesweeper Server!");
        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String output = handleRequest(line);
                // TODO: Consider improving spec of handleRequest to avoid use of null
                out.println(output);
            }
        } finally {
            out.close();
            in.close();
        }
    }

    /**
     * Handler for client input, performing requested operations and returning an output message.
     *
     * @param input message from client
     * @return message to client, or ''if none
     */
    private String handleRequest(String input) {
        String regex = "(look)|(help)|(bye)|"
                + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
        if ( ! input.matches(regex)) {
            // invalid input
            return "Invalid input. Please try again.";
            // TODO Problem 5
        }
        String[] tokens = input.split(" ");
        if (tokens[0].equals("look")) {
            System.out.println("Handle look request");
            return "Handle look request";
            // 'look' request
            // TODO Problem 5
        } else if (tokens[0].equals("help")) {
            System.out.println("Handle help request");
            return board.HandleHelpMessage();
            // 'help' request
            // TODO Problem 5
        } else if (tokens[0].equals("bye")) {
            System.out.println("Handle bye request");
            return board.HandleByeMessage();
            // 'bye' request
            // TODO Problem 5
        } else {
            int x = Integer.parseInt(tokens[1]);
            int y = Integer.parseInt(tokens[2]);
            if (tokens[0].equals("dig")) {
                System.out.println("Handle dig request at " + x + ", " + y);
                return board.HandleDigMessage(x, y);
                // 'dig x y' request
                // TODO Problem 5
            } else if (tokens[0].equals("flag")) {
                System.out.println("Handle flag request at " + x + ", " + y);
                return board.HandleFlagMessage(x,y);
                // 'flag x y' request
                // TODO Problem 5
            } else if (tokens[0].equals("deflag")) {
                System.out.println("Handle deflag request at " + x + ", " + y);
                return board.HandleDeflagMessage(x,y);
                // 'deflag x y' request
                // TODO Problem 5
            }
        }
        // TODO: Should never get here, make sure to return in each of the cases above
        throw new UnsupportedOperationException();
    }
    @Override
    public void run() {
        try{
            handleConnection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
