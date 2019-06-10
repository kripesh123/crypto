package com.kripesh.cryptoresourceserver.web.dto;

import javax.validation.constraints.NotNull;

public class InputDTO {

    @NotNull(message = "Please enter text to encode")
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
