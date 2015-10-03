package com.psk.pms.model;


public enum SheetNames {

    MATERIAL_RATES("Material Rates"),
    LABOUR_RATES("Labour Rates"),
    MACHINERY_RATES("Machine Rates"),
    OTHER_RATES("Other Rates");

    private final String sheetName;

    SheetNames(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return this.sheetName;
    }
}
