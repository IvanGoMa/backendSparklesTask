package com.ra12.projecte1.model;

import java.sql.Timestamp;

import com.ra12.projecte1.odt.taskResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private long id;
    private String nomTasca;
    private int sparks;
    private Timestamp dataLimit;
    private Timestamp dataCreated;
    private Timestamp dataUpdated;
    private String urlImage;

    public Task() {
    }

    public Task(String nomTasca, int sparks, Timestamp dataLimit, Timestamp dataCreated, Timestamp dataUpdated,
            String urlImage) {
        
        this.nomTasca = nomTasca;
        this.sparks = sparks;
        this.dataLimit = dataLimit;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
        this.urlImage = urlImage;
    }


    public Task(String nomTasca, int sparks, Timestamp dataLimit, Timestamp dataCreated, Timestamp dataUpdated) {
        this.nomTasca = nomTasca;
        this.sparks = sparks;
        this.dataLimit = dataLimit;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    public void setNomTasca(String nomTasca) {
        this.nomTasca = nomTasca;
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
    public Timestamp getDataCreated() {
        return dataCreated;
    }
    public void setDataCreated(Timestamp dataCreated) {
        this.dataCreated = dataCreated;
    }
    public Timestamp getDataUpdated() {
        return dataUpdated;
    }
    public void setDataUpdated(Timestamp dataUpdated) {
        this.dataUpdated = dataUpdated;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNomTasca() {
        return nomTasca;
    }

    public taskResponseDTO toTaskResponseDTO(){
        return new taskResponseDTO();
    }
    

}
