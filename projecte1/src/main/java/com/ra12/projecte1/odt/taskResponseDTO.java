package com.ra12.projecte1.odt;

import java.sql.Timestamp;

public class taskResponseDTO {
    private Long id; 
    private String nomTasca; 
    private int sparks;
    private Timestamp dataLimit;
    private String urlImage;

    public taskResponseDTO(Timestamp dataLimit, Long id, String nomTasca, int sparks, String urlImage) {
        this.dataLimit = dataLimit;
        this.id = id;
        this.nomTasca = nomTasca;
        this.sparks = sparks;
        this.urlImage = urlImage;
    }

    public taskResponseDTO() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTasca() {
        return nomTasca;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "taskResponseDTO [id=" + id + ", nomTasca=" + nomTasca + ", sparks=" + sparks + ", dataLimit="
                + dataLimit + ", urlImage=" + urlImage + "]";
    }

    

    
}
