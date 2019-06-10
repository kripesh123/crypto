package com.kripesh.cryptoresourceserver.model;

import javax.persistence.*;

@Entity
@Table(name="CRYPTOGRAPHY")
public class Cryptography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputText;

    private String encodedValue;

    public Cryptography(String inputText, String encodedValue) {
        this.inputText = inputText;
        this.encodedValue = encodedValue;
    }

    public Cryptography() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Cryptography{" +
                "id=" + id +
                ", inputText='" + inputText + '\'' +
                ", encodedValue='" + encodedValue + '\'' +
                '}';
    }
}
