package message.Board;

import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import java.util.Optional;
import static javafx.scene.control.ButtonType.OK;


@Component
public class postController {
    //Buttons
    @FXML
    private Button btnSubmit;

    //Textfields/area
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtArea;

    //Repository
    @Autowired
    private postRepository postrepository;

    private static final String VALUES_MISSING_MESSAGE = "Please provide values for all fields";
    private static final String SAVE_POST = "Thank you for your submission";



    //If there's no input show error
    private void showFieldError(DataIntegrityViolationException x) {
        String instructions = VALUES_MISSING_MESSAGE;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, instructions);
        alert.show();
    }


    //Get text from textfield/area and submit to discussion board
    @FXML
    private void handlePostMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, SAVE_POST);
        Optional<ButtonType> result = alert.showAndWait();


        Messageboard board = new Messageboard();
        board.setName(txtName.getText());
        board.setMessage(txtArea.getText());

        try {
            if (result.get() == OK)
            postrepository.saveAndFlush(board);
            clearFields();
        }   catch (DataIntegrityViolationException e) {
            showFieldError(e);
        }
    }


    //Clear all fields
        @FXML
        private void clearFields() {
            txtName.setText(null);
            txtArea.setText(null);
        }
}

