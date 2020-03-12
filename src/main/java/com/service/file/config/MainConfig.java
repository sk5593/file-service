package com.service.file.config;

public class MainConfig {
    public static final String ALGORITHM = "AES";
    public static final int KEY_SIZE = 128;
    public static final int CACHE_SIZE = 1024;
    //公钥
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/P3pPTDWEjvW9G19DT6HwIgW6\n" +
            "i8xhoIRceDjij+zXroj+xaxxnKiz206OGI0jXBwSPsFOc5ZZrWUq6d0GjEyGv53O\n" +
            "0MzMnEjrD+kqpxK4o15JGOUlSBheGEzGaobUtkrKopZWxVkHHQ8sNDfsFxtxVixN\n" +
            "qVL2irh+eKOu49MIiwIDAQAB";
    public final static String DB_URL = "jdbc:derby:file_db";
    //derby驱动
    public final static String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
}
