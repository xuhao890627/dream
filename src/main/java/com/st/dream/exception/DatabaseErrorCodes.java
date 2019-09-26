package com.st.dream.exception;

public enum DatabaseErrorCodes implements ErrorCodes{

    ERROR_5000 (1000,"Failed to map foreign key field"), //Database record is referencing another record that does not exist
    ERROR_5001 (1001,"Failed to decrypt value from database"), //invalid encrypted value in the database.
    ERROR_5002 (1002,"Failed to get a connection from the connection pool"), //Connection poll may be full or mysql is down
    ERROR_5003 (1003,"SQL failed"), //Sql failed.
    ERROR_5004 (1004,"Reflection failure. Look into this immediately"), //Reflection failed, likely do to a type mismatch
    ERROR_5005 (1005,"Unsupported Cache type"), //Cache is designed for specific types.
    ERROR_5006 (1006,"Deserialization failure"), //failed to deserialize a field.
    ERROR_5007 (1007,"Cache Failed"), //Failed to retrieve from cache
    ERROR_5008 (1008,"Unsupported Serialized type"), //Cache is designed for specific types.
    ERROR_5009(1009,"Failed to insert record into Database"),
    ERROR_5010(1010,"Record doesn't exist in Database");

    private Integer code;
    private String usage;

    DatabaseErrorCodes(Integer code,String usage) {
        this.code = code;
        this.usage = usage;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
