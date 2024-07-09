package com.demo;

import com.demo.Support.ViewsManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {
    ViewsManager view = new ViewsManager();
    view.showLoginWindow();

  }
}
