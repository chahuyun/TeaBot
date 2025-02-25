package cn.chahuyun.teabot.repository;

import cn.chahuyun.hibernateplus.Configuration;
import cn.chahuyun.hibernateplus.HibernatePlusService;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-25 17:09
 */
public class RepositoryLoader {

    public static void load(Class<?> clazz) {
        Configuration configuration = HibernatePlusService.createConfiguration(clazz);

    }


}
