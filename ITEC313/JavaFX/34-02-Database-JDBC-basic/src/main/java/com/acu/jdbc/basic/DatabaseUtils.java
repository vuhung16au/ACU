package com.acu.jdbc.basic;

import java.nio.ByteBuffer;
import java.util.UUID;

public class DatabaseUtils {
    public static final String JDBC_Driver_PostgreSQL = "org.postgresql.Driver";
    public static final String JDBC_URL_PostgreSQL = "jdbc:postgresql://localhost:5432/jdbc_basic?user=postgres&password=postgres&ssl=false";

    public static UUID asUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
