package hyman.sr.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

public class DataSourceAspect {

    private List<String> slaveMethodPattern = new ArrayList<String>();  //保存有readonly属性的带通配符方法名
    private static final String[] defaultSlaveMethodStartWith = new String[]{"query", "find", "get" };
    private String[] slaveMethodStartWith;  //保存有slaveMethodStartWith属性的方法名头部
    
    //注入
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception {
        if (txAdvice == null) {
            // 没有配置事务策略
            return;
        }
        //从txAdvice获取策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource)) {
            return;
        }
        //使用反射技术获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
        nameMapField.setAccessible(true); //设置该字段可访问
        //获取nameMap的值
        @SuppressWarnings("unchecked")
		Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField.get(matchTransactionAttributeSource);
        //遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet()) {
            if (!entry.getValue().isReadOnly()) {   // 定义了ReadOnly的策略才加入到slaveMethodPattern
                continue;
            }
            slaveMethodPattern.add(entry.getKey());
        }
    }

    // 切面 before方法
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();

        boolean isSlave = false;

        if (slaveMethodPattern.isEmpty()) {
            // 没有配置read-only属性，采用方法名匹配方式
            isSlave = isSlaveByMethodName(methodName);
        } else {
            // 配置read-only属性, 采用通配符匹配
            for (String mappedName : slaveMethodPattern) {
                if (isSlaveByConfigWildcard(methodName, mappedName)) {
                    isSlave = true;
                    break;
                }
            }
        }
        if (isSlave) {
            // 标记为读库
            DynamicDataSource.markMaster(false);
        } else {
            // 标记为写库
            DynamicDataSource.markMaster(true);
        }
    }

    // 匹配以指定名称开头的方法名, 配置了slaveMethodStartWith属性, 或使用默认
    private Boolean isSlaveByMethodName(String methodName) {
        return StringUtils.startsWithAny(methodName, getSlaveMethodStartWith());
    }

    // 匹配带通配符"xxx*", "*xxx" 和 "*xxx*"的方法名, 源自配置了readonly属性的方法名
    protected boolean isSlaveByConfigWildcard(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    // 注入
    public void setSlaveMethodStartWith(String[] slaveMethodStartWith) {
        this.slaveMethodStartWith = slaveMethodStartWith;
    }

    public String[] getSlaveMethodStartWith() {
        if(this.slaveMethodStartWith == null){
            // 没有配置slaveMethodStartWith属性，使用默认
            return defaultSlaveMethodStartWith;
        }
        return slaveMethodStartWith;
    }
}
