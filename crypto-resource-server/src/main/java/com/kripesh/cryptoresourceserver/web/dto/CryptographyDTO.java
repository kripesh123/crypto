package com.kripesh.cryptoresourceserver.web.dto;

import javax.validation.constraints.NotEmpty;

public class CryptographyDTO {

    private long id;

    @NotEmpty
    private String inputText;

    @NotEmpty
    private String encodedValue;

    public CryptographyDTO(long id, @NotEmpty String inputText, @NotEmpty String encodedValue) {
        this.id = id;
        this.inputText = inputText;
        this.encodedValue = encodedValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    public void setEncodedValue(String encodedValue) {
        this.encodedValue = encodedValue;
    }
}