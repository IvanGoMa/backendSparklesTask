package com.ra12.projecte1.odt;

import java.sql.Timestamp;

import com.ra12.projecte1.model.Task;

public class taskRequestDTO {
    private String nomTaska; 
    private int sparks;
    private Timestamp dataLimit;
    private String urlImage;

    public Task toTask(){
        return new Task(nomTaska, sparks, dataLimit, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), urlImage);
    }

    public String getNomTaska() {
        return nomTaska;
    }

    public void setNomTaska(String nomTaska) {
        this.nomTaska = nomTaska;
    }

    public int getSparks() {
        return sparks;
    }

    public void setSparks(int sparks) {
        this.sparks = sparks;
    }

    public Timestamp getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(Timestamp dataLimit) {
        this.dataLimit = dataLimit;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    

}

