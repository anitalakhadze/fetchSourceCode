package com.example.fetchsourcecode.model;

import javax.validation.constraints.Min;

public class NumOfWindows {

    @Min(value = 1, message = "You should input at least one URL")
    private int numOfWindows;

    public int getNumOfWindows() {
        return numOfWindows;
    }

    public void setNumOfWindows(int numOfWindows) {
        this.numOfWindows = numOfWindows;
    }
}
