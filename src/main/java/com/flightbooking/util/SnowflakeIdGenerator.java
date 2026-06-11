package com.flightbooking.util;

/**
 * 闆姳绠楁硶鍒嗗竷寮廔D鐢熸垚鍣?
 *
 * 64浣岻D缁撴瀯:
 *   0 - 0000000000 0000000000 0000000000 0000000000 0 - 0000000000 - 0000 00000000
 *   鈫?                        鈫?                         鈫?         鈫?
 *  绗﹀彿浣?1)              鏃堕棿鎴?41)                 鏈哄櫒ID(10)   搴忓垪鍙?12)
 */
public class SnowflakeIdGenerator {
    // ===== Bit 鍒嗛厤甯搁噺 =====
    // TODO: 浣犳潵鍐欎笅闈㈣繖涓変釜甯搁噺

    /** 鏈哄櫒ID鍗犵敤鐨勪綅鏁?- 10浣?*/
    private static final long WORKER_ID_BITS = 10L;

    /** 搴忓垪鍙峰崰鐢ㄧ殑浣嶆暟 - 12浣?*/
    private static final long SEQUENCE_BITS = 12L;

    /** 搴忓垪鍙锋渶澶у€?= 2^12 - 1 = 4095 */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /** 鏈哄櫒鐮佽宸︾Щ12浣?*/
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 鏃堕棿鎴宠宸︾Щ锛堟満鍣ㄧ爜+搴忓垪鍙凤級 10+12 = 22浣?*/
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 鏈哄櫒鐮?*/
    private final long workerId;

    /** 涓婃鍙戝彿鐨勬椂闂?锛堟绉掓椂闂存埑锛?*/
    private long lastTimestamp = -1L;

    /** 褰撳墠姣鍐呯殑搴忓彿 */
    private long sequence = 0L;

    private static final long START_EPOCH = 1704067200000L;

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    public SnowflakeIdGenerator(long workerId){
        if(workerId < 0 || workerId > MAX_WORKER_ID){
            throw new IllegalArgumentException("workerId out of index");
        }else
            this.workerId = workerId;
    }

    /**
     * 鐢熸垚涓嬩竴涓垎甯冨紡ID
     */
    public synchronized long nextId() {
        // 1. 鑾峰彇褰撳墠鏃堕棿鎴筹紙姣锛?
        // TODO: 鐢?System.currentTimeMillis()
        long currentTimestamp = System.currentTimeMillis();
        // 2. 鏃堕挓鍥炴嫧妫€鏌ワ紙褰撳墠鐨勬椂閽熶笉鑳藉皬浜庝笂涓€娆¤皟鐢ㄩ洩鑺辩畻娉曠殑鏃堕挓锛屽鏋滃皬浜庯紝鍒欒瘉鏄庢椂闂磋鍥炴嫧浜嗭紝灏变細瀵艰嚧ID 閲嶅鐢熸垚锛?
        if(currentTimestamp < lastTimestamp){
            throw new RuntimeException("Current timestamp is less than last timestamp!!"); 
        }
        // 3. 搴忓垪鍙峰鐞?
        /**
         *  濡傛灉 currentTimestamp == lastTimestamp锛堝悓涓€姣鍐咃級锛?
                sequence 閫掑锛屼絾涓嶈兘瓒呰繃 MAX_SEQUENCE
                濡傛灉 sequence 瓒呰繃 MAX_SEQUENCE锛屽氨绛変笅涓€姣
            鍚﹀垯锛堟柊鐨勪竴姣锛夛細
                sequence 閲嶇疆涓?0
         */
        if(currentTimestamp == lastTimestamp){
            // 浣嶈繍绠楀疄鐜皊equence number鐨勬孩鍑?
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if(sequence == 0){
                // 杩欓噷浣跨敤鐨勬槸锛岃嚜鏃嬫煡璇㈡椂闂存槸鍚﹀埌涓嬩竴姣锛屽氨鏄竴鐩村幓鍒ゆ柇褰撳墠鐨勬椂閽熸槸鍚﹀拰涓婁竴娆＄殑鏃堕挓涓€鏍锋垨灏忎簬
                while(currentTimestamp<=lastTimestamp){
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        }else{
            // 姣忎釜鏃堕挓寮€濮嬶紝sequence number閮借鏄?;
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;
        // 4. 鎷兼帴ID
        long id = (currentTimestamp - START_EPOCH << TIMESTAMP_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
        return id;
    }
}
