package com.lian.mycollection.common.myReference;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 12:36
 */
public class SortRefCacheTest {
    public static void main(String[] args) {
        // 这个接口实际应该实现为到数据库或硬盘查询实际的数据，这里就简单模拟，直接new
        QueryForCache<Integer, MyImage> queryForCache = key -> new MyImage(key, new byte[10*1024*1024]);
        // 创建缓存
        SoftRefCache<Integer, MyImage> softRefCache = new SoftRefCache<>(queryForCache);
        // 此处模拟不断对缓存进行装入，观察内存和gc情况
        for(int i=1; i < 100; i++){
            MyImage value = softRefCache.get(i);
            System.out.println("缓存数量--->"+softRefCache.size());
            System.out.println("从缓存中获取到第" + value.getId() + "个MyImage");
        }
    }
}

class MyImage {
    private Integer id;
    private byte[] data; // 模拟较大的内存占用，以更好观察gc前后的内存变化

    public MyImage(Integer id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }
}