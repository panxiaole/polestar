package com.haier.polestar.datasource.autoconfigure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.haier.polestar.datasource.handler.DefaultMetaObjectHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPlus 扩展功能自动配置类
 *
 * @author panxiaole
 * @date 2019-04-01
 */
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@Import(DefaultMetaObjectHandler.class)
public class MybatisPlusExtensionAutoConfiguration {

	/**
	 * 乐观锁
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLocker() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * oracle使用序列作为主键生成策略
	 */
	@Bean
	public OracleKeyGenerator oracleKeyGenerator() {
		return new OracleKeyGenerator();
	}

	/**
	 * 分页插件
	 * 攻击 SQL 阻断解析器、加入解析链
	 */
	@Bean
	@ConditionalOnMissingBean(PaginationInterceptor.class)
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>();
		// 攻击 SQL 阻断解析器、加入解析链
		sqlParserList.add(new BlockAttackSqlParser());
		paginationInterceptor.setSqlParserList(sqlParserList);
		return paginationInterceptor;
	}

	/**
	 * SQL执行效率插件
	 * 生产环境不建议使用
	 */
	@Bean
	@Profile({"dev", "test"})
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

}
