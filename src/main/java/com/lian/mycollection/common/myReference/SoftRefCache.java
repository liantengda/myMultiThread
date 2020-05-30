package com.lian.mycollection.common.myReference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 12:35
 */
public class SoftRefCache<K,V> {
        // 实际装载缓存的数据结构，采用Hashtable可以保证线程安全
        private final Hashtable<K, ValueRef> cache;
        // 此队列用来接收被GC的引用对象，来完成清理工作
        private final ReferenceQueue<V> queue;
        // 当被缓存对象不存在缓存中时，调用该接口来查询此对象，以装入缓存
        private final QueryForCache<K,V> queryForCache;

        public SoftRefCache(QueryForCache<K,V> queryForCache) {
            this.cache = new Hashtable<>();
            this.queue = new ReferenceQueue<>();
            this.queryForCache = queryForCache;
        }

        /**
         * 对value的包装，使用软引用来关联value对象，使其具有软引用的对象特性，并保存该value对象的key，以便于完成清理工作
         */
        private class ValueRef extends SoftReference<V> {

            private final K key;

            public ValueRef(K key, V referent, ReferenceQueue<? super V> q) {
                super(referent, q);
                this.key = key;
            }

            public K getKey() {
                return key;
            }
        }

        /**
         * 由Key获取一个对象，若已被缓存，则直接返回，若未被缓存，则将其缓存
         * @param key 要获取的对象的eky
         * @return 要获取的对象
         */
        public V get(K key) {
            V val = null;
            if (cache.containsKey(key)) {
                ValueRef valueRef = cache.get(key);
                val = valueRef != null ? valueRef.get() : null;
            }
            // cache中没有该key对应的对象实例
            if (val == null) {
                // 到数据库或硬盘查询该对象，并加入到cache中
                val = this.queryForCache.query(key);
                addToCache(key, val);
            }
            return val;
        }

        /**
         * 获取缓存内key--value对的数量
         */
        public int size(){
            this.clearCache();
            return cache.size();
        }

        /**
         * 清除缓存
         */
        public void clearAllCache(){
            clearCache();
            cache.clear();
            // 可以根据实际情况决定是否要GC
            System.gc();

        }

        /**
         * 将对象加入缓存
         */
        private void addToCache(K key, V val){
            // 清除垃圾引用
            clearCache();
            // 加入到缓存
            ValueRef valueRef = new ValueRef(key, val, queue);
            this.cache.put(key, valueRef);
        }

        /**
         * 清除缓存中已被GC的Value对象。
         * 具体是通过对ReferenceQueue轮询来实现的
         */
        private void clearCache(){
            ValueRef valueRef = null;
            while((valueRef = (ValueRef) queue.poll()) != null){
                cache.remove(valueRef.getKey());
            }
        }

    }

    /**
     * 该接口定义了一个需要缓存的对象不在缓存时，应该通过怎样的方式获取
     * @param <K> key的类型
     * @param <V> value的类型
     */
    @FunctionalInterface
    interface QueryForCache<K,V> {
        V query(K key);
}
