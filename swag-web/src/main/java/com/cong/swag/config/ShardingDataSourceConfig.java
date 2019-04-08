package com.cong.swag.config;

import com.cong.swag.service.sharding.DataBaseShardingAlgorithm;
import com.cong.swag.service.sharding.TableShardingAlgorithm;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * sharding-jdbc配置
 */
@Configuration
@Component
public class ShardingDataSourceConfig {

    @Autowired
    DataSource db1;

    @Autowired
    DataSource db2;

    @Bean(name = "shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("sharding_db_001", db1);
        dataSourceMap.put("sharding_db_002", db2);

        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();

        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",
            DataBaseShardingAlgorithm.class.getName()));
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",
            TableShardingAlgorithm.class.getName()));
        tableRuleConfiguration.setLogicTable("user");
        tableRuleConfiguration.setActualDataNodes("sharding_db_00${1..2}.user${0..2}");

        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
        return ShardingDataSourceFactory
            .createDataSource(dataSourceMap, shardingRuleConfiguration, new ConcurrentHashMap<>(), new Properties());
    }

}
