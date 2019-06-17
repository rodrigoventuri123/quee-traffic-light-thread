package com.unisociesc.bookfair.dto;

import com.unisociesc.bookfair.domain.enumeration.Status;

public class ResultGenreDTO {

    private Status status;
    private String nameSchool;
    private String nameGenre;
    private Integer amountRequest;
    private Double priceRequest;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public Integer getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(Integer amountRequest) {
        this.amountRequest = amountRequest;
    }

    public Double getPriceRequest() {
        return priceRequest;
    }

    public void setPriceRequest(Double priceRequest) {
        this.priceRequest = priceRequest;
    }
}
