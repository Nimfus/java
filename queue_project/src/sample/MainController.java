package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class MainController {
    @FXML
    private TextField emailInput;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private ProgressIndicator progressIndicator;

    private RunnableTask task;

    public void start()
    {
        String email = emailInput.getText();
        LocalDate dateFromValue = dateFrom.getValue();
        LocalDate dateToValue = dateTo.getValue();

        task = new RunnableTask(email, dateFromValue, dateToValue);

        task.start();
        progressIndicator.setVisible(true);
        progressIndicator.setProgress(-1.0);
    }

    public void stop()
    {
        task.stop();
        progressIndicator.setVisible(false);
    }
}
