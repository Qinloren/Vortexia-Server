package com.zeeyeh.vortexiaserver.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanUtils {
    // 需要排除的特殊属性
    private static final Set<String> EXCLUDED_PROPERTIES = Set.of("class");

    /**
     * 复制源对象的属性到目标对象，忽略目标对象中不存在的属性
     * 支持类型转换：Integer -> Enum, Long -> LocalDateTime
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreMissing(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        // 获取源对象和目标对象的属性描述符
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        // 获取目标对象的可写属性名
        Set<String> targetProperties = new HashSet<>();
        PropertyDescriptor[] targetPropertyDescriptors = targetWrapper.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : targetPropertyDescriptors) {
            // 只添加可写的属性，并排除特殊属性
            if (targetWrapper.isWritableProperty(descriptor.getName()) &&
                    !EXCLUDED_PROPERTIES.contains(descriptor.getName())) {
                targetProperties.add(descriptor.getName());
            }
        }

        // 获取源对象的可读属性名
        Set<String> sourceProperties = new HashSet<>();
        PropertyDescriptor[] sourcePropertyDescriptors = sourceWrapper.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : sourcePropertyDescriptors) {
            // 只添加可读的属性，并排除特殊属性
            if (sourceWrapper.isReadableProperty(descriptor.getName()) &&
                    !EXCLUDED_PROPERTIES.contains(descriptor.getName())) {
                sourceProperties.add(descriptor.getName());
            }
        }

        // 找出两个对象都有的属性（交集）
        Set<String> commonProperties = sourceProperties.stream()
                .filter(targetProperties::contains)
                .collect(Collectors.toSet());

        // 只复制共同的属性
        for (String property : commonProperties) {
            // 再次检查属性是否可写
            if (!targetWrapper.isWritableProperty(property)) {
                continue;
            }

            Object value = sourceWrapper.getPropertyValue(property);
            Class<?> sourceType = sourceWrapper.getPropertyType(property);
            Class<?> targetType = targetWrapper.getPropertyType(property);

            // 类型相同直接复制
            if (sourceType == targetType) {
                targetWrapper.setPropertyValue(property, value);
            }
            // Integer转Enum
            else if (sourceType == Integer.class && targetType.isEnum()) {
                Object enumValue = convertIntegerToEnum((Integer) value, targetType);
                if (enumValue != null) {
                    targetWrapper.setPropertyValue(property, enumValue);
                }
            }
            // Long转LocalDateTime
            else if (sourceType == Long.class && targetType == LocalDateTime.class) {
                Object dateTimeValue = convertLongToLocalDateTime((Long) value);
                if (dateTimeValue != null) {
                    targetWrapper.setPropertyValue(property, dateTimeValue);
                }
            }
            // LocalDateTime转Long
            else if (sourceType == LocalDateTime.class && targetType == Long.class) {
                Object longValue = convertLocalDateTimeToLong((LocalDateTime) value);
                if (longValue != null) {
                    targetWrapper.setPropertyValue(property, longValue);
                }
            }
            // 其他情况尝试直接设置
            else {
                try {
                    targetWrapper.setPropertyValue(property, value);
                } catch (Exception e) {
                    // 忽略无法设置的属性
                }
            }
        }
    }

    /**
     * Integer转Enum转换
     * @param value Integer值
     * @param enumClass 枚举类
     * @return 枚举值
     */
    private static Object convertIntegerToEnum(Integer value, Class<?> enumClass) {
        if (value == null) {
            return null;
        }

        try {
            // 尝试调用枚举类的from方法
            Method fromMethod = enumClass.getMethod("fromCode", int.class);
            return fromMethod.invoke(null, value);
        } catch (Exception e) {
            try {
                // 尝试调用valueOf方法
                Method valueOfMethod = enumClass.getMethod("valueOf", String.class);
                return valueOfMethod.invoke(null, String.valueOf(value));
            } catch (Exception ex) {
                // 尝试直接通过ordinal获取
                Object[] enumConstants = enumClass.getEnumConstants();
                if (value >= 0 && value < enumConstants.length) {
                    return enumConstants[value];
                }
                return null;
            }
        }
    }

    /**
     * Long时间戳转LocalDateTime
     * @param timestamp 时间戳
     * @return LocalDateTime对象
     */
    private static LocalDateTime convertLongToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转Long时间戳
     * @param dateTime LocalDateTime对象
     * @return 时间戳
     */
    private static Long convertLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}