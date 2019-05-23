package message.Board;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


import static javafx.scene.control.ButtonType.OK;

@Component
public class boardController implements Initializable {


    @FXML
    private TableView<Messageboard> tableView;
    @FXML
    private TableColumn<Messageboard, Integer> idColumn;
    @FXML
    private TableColumn<Messageboard, String> nameColumn;
    @FXML
    private TableColumn<Messageboard, String> messageColumn;

    @FXML
    private TextArea txtArea;


    private static final String DELETE_MESSAGE = "Are you sure you want to delete this post?";

    @Autowired
    private postRepository postrepository;

    int tempId;

    @FXML
    private void handleClickTable() {
        tableView.setOnMouseClicked(e -> {
            Messageboard board = tableView.getSelectionModel().getSelectedItem();
            tempId = board.getId();
            txtArea.setText(board.getMessage() + "");
        });
    }

        @FXML
        private void handleDeleteButton() {
            Messageboard board = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, DELETE_MESSAGE);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == OK) {
                postrepository.delete(board);
                tableView.getItems().remove(board);
                clearFields();
                tableView.getSelectionModel().clearSelection();
            }
        }

        @FXML
        private void clearFields(){
        txtArea.setText(null);
        }


        @Override
        public void initialize (URL url, ResourceBundle rb){

            ObservableList<Messageboard> observableList = FXCollections.observableArrayList();
            postrepository.findAll().forEach(observableList::add);

            idColumn.setCellValueFactory(new PropertyValueFactory<Messageboard, Integer>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Messageboard, String>("name"));
            messageColumn.setCellValueFactory(new PropertyValueFactory<Messageboard, String>("message"));

            tableView.setItems(observableList);
            handleClickTable();


        }


}



