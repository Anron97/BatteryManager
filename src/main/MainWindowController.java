package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class MainWindowController {

    @FXML
    private Label battery;
    @FXML
    private Label adapter;
    @FXML
    private Label time;
    @FXML
    private Label timeLock;
    @FXML
    private Button apply;
    @FXML
    private Slider slider;

    private RefreshThread refreshThread = new RefreshThread();

    public MainWindowController() {
        refreshThread.start();
        refreshThread.addListeners(() -> updateLabels());
    }

    private void updateLabels() {
        Platform.runLater(() -> {
                if(battery != null) battery.setText(refreshThread.getList().get(0) + ", " + refreshThread.getList().get(1));
                if(time != null) time.setText(refreshThread.getList().get(2));
                if(adapter != null) adapter.setText(refreshThread.getAdapterInfo());
        });
    }

    public void handle(){
        System.out.println("handle");
    }

    public void shutdown() {
        System.out.println("Shutdown");
        refreshThread.shutdown();
    }

}
