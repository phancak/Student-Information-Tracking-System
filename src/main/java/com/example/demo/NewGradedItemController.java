package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewGradedItemController implements Connectable {
    SchoolController schoolController; //Caller - SchoolController object
    private boolean newGradedItem; //Is new student being generated or existing student being edited
    Section selectedSection;

    //Pane title text
    @FXML
    private Text title = new Text();

    //Section Information Fields
    @FXML private ChoiceBox sectionChoiceBox = new ChoiceBox();
    @FXML private TextField gradedItemTitleTextField = new TextField();
    @FXML private TextArea gradedItemDescritpionTextArea = new TextArea();
    @FXML private DatePicker gradedItemDate = new DatePicker();

    //Window buttons
    @FXML private Button addGradedItemButton = new Button();

    //Pass SchoolController caller object
    public void initData(SchoolController schoolController, boolean newGradedItem, Section selectedSection){
        this.schoolController = schoolController;
        this.selectedSection = selectedSection;
        this.newGradedItem = newGradedItem; //New Graded Item or Edit Section

        //Must initialize components from initData and makes initial selection
        this.populateSectionChoiceBox();

        //Existing graded item is being edited
        if(!newGradedItem){
            this.title.setText("Edit Graded Item");
            this.addGradedItemButton.setText("Modify Graded Item Entry");
            this.gradedItemTitleTextField.setText(this.schoolController.gradedItemSelectedItems.get(0).getGradedItemTitle());
            this.gradedItemDescritpionTextArea.setText(this.schoolController.gradedItemSelectedItems.get(0).getGradedItemDescription());

            //Graded item date initialization
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate gradedItemDateCurrent = LocalDate.parse(schoolController.gradedItemSelectedItems.get(0).getGradedItemDate(), formatter);
            this.gradedItemDate.setValue(gradedItemDateCurrent);
        }
    }

    @FXML
    public void initialize() {
        //Initialize in initData after receiving schoolController
        //Due to database login
    }

    //Process incoming data from the database
    @Override
    public void ProcessData(ResultSet rs, String opCode) {
        //Nothing to process
    }

    //Populates the section selection choiceBox with the currently available sections
    public void populateSectionChoiceBox() {
        Database.initialize_Section_ChoiceBox(sectionChoiceBox, this.schoolController.getSectionList(), this.schoolController.sectionIdList, 2);

        //Check if section was selected on the graded item choice box
        if (this.selectedSection != null) {
            //initializes selection at the currently selected section in the caller window
            System.out.println("Selected section graded item2: " + this.selectedSection.getSectionString());
            System.out.println("index: " + this.schoolController.sectionIdList.indexOf(this.selectedSection));
            this.sectionChoiceBox.getSelectionModel().select(this.schoolController.sectionIdList.indexOf(this.selectedSection.getSectionId()));
        }
    }

    @FXML
    public void onCloseNewGradedItemButton() {
        Stage currentStage = (Stage) this.title.getScene().getWindow();
        currentStage.close();
    }

    public void onAddGradedItemButton(){
        String selectedSectionItemId = new String();
        String selectedGrtadedItemId = new String();

        //Test for empty data fields
        if (this.sectionChoiceBox.getSelectionModel().isEmpty() ||
                this.gradedItemTitleTextField.getText().isEmpty() || this.gradedItemDescritpionTextArea.getText().isEmpty() ||
                this.gradedItemDate.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Invalid graded item Data. Must select valid section, title, description, and date.");
            alert.showAndWait();
        } else {
            if (this.newGradedItem) {
                //Determines the selected section item id from the section choice box
                selectedSectionItemId = this.schoolController.sectionIdList.get(this.sectionChoiceBox.getSelectionModel().getSelectedIndex());

                //Format date data
                LocalDate gradedItemDateValue = this.gradedItemDate.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String gradedItemDateString = gradedItemDateValue.format(formatter);

                //Modifies data in database
                Database.getDatabaseData("INSERT INTO Graded_Item (Section_Id, " +
                                "Graded_Item_Title, Graded_Item_Description, Graded_Item_Date) " +
                                "VALUES ('" + selectedSectionItemId + "', '" +
                                this.gradedItemTitleTextField.getText() + "', '" +
                                this.gradedItemDescritpionTextArea.getText() + "', '" +
                                gradedItemDateString + "');",
                        "newGradedItem", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.sectionChoiceBox.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabGradedItemListModified(selectedSectionItemId); //Reacquire Section list
            } else {
                //Determines the selected section item id from the section choice box
                selectedSectionItemId = this.schoolController.sectionIdList.get(sectionChoiceBox.getSelectionModel().getSelectedIndex());

                //Format date data
                LocalDate gradedItemDateValue = this.gradedItemDate.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String gradedItemDateString = gradedItemDateValue.format(formatter);

                //Request data from database
                Database.getDatabaseData("UPDATE Graded_Item " +
                                "SET Section_Id='" + selectedSectionItemId +
                                "', Graded_Item_Title='" + this.gradedItemTitleTextField.getText() + "', Graded_Item_Description='" + this.gradedItemDescritpionTextArea.getText() +
                                "', Graded_Item_Date='" + gradedItemDateString + "' " +
                                "WHERE Graded_Item_Id=" + schoolController.gradedItemSelectedItems.get(0).getSectionId() + ";",
                        "updateGradedItem", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.sectionChoiceBox.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabGradedItemListModified(selectedSectionItemId); //Reacquire Section list
            }
        }
    }

    @Override
    public SchoolController getSchoolController() {
        return this.schoolController;
    }
}
