package com.chzu.apitemplate.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author water king
 * @time 2023/5/17
 */
public class SqlUtils {

    /**
     * 防止sql注入问题
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
