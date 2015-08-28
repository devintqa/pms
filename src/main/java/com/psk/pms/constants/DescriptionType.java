package com.psk.pms.constants;

/**
 * Created by prakashbhanu57 on 8/26/2015.
 */
public enum DescriptionType {

    PSK("projectdesc"),
    FIELD("fieldprojectdesc"),
    GOVERNMENT("quotedprojectdesc"),
    INVALID("");

    DescriptionType(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    String dbTableName;

    public static String getDbTableName(String descriptionString) {
        String dbName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
                dbName = descriptionType.dbTableName;
                break;
            }
        }
        return dbName;
    }
}
