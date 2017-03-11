package com.mango.framework.util;


import android.support.v4.util.LongSparseArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合工具类
 */
public class Util_collection {
    /**
     * http://wuniu2010.iteye.com/blog/1891814
     * 删除ArrayList中重复元素，保持顺序.
     * 对象需实现equals和hashCode方法
     * @param list
     */
    public static void removeRepeat(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
    public static List convertToList(Map map) {
        if(map==null)
            return null;
        return new ArrayList<>( map.values());
    }
    public static Object[] convertToArray(Map map) {
        if(map==null)
            return null;
        Object[] valueArray = new Object[map.size()];
        map.values().toArray(valueArray);
        return valueArray;
    }

    /**
     * 判断是否为集合中有效的下标
     *
     * @param collection
     * @param position
     * @return
     */
    public static boolean isValidPosition(Collection collection, int position) {
        return collection != null && position >= 0 && position < collection.size();
    }

    /**
     * 默认方式为追加
     *
     * @param src
     * @param tar
     */
    public static void addAll(List src, List tar) {
        addAll(src, tar, true);
    }

    /**
     * @param src
     * @param tar
     * @param append 是否追加,如果不是追加,会清空原集合
     */
    public static void addAll(List src, List tar, boolean append) {
        addAll(src, tar, 0, append);
    }


    public static void addAll(List src, List tar, int start, boolean append) {
        addAll(src, tar, start, tar == null ? 0 : tar.size(), append);
    }

    public static void addAll(List src, List tar, int start, int end, boolean append) {
        if (src != null && !isEmpty(tar)) {
            if (!append) {
                src.clear();
            }
            src.addAll(tar.subList(start, end));
        }
    }

    public static boolean contains(Collection collection, Object obj) {
        if (collection == null) {
            return false;
        }
        return collection.contains(obj);
    }

    public static int size(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static void putAll(LongSparseArray src, LongSparseArray tar) {
        if (src != null && tar != null) {
            final int size = src.size();
            for (int i = 0; i < size; i++) {
                tar.put(src.keyAt(i), tar.valueAt(i));
            }
        }
    }

    public static List mapToList(Map map) {
        List list = new ArrayList();
        Set keySet = map.keySet();
        for (Object key : keySet) {
            list.add(key);
        }
        return list;
    }

    public static void copy(Map from, Map to) {
        if (from != null && to != null) {
            to.clear();
            Set keySet = from.keySet();
            for (Object key : keySet) {
                to.put(key, from.get(key));
            }
        }
    }
}
