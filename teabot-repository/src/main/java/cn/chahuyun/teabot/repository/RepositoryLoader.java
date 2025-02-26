package cn.chahuyun.teabot.repository;

import cn.chahuyun.hibernateplus.Configuration;
import cn.chahuyun.hibernateplus.DriveType;
import cn.chahuyun.hibernateplus.HibernatePlusService;
import cn.chahuyun.teabot.conf.system.ConfigService;
import cn.chahuyun.teabot.conf.system.entity.DataConfig;
import cn.chahuyun.teabot.conf.system.entity.SystemConfig;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-25 17:09
 */
public class RepositoryLoader {

    public static void load(Class<?> clazz) {
        Configuration configuration = HibernatePlusService.createConfiguration(clazz);

        configuration.setDriveType(DriveType.MYSQL);

        SystemConfig config = ConfigService.getConfig();
        DataConfig data = config.getData();
        configuration.setAddress(data.getAddress());
        configuration.setUser(data.getUsername());
        configuration.setPassword(data.getPassword());

        HibernatePlusService.loadingService(configuration);
    }


}
