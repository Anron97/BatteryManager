package main;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        Platform.runLater(() -> {
            double currentLockTime = getCurrentLockTime();
            slider.setValue(currentLockTime);
            timeLock.setText(Integer.toString((int)currentLockTime) + " seconds");
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    timeLock.setText(Integer.toString((int)slider.getValue()) + " seconds");
                    apply.setDisable(false);
                }
            });
        });
    }

    private void updateLabels() {
        Platform.runLater(() -> {
                if(battery != null) battery.setText(refreshThread.getList().get(0) + ", "
                        + refreshThread.getList().get(1));
                if(time != null) time.setText(refreshThread.getList().get(2));
                if(adapter != null) adapter.setText(refreshThread.getAdapterInfo());
        });
    }

    private double getCurrentLockTime() {
        try {
            Process process = Runtime.getRuntime().exec("gsettings get org.gnome.desktop.session idle-delay");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return Integer.parseInt(reader.readLine().split(" ")[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void shutdown() {
        refreshThread.shutdown();
    }

}
