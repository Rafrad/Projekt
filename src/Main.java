import Exceptions.PlayerColorException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TODO: scenes


//        primaryStage.setTitle("Zu Wilkomen");
//        AnchorPane rootLayout;
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getClassLoader().getResource("Views/MainMenuScreen.fxml"));
//        rootLayout = loader.load();
//        Scene s = new Scene(rootLayout);
//
//        ConnectionManager connectionManager = new ConnectionManager(s);
//        connectionManager.showLogInScreen();
//
//        primaryStage.setScene(s);
//        primaryStage.show();


        Parent root = FXMLLoader.load(getClass().getResource("Views/Game.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
        primaryStage.setResizable(false);

    }


    public static void main(String[] args) throws PlayerColorException {
        //getIcon - Model???
        launch(args);

//        for(int i = 7; i >= 0; i--) {
//            for(int j = 0; j < 8; j++) {
//                System.out.print(i);
//                System.out.print(j + " ");
//            }
//            System.out.println(
//
//            );
//        }

//        Game game = new Game();
//        game.boardClass.board[2][1] = new WhitePawn();
//        game.boardClass.PrintBoard();
//        game.moveClass.canMove(1, 0);
//        game.move(1, 0, 2, 0);
//
//        game.boardClass.PrintBoard();

    }
}