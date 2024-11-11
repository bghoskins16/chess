package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import communication.ServerFacade;

import java.io.PrintStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Client {
    Scanner scanner = new Scanner(System.in);
    ServerFacade facade = new ServerFacade();
    DrawChessBoard drawChessBoard = new DrawChessBoard();

    boolean stopUI = false;
    String currAuthToken = null;

    public Client() {
    }

    public void runUI() {
        printStartScreen();
        while (!stopUI) {
            String line = scanner.nextLine();
            if (currAuthToken == null) {
                preLoginParse(line);
            } else {
                postLoginParse(line);
            }
        }
    }

    private void preLoginParse(String line) {
        try {
            String[] args = line.split(" ");
            String cmd = args[0];
            switch (cmd) {
                case "register":
                    currAuthToken = facade.register(args[1], args[2], args[3]);
                    printLoginScreen();
                    break;
                case "login":
                    currAuthToken = facade.login(args[1], args[2]);
                    printLoginScreen();
                    break;
                case "quit":
                    stopUI = true;
                    break;
                case "help":
                default:
                    printPreLoginHelp();
                    break;
            }
        } catch (Exception e) {
            printPreLoginHelp();
        }
    }

    private void postLoginParse(String line) {
        try {
            String[] args = line.split(" ");
            String cmd = args[0];
            switch (cmd) {
                case "create":
                    facade.createGame(args[1]);
                    break;
                case "list":
                    facade.listGames();
                    break;
                case "join":
                    facade.joinGame(args[1], args[2]);
                    drawChessBoard.printStartingBoard();
                    break;
                case "observe":
                    drawChessBoard.printStartingBoard();
                    break;
                case "logout":
                    facade.logout();
                    currAuthToken = null;
                    printStartScreen();
                    break;
                case "quit":
                    stopUI = true;
                    break;
                case "help":
                default:
                    printPostLoginHelp();
                    break;
            }
        } catch (Exception e) {
            printPostLoginHelp();
        }
    }

    private void printPostLoginHelp() {
        System.out.println("POSSIBLE COMMANDS:");
        System.out.println(" create <name>  --  Create a new chess game with the given name");
        System.out.println(" list  --  List all the current chess games");
        System.out.println(" join <id> [black OR white]  --  Join the chess game with the given id and color");
        System.out.println(" observe <id>  --  Observe the chess game with the given id");
        System.out.println(" logout  --  Logout of your account");
        System.out.println(" quit  --  Exit chess program");
        System.out.println(" help  --  Display the help message");
    }

    private void printPreLoginHelp() {
        System.out.println("POSSIBLE COMMANDS:");
        System.out.println(" register <username> <password> <email>  --  Create a new account");
        System.out.println(" login <username> <password>  --  Login to your account");
        System.out.println(" quit  --  Exit chess program");
        System.out.println(" help  --  Display the help message");
    }

    private void printStartScreen() {
        System.out.println("Welcome to chess!!");
        System.out.println(" To get started type your command. Type 'help' to see possible commands.");
        System.out.println(" You'll need to login to play!");
    }

    private void printLoginScreen() {
        System.out.println("You have successfully logged in!!");
        System.out.println(" To play type your command. Type 'help' to see possible commands.");
        System.out.println(" You'll need to join a game to play!");
    }

}