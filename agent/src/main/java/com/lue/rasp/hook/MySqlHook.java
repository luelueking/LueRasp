package com.lue.rasp.hook;

import com.alibaba.druid.wall.WallCheckResult;
import com.alibaba.druid.wall.spi.MySqlWallProvider;

import java.util.logging.Logger;

// TODO test sqli
public class MySqlHook {
    private static final Logger logger = Logger.getLogger(MySqlHook.class.getName());

    public static boolean filter(String sql) {
        MySqlWallProvider mySqlWallProvider = new MySqlWallProvider();
        // 使用druid的SqlWall进行Sqli检测
        WallCheckResult checkResult = mySqlWallProvider.check(sql.trim());
        if (checkResult.getViolations().size() > 0) {
            checkResult.getViolations().forEach( e -> {
                logger.warning("Sqli Warning: " + e.getMessage());
            });
            return false;
        }
        return true;
    }
}
