package com.omicto.database.oracle.metadata;

import java.util.regex.Pattern;

public class DdlParser {
    private final String oracleDdl;
    private final String mssqlDdl;
    private static final String UGLY_STRING = "USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS \n" +
            "  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645\n" +
            "  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)\n" +
            "  TABLESPACE \"USERS\"  ENABLE";;

    public DdlParser(String oracleDdl) {
        this.oracleDdl = oracleDdl;
        this.mssqlDdl = parseSql();
    }

    private String parseSql(){
        String createString = oracleDdl.split("SEGMENT CREATION IMMEDIATE")[0]
                .replace(UGLY_STRING, "")
                .replace("ENABLE", "")
                .replaceAll("VARCHAR2|CHAR", "NVARCHAR")
                .replace("DATE", "DATETIME");
        createString = changeNumberTypes(createString);
        return createString;
    }

    private String changeNumberTypes(String createString) {
        return createString
                .replaceAll("NUMBER\\([1-9]+,[1-9]+\\)", "FLOAT")
                .replaceAll("NUMBER\\([1-9]+,0\\)|NUMBER","INT");
    }

    public String getParsed(){
        return mssqlDdl;
    }

}
