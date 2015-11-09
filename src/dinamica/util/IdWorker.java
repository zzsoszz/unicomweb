package dinamica.util;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 42位的时间前缀+10位的节点标识+12位的sequence避免并发的数字（12位不够用时强制得到新的时间前�?�?
 * <p>
 * <b>对系统时间的依赖性非常强，需要关闭ntp的时间同步功能，或�?�当�?测到ntp时间调整后，拒绝分配id�?
 * 
 * @author sumory.wu
 * @date 2012-2-26 下午6:40:28
 */
public class IdWorker {
    private final static Logger logger = LoggerFactory.getLogger(IdWorker.class);

    private final long workerId;
    private final long snsEpoch = 1330328109047L;// 起始标记点，作为基准
    private long sequence = 0L;// 0，并发控�?
    private final long workerIdBits = 10L;// 只允许workid的范围为�?0-1023
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 1023,1111111111,10�?
    private final long sequenceBits = 12L;// sequence值控制在0-4095

    private final long workerIdShift = this.sequenceBits;// 12
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;// 4095,111111111111,12�?

    private long lastTimestamp = -1L;

    public IdWorker(long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {// workid < 1024[10位：2�?10次方]
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() throws Exception {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新�?
            //System.out.println("lastTimeStamp:" + lastTimestamp);
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        }
        else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            logger.error(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
            throw new Exception(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        // 生成的timestamp
        return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 保证返回的毫秒数在参数之�?
     * 
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒�?
     * 
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        IdWorker iw1 = new IdWorker(1);
        IdWorker iw2 = new IdWorker(2);
        IdWorker iw3 = new IdWorker(3);

        // System.out.println(iw1.maxWorkerId);
        // System.out.println(iw1.sequenceMask);
//
//        System.out.println("---------------------------");
//
//        long workerId = 1L;
//        long twepoch = 1330328109047L;
//        long sequence = 0L;// 0
//        long workerIdBits = 10L;
//        long maxWorkerId = -1L ^ -1L << workerIdBits;// 1023,1111111111,10�?
//        long sequenceBits = 12L;
//
//        long workerIdShift = sequenceBits;// 12
//        long timestampLeftShift = sequenceBits + workerIdBits;// 22
//        long sequenceMask = -1L ^ -1L << sequenceBits;// 4095,111111111111,12�?
//
//        long ct = System.currentTimeMillis();// 1330328109047L;//
//        System.out.println(ct);
//
//        System.out.println(ct - twepoch);
//        System.out.println(ct - twepoch << timestampLeftShift);// 左移22位：*2�?22次方
//        System.out.println(workerId << workerIdShift);// 左移12位：*2�?12次方
//        System.out.println("哈哈");
//        System.out.println(ct - twepoch << timestampLeftShift | workerId << workerIdShift);
//        long result = ct - twepoch << timestampLeftShift | workerId << workerIdShift | sequence;// 取时间的�?40�? | （小�?1024:只有12位）的低12�? | 计算的sequence
//        System.out.println(result);

        System.out.println("---------------");
        for (int i = 0; i < 1000000; i++) {
            System.out.println(iw1.nextId());
        }

       // 360123613308786792
       // 9223372036854775807L
        System.out.println(9223372036854775807L);
        
//        Long t1 = 66708908575965184l;
//        Long t2 = 66712718304231424l;
//        Long t3 = 66715908575739904l;
//        Long t4 = 66717361423925248l;
//        System.out.println(Long.toBinaryString(t1));
//        System.out.println(Long.toBinaryString(t2));
//        System.out.println(Long.toBinaryString(t3));
//        System.out.println(Long.toBinaryString(t4));
        //1110110011111111011001100001111100 0001100100 000000000000
        //1110110100000010110111010010010010 0001100100 000000000000
        //1110110100000101110000111110111110 0001100100 000000000000
        //1110110100000111000101100011010000 0001100100 000000000000
 //       System.out.println(Long.toBinaryString(66706920114753536l));
        //1110110011111101100101110010010110 0000000001 000000000000

//        String a = "0001100100";//输入数�?? 
//        BigInteger src = new BigInteger(a, 2);//转换为BigInteger类型 
//        System.out.println(src.toString());//转换�?2进制并输出结�? 

    }
}






//
//public class IdWorker {
//	private final long workerId;
//	private final static long twepoch = 1361753741828L;
//	private long sequence = 0L;
//	private final static long workerIdBits = 4L;
//	public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
//	private final static long sequenceBits = 10L;
//
//	private final static long workerIdShift = sequenceBits;
//	private final static long timestampLeftShift = sequenceBits + workerIdBits;
//	public final static long sequenceMask = -1L ^ -1L << sequenceBits;
//
//	private long lastTimestamp = -1L;
//
//	public IdWorker(final long workerId) {
//		super();
//		if (workerId > this.maxWorkerId || workerId < 0) {
//			throw new IllegalArgumentException(String.format(
//					"worker Id can't be greater than %d or less than 0",
//					this.maxWorkerId));
//		}
//		this.workerId = workerId;
//	}
//
//	public synchronized long nextId() {
//		long timestamp = this.timeGen();
//		if (this.lastTimestamp == timestamp) {
//			this.sequence = (this.sequence + 1) & this.sequenceMask;
//			if (this.sequence == 0) {
//				System.out.println("###########" + sequenceMask);
//				timestamp = this.tilNextMillis(this.lastTimestamp);
//			}
//		} else {
//			this.sequence = 0;
//		}
//		if (timestamp < this.lastTimestamp) {
//			try {
//				throw new Exception(
//						String.format(
//								"Clock moved backwards.  Refusing to generate id for %d milliseconds",
//								this.lastTimestamp - timestamp));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		this.lastTimestamp = timestamp;
//		long nextId = ((timestamp - twepoch << timestampLeftShift))
//				| (this.workerId << this.workerIdShift) | (this.sequence);
////		System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
////				+ timestampLeftShift + ",nextId:" + nextId + ",workerId:"
////				+ workerId + ",sequence:" + sequence);
//		return nextId;
//	}
//
//	private long tilNextMillis(final long lastTimestamp) {
//		long timestamp = this.timeGen();
//		while (timestamp <= lastTimestamp) {
//			timestamp = this.timeGen();
//		}
//		return timestamp;
//	}
//
//	private long timeGen() {
//		return System.currentTimeMillis();
//	}
//	
//	
//	public static void main(String[] args){
//		IdWorker worker2 = new IdWorker(2);
//		System.out.println(worker2.nextId());
//	}
//}
//
//
//
//





