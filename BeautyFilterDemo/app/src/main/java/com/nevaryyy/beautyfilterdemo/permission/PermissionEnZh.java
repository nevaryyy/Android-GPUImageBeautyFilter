package com.nevaryyy.beautyfilterdemo.permission;

import java.util.Map;

/**
 * @author nevaryyy
 */
public class PermissionEnZh {
    public static String VERSION;
    public static String AUTHOR;
    public static Map<String, String> EN_ZH;

    public static String getPermissionZh(String permissionEn) {
        if (EN_ZH == null) {
            return permissionEn;
        }
        if (EN_ZH.containsKey(permissionEn)) {
            return EN_ZH.get(permissionEn);
        }
        else {
            return permissionEn;
        }
    }
}
