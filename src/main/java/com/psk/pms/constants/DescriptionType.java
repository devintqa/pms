package com.psk.pms.constants;

/**
 * Created by prakashbhanu57 on 8/26/2015.
 */
public enum DescriptionType {

    PSK("projectdesc","projdescitem","pskpricedetail"),
    FIELD("fieldprojectdesc","fieldprojdescitem","pskpricedetail"),
    GOVERNMENT("govprojectdesc","govprojdescitem","govpricedetail"),
    BASE("basedesc","basedescitem",null),
    INVALID("","","");

    DescriptionType(String descriptionTableName,String descriptionItemTableName,String descriptionPriceTableName) {
        this.descriptionTableName = descriptionTableName;
        this.descriptionItemTableName = descriptionItemTableName;
        this.descriptionPriceTableName = descriptionPriceTableName;
    }

    String descriptionTableName;
    String descriptionItemTableName;
    String descriptionPriceTableName;

    public static String getDescriptionTableName(String descriptionString) {
        String tblName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
            	tblName = descriptionType.descriptionTableName;
                break;
            }
        }
        return tblName;
    }

    public static String getDescriptionItemTableName(String descriptionString) {
        String tblName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
            	tblName = descriptionType.descriptionItemTableName;
                break;
            }
        }
        return tblName;
    }

    public static String getDescriptionPriceTableName(String descriptionString) {
        String tblName = "";
        for (DescriptionType descriptionType : DescriptionType.values()) {
            if (descriptionType.name().equalsIgnoreCase(descriptionString)) {
                tblName = descriptionType.descriptionPriceTableName;
                break;
            }
        }
        return tblName;
    }
}
