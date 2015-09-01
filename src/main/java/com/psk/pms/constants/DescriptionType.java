package com.psk.pms.constants;

/**
 * Created by prakashbhanu57 on 8/26/2015.
 */
public enum DescriptionType {

    PSK("projectdesc","projdescitem"),
    FIELD("fieldprojectdesc","fieldprojdescitem"),
    GOVERNMENT("quotedprojectdesc","quotedprojdescitem"),
    INVALID("","");

    DescriptionType(String descriptionTableName,String descriptionItemTableName) {
        this.descriptionTableName = descriptionTableName;
        this.descriptionItemTableName = descriptionItemTableName;
    }

    String descriptionTableName;
    String descriptionItemTableName;

    public static String getDescriptionTableName(String descriptionString) {
        String dbName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
                dbName = descriptionType.descriptionTableName;
                break;
            }
        }
        return dbName;
    }

    public static String getDescriptionItemTableName(String descriptionString) {
        String dbName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
                dbName = descriptionType.descriptionItemTableName;
                break;
            }
        }
        return dbName;
    }
}
