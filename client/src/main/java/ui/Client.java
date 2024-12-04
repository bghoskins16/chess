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
                    if (args.length == 4) {
                        currAuthToken = facade.register(args[1], args[2], args[3]);
                        if (currAuthToken != null) {
                            printLoginScreen();
                        }
                        break;
                    }
                    printPreLoginHelp();
                    break;
                case "login":
                    if (args.length == 3) {
                        currAuthToken = facade.login(args[1], args[2]);
                        if (currAuthToken != null) {
                            printLoginScreen();
                        }
                        break;
                    }
                    printPreLoginHelp();
                    break;
                case "quit":
                    if (args.length == 1) {
                        stopUI = true;
                        break;
                    }
                    if (args[1].equals("clear")) {
                        facade.clear();
                        System.out.println("Clearing database instead of quitting :)");
                        break;
                    }
                    printPreLoginHelp();
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
                    if (args.length == 2) {
                        facade.createGame(currAuthToken, args[1]);
                        break;
                    }
                    printPostLoginHelp();
                    break;
                case "list":
                    if (args.length == 1) {
                        facade.listGames(currAuthToken);
                        break;
                    }
                    printPostLoginHelp();
                    break;
                case "join":
                    if (args.length == 3) {
                        if (facade.joinGame(currAuthToken, args[1], args[2])) {
                            System.out.println("need to move to next screen");
                        }
                        break;
                    }
                    printPostLoginHelp();
                    break;
                case "observe":
                    if (args.length == 2) {
                        drawChessBoard.printStartingBoard();
                        break;
                    }
                    printPostLoginHelp();
                    break;
                case "logout":
                    if (args.length == 1) {
                        if (facade.logout(currAuthToken)) {
                            currAuthToken = null;
                            printStartScreen();
                        }
                        break;
                    }
                    printPostLoginHelp();
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
        System.out.println(" To play type your command. Type 'help' to see possible commands.");
        System.out.println(" You'll need to join a game to play!");
    }

}