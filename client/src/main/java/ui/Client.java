package ui;

import chess.*;
import communication.ServerFacade;

import java.io.PrintStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Client {
    Scanner scanner = new Scanner(System.in);
    ServerFacade facade = new ServerFacade();
    DrawChessBoard drawChessBoard = new DrawChessBoard();

    boolean stopUI = false;
    String currAuthToken = null;
    boolean inGame = false;

    public Client() {
    }

    public void runUI() {
        printStartScreen();
        while (!stopUI) {
            String line = scanner.nextLine();
            if (inGame){
                gamePlayParse(line);
            }
            else if (currAuthToken == null) {
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
                            inGame = true;
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

    private void gamePlayParse(String line) {
        try {
            String[] args = line.split(" ");
            String cmd = args[0];
            switch (cmd) {
                case "move":
                    if (args.length == 2) {
                        System.out.println("possible moves ...");
                        break;
                    }
                    else if(args.length == 4){
                        // First check format
                        if (!Objects.equals(args[2], "to")) {
                            printGamePlayHelp();
                            break;
                        }
                        if (args[1].length() != 2 || args[3].length() != 2){
                            printGamePlayHelp();
                            break;
                        }
                        int startCol = args[1].charAt(0) - 64;
                        int startRow = args[1].charAt(1) - 48;
                        int endCol = args[3].charAt(0) - 64;
                        int endRow = args[3].charAt(1) - 48;

                        if (startRow > 8 || startRow < 1 ||
                            startCol > 8 || startCol < 1 ||
                            endRow > 8 || endRow < 1 ||
                            endCol > 8 || endCol < 1) {
                            System.out.println("Please enter a valid position in a form like this: 'B2'");
                            break;
                        }

                        ChessMove move = new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow,endCol), null);
                        facade.makeMove(currAuthToken, move);

                        break;
                    }
                    printGamePlayHelp();
                    break;
                case "redraw":
                    if (args.length == 1) {
                        System.out.println("redraw ...");
                        break;
                    }
                    printGamePlayHelp();
                    break;
                case "leave":
                    if (args.length == 1) {
                        // todo:: need to remove from game
                        inGame = false;
                        System.out.println("You have left the game");
                        printLoginScreen();
                        break;
                    }
                    printGamePlayHelp();
                    break;
                case "resign":
                    if (args.length == 1) {
                        while (true){
                            System.out.println("Are you sure you want to accept defeat and resign? [y/n]");
                            String confirm = scanner.nextLine();
                            if (Objects.equals(confirm, "y")){
                                //Resigns
                                facade.resign(currAuthToken);
                                System.out.println("You have resigned");
                            }
                            else if (Objects.equals(confirm, "n")){
                                //Exits resign (what should I send?)
                                break;
                            }
                            else {
                                System.out.println("Please enter 'y' for yes or 'n' for no");
                            }
                            }
                        break;
                    }
                    printGamePlayHelp();
                    break;
                case "help":
                default:
                    printGamePlayHelp();
                    break;
            }
        } catch (Exception e) {
            printPreLoginHelp();
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

    private void printGamePlayHelp() {
        System.out.println("POSSIBLE COMMANDS:");
        System.out.println(" move <position>  --  Show the valid moves of the piece at that position [ex: move B2]");
        System.out.println(" move <position> to <position>  --  Move your piece from one position to another [ex: move B2 to B4]");
        System.out.println(" redraw  --  Redraw the chess board");
        System.out.println(" leave  --  Exit the chess game");
        System.out.println(" resign  --  Give up, the other player will win");
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

    private void printPlayScreen() {
        System.out.println(" Type your command. Type 'help' to see possible commands.");
    }

}