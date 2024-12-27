package com.example.ecommerce_java_proj;

import com.example.ecommerce_java_proj.backend.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage);
        sceneManager.switchScene("/com/example/ecommerce_java_proj/Log_IN.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
