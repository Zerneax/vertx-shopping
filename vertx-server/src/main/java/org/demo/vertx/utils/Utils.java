package org.demo.vertx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new Date());
    }
}
