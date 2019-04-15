package com.cong.swag.config;

import com.cong.swag.service.sharding.AuditSnapshotShardingAlgorithm;
import com.cong.swag.service.sharding.DataBaseShardingAlgorithm;
import com.cong.swag.service.sharding.TableShardingAlgorithm;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.constant.ShardingPropertiesConstant;
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

    @Autowired
    DataSource auditDb;

    @Bean(name = "shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        //添加数据源集合
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("sharding_db_001", db1);
        dataSourceMap.put("sharding_db_002", db2);
        dataSourceMap.put("audit_test", auditDb);

        Properties props = new Properties();
        // 将show_sql的配置设置为true
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(),"true");
        //构造一个分片规则配置对象
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        //构造一个特定的分表规则配置
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        //采用的分库策略为标准分片策略StandardShardingStrategyConfiguration, 具体包装的分片算法是精确分片算法
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",
            DataBaseShardingAlgorithm.class.getName()));
        //采用的分表策略为标准分片策略StandardShardingStrategyConfiguration, 具体包装的分片算法是精确分片算法
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",
            TableShardingAlgorithm.class.getName()));
        tableRuleConfiguration.setLogicTable("user");
        tableRuleConfiguration.setActualDataNodes("sharding_db_00${1..2}.user${0..2}");

        TableRuleConfiguration tableRuleConfiguration1 = new TableRuleConfiguration();
        tableRuleConfiguration1.setLogicTable("audit_type_config");
        tableRuleConfiguration1.setActualDataNodes("audit_test.audit_type_config");

        TableRuleConfiguration tableRuleConfiguration2 = new TableRuleConfiguration();
        tableRuleConfiguration2.setLogicTable("audit_snapshot");
        tableRuleConfiguration2.setActualDataNodes("audit_test.audit_snapshot_${['deluke','store', 'guanghui', 'normal', 'oushang']}${0..1}");
        tableRuleConfiguration2.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("audit_type",
            AuditSnapshotShardingAlgorithm.class.getName()));

        TableRuleConfiguration tableRuleConfiguration3 = new TableRuleConfiguration();
        tableRuleConfiguration3.setLogicTable("audit_info");
        tableRuleConfiguration3.setActualDataNodes("audit_test.audit_info");
        //将构造好的特定分表规则配置加入到分片配置对象中
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration1);
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration2);
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration3);
        //利用数据源集合加上构造好的分片规则配置创建一个可供orm直接使用的数据源
        return ShardingDataSourceFactory
            .createDataSource(dataSourceMap, shardingRuleConfiguration, new ConcurrentHashMap<>(), props);
    }

}
