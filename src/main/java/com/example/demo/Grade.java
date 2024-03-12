package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Grade {

    private SimpleStringProperty gradeId;
    private SimpleStringProperty gradedItemId;
    private SimpleStringProperty sectionId;
    private SimpleStringProperty studentId;
    private SimpleStringProperty grade;

    public Grade() {
        this.gradeId = new SimpleStringProperty(this, "gradeId", "n/a");
        this.gradedItemId = new SimpleStringProperty(this, "gradedItemId", "n/a");
        this.sectionId = new SimpleStringProperty(this, "sectionId", "n/a");
        this.studentId = new SimpleStringProperty(this, "studentId", "n/a");
        this.grade = new SimpleStringProperty(this, "grade", "n/a");
    }

    public static void processGradeTable(ResultSet rs, ObservableList<Grade> gradeList) {
        Grade testGrade = null;

        gradeList.clear();

        try {
            while (rs.next()) {
                String gradeId = rs.getString("Grade_Id");
                String gradedItemId = rs.getString("Graded_Item_Id");
                String sectionId = rs.getString("Section_Id");
                String studentId = rs.getString("Student_Id");
                String grade = rs.getString("Grade");

                testGrade = new Grade();
                testGrade.setGradeId(gradeId);
                testGrade.setGradedItemId(gradedItemId);
                testGrade.setSectionId(sectionId);
                testGrade.setStudentId(studentId);
                testGrade.setGrade(grade);

                if (!gradeList.contains(testGrade)) {
                    gradeList.add(testGrade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade grade)) return false;

        return Objects.equals(gradeId, grade.gradeId);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(gradeId);
    }

    public String getGradeId() {
        return gradeId.get();
    }

    public StringProperty gradeIdProperty() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId.set(gradeId);
    }

    public String getGradedItemId() {
        return gradedItemId.get();
    }

    public StringProperty gradedItemIdProperty() {
        return gradedItemId;
    }

    public void setGradedItemId(String gradedItemId) {
        this.gradedItemId.set(gradedItemId);
    }

    public String getSectionId() {
        return sectionId.get();
    }

    public StringProperty sectionIdProperty() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId.set(sectionId);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public StringProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }
}
