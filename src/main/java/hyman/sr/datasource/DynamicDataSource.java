package hyman.sr.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;


/**
 * 定义动态数据源，实现通过spring提供的AbstractRoutingDataSource，
 * 由于DynamicDataSource是单例的，是线程不安全的，所以采用ThreadLocal保证线程安全
 * @author hyman
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    // 读库总数
    private Integer slaveCount;  
    // 读库轮询计数, 初始为-1, 本类为单例, AtomicInteger线程安全
    private AtomicInteger counter = new AtomicInteger(-1);
    // 存储读库的识别key sl1ve01, slave02...  写库识别key为master
    private List<Object> slaveDataSources = new ArrayList<Object>();
    
    //当前线程的写库/读库token
    private static final ThreadLocal<Boolean> tokenHolder = new ThreadLocal<>();
    
    public static void markMaster(boolean isMaster){
        tokenHolder.set(isMaster);
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
        if (tokenHolder.get()) {
            return "master";   // 写库
        }
        // 轮询读库, 得到的下标为：0、1、2...
        Integer index = counter.incrementAndGet() % slaveCount;
        if (counter.get() > 99999) { // 以免超出Integer范围
            counter.set(-1); 
        }
        return slaveDataSources.get(index);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        // 父类的resolvedDataSources属性是private, 需要使用反射获取
        Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
        field.setAccessible(true); // 设置可访问
        try {
            @SuppressWarnings("unchecked")
			Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
            
            // 读库数等于dataSource总数减写库数
            this.slaveCount = resolvedDataSources.size() - 1;
            for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
                if ("master".equals(entry.getKey())) {
                    continue;
                }
                slaveDataSources.add(entry.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}