package com.zhijian.ebook.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * uuid的强化版本，保证在同一时空产生的id号是唯一的
 * 
 * @author evic 2015年11月12日 下午3:38:21
 */
public class UUID16maker {

    private static final Logger log = LogManager.getLogger();

    private final long workerId;

    private final static long twepoch = 1288834974657L;

    private long sequence = 0L;

    private final static long workerIdBits = 4L;

    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;

    private final static long sequenceBits = 10L;

    private final static long workerIdShift = sequenceBits;

    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    @SuppressWarnings("static-access")
    public UUID16maker(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    @SuppressWarnings("static-access")
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                // System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << this.workerIdShift)
                | (this.sequence);
        // System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"+
        // timestampLeftShift + ",nextId:" + nextId + ",workerId:" + workerId +
        // ",sequence:" + sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 生成唯一的18位订单编号
     * 
     * @author evic 2015年11月12日 下午3:45:19
     * @return str 18位订单编号字符串
     */
    public static String getOrderUUID16IdStr() {
        UUID16maker uuid16maker = new UUID16maker(1);
        long l = uuid16maker.nextId();
        String str = "dk" + l;
        log.info("=========>订单编号:" + str);
        return str;
    }

    /**
     * 
     * @author evic 2015年11月12日 下午3:48:47
     * @return l 16位uuid数字
     */
    public static long getUUID16Id() {
        UUID16maker uuid16maker = new UUID16maker(1);
        long l = uuid16maker.nextId();
        log.info("=========>uuid16位数字:" + l);
        return l;
    }

    public static void main(String[] args) {
        getUUID16Id();
        getOrderUUID16IdStr();
    }
}
