package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseLoginController implements Connectable{

    SchoolController schoolController; //Caller - SchoolController object

    //Section Information Fields
    @FXML private TextField userIdTextField = new TextField();
    @FXML private TextField passswordTextField = new TextField();
    @FXML private Text databaseLoginMessage = new Text();

    //Window buttons
    @FXML private Button loginButton = new Button();

    //Pass SchoolController caller object
    public void initData(SchoolController schoolController){
        this.schoolController = schoolController;

        //Initialize demonstration login data
        this.userIdTextField.setText("root");
        this.passswordTextField.setText("12345678");
    }

    @FXML
    public void initialize() {
        this.databaseLoginMessage.setText("");
    }

    @Override
    public void ProcessData(ResultSet rs, String opCode) {
        switch (opCode) {
            case "newLoginInfo":
                this.processSchemaData(rs);
                break;
        }
    }

    public void processSchemaData(ResultSet rs){
        String string=null;

        try {
            while (rs.next()) {
                String schemaName = new String(rs.getString(1));
                this.schoolController.updateStatusTextFlow("Successfully connected to database schema: " + schemaName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.schoolController.updateStatusTextFlow("Database error");
        }
    }

    @FXML
    public void onDatabaseCancelLoginButton() {
        //Close the window
        Stage currentStage = (Stage) this.userIdTextField.getScene().getWindow();
        currentStage.close();
    }

    public void onDatabaseLoginButton(){
        //Test for empty data fields
        if (this.userIdTextField.getText().isEmpty() || this.passswordTextField.getText().isEmpty()){
            this.databaseLoginMessage.setText("Invalid Login Data");

            //Wrong user login information reopens this window with a message (from Database method)
        } else {
            //Get the login info
            this.schoolController.getLogin().setUser_name(userIdTextField.getText());
            this.schoolController.getLogin().setPass(passswordTextField.getText());

            //Request data from database 
            Database.getDatabaseData("SELECT DATABASE();",
                    "newLoginInfo", this.schoolController.get_login_info(), this);

            //Close the new student window
            Stage currentStage = (Stage) this.userIdTextField.getScene().getWindow();
            currentStage.close();
            this.schoolController.onTabStudentSelection(); //Reacquire Section list
        }
    }

    @Override
    public SchoolController getSchoolController() {
        return this.schoolController;
    }
}
