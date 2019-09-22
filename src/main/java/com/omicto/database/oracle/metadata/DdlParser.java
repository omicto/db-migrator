package com.omicto.database.oracle.metadata;



public class DdlParser {
    private final String oracleDdl;
    private String mssqlDdl;

    public DdlParser(String oracleDdl) {
        this.oracleDdl = oracleDdl;
        this.mssqlDdl = parseSql();
    }

    private String parseSql(){
        String createString = changeNumberTypes(oracleDdl)
                .replace("ENABLE","")
                .replace("ORGANIZATION INDEX NOCOMPRESS", "")
                .replaceAll("VARCHAR2|CHAR", "NVARCHAR");
        return createString;
    }

    private String changeNumberTypes(String createString) {
        return createString
                .replaceAll("NUMBER\\([1-9]+,[1-9]+\\)", "FLOAT")
                .replaceAll("NUMBER\\([1-9]+,0\\)|NUMBER", "INT");
    }

    public String getParsedDdl() {
        return mssqlDdl;
    }

}
