package com.tencent.wxcloudrun.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import org.springframework.objenesis.ObjenesisStd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象拷贝工具类
 *
 */
@Slf4j
public class BeanCopyUtils {

    private static ThreadLocal<ObjenesisStd> objenesisStdThreadLocal = ThreadLocal.withInitial(ObjenesisStd::new);

    private static ConcurrentHashMap<BeanCopierKey, BeanCopier> copierMap = new ConcurrentHashMap<>();

    /**
     * 复制对象
     * 
     * @param sourceObj 源对象
     * @param targetClazz 目标对象类型
     * @param <T> 目标对象
     * @return 复制后的目标对象
     */
    public static <T extends Serializable> T copy(Object sourceObj, Class<T> targetClazz) {
        T t = null;
        if (sourceObj == null || targetClazz == null) {
            return null;
        }
        Class<?> srcType = sourceObj.getClass();
        BeanCopierKey copierKey = new BeanCopierKey(srcType, targetClazz);
        BeanCopier copier = copierMap.get(copierKey);
        if (copier == null) {
            copierMap.putIfAbsent(copierKey, BeanCopier.create(srcType, targetClazz, false));
            copier = copierMap.get(copierKey);
        }
        try {
            t = targetClazz.newInstance();
            copier.copy(sourceObj, t, null);
        } catch (Exception e) {
            log.error("BeanCopyUtils.copy,Exception:{}", e.getMessage());
        }
        return t;
    }

    /**
     * 复制对象
     *
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copyBean(Object sourceObj, Object targetObj) {
        if (sourceObj == null || targetObj == null) {
            return;
        }
        Class<?> srcType = sourceObj.getClass();
        BeanCopierKey copierKey = new BeanCopierKey(srcType, targetObj.getClass());
        BeanCopier copier = copierMap.get(copierKey);
        if (copier == null) {
            copierMap.putIfAbsent(copierKey, BeanCopier.create(srcType, targetObj.getClass(), false));
            copier = copierMap.get(copierKey);
        }
        try {
            copier.copy(sourceObj, targetObj, null);
        } catch (Exception e) {
            log.error("BeanCopyUtils.copyBean,Exception:{}", e.getMessage());
        }
    }

    /**
     * 复制对象列表
     * 
     * @param sourceList 源对象列表
     * @param sourceClazz 源对象类型
     * @param targetClazz 目标对象类型
     * @param <O> 源对象
     * @param <T> 目标对象
     * @return 复制后的目标对象列表
     */
    public static <O, T> List<T> copyList(List<O> sourceList, Class<O> sourceClazz, Class<T> targetClazz) {
        BeanCopierKey copierKey = new BeanCopierKey(sourceClazz, targetClazz);
        BeanCopier copier;
        if (copierMap.containsKey(copierKey)) {
            copier = copierMap.get(copierKey);
        } else {
            copier = BeanCopier.create(sourceClazz, targetClazz, false);
            copierMap.put(copierKey, copier);
        }
        List<T> targetList = new ArrayList<>();
        try {
            sourceList.forEach(bean -> {
                T instance = null;
                try {
                    instance = (T) targetClazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                copier.copy(bean, instance, null);
                targetList.add(instance);
            });
        } catch (Exception e) {
            log.error("BeanCopyUtils.copyList,Exception:{}", e.getMessage());
        }
        return targetList;
    }

    /**
     * 键值对转换对象
     * 
     * @param source 源键值对
     * @param target 模板对象类型
     * @param <T> 目标对象
     * @return 转换后的目标对象
     */
    public static <T> T mapToBean(Map<?, ?> source, Class<T> target) {
        T bean = objenesisStdThreadLocal.get().newInstance(target);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(source);
        return bean;
    }

    /**
     * 对象转换键值对
     * 
     * @param source 源对象
     * @param <T> 目标键值对
     * @return 转换后的目标键值对
     */
    public static <T> Map<?, ?> beanToMap(T source) {
        return BeanMap.create(source);
    }

    private static class BeanCopierKey {

        Class<?> sourceType;
        Class<?> targetType;

        public BeanCopierKey(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof BeanCopierKey)) {
                return false;
            }
            BeanCopierKey key = (BeanCopierKey) obj;
            return key.sourceType == sourceType && key.targetType == targetType;
        }

        @Override
        public int hashCode() {
            return sourceType.hashCode() + targetType.hashCode();
        }

        @Override
        public String toString() {
            return sourceType + " -> " + targetType;
        }
    }
}
