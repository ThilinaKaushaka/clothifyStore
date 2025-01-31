package starter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;





public class Starter extends Application {
    public static Stage mainWindow;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow=stage;
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainWindow.fxml"))));
        stage.getIcons().add(new Image("image/489b2e1076ad1b8c680ff477b05f2110.png"));
        stage.show();


    }


}

