import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;


public class FareProcessor {
    public final static String STOP_1 = "Stop1";
    public final static String STOP_2 = "Stop2";
    public final static String STOP_3 = "Stop3";
    public final static String NO_TAP_OFF = "None";
    
    private Map<String, Map<String, BigDecimal>> busStopMap = new HashMap<>();

    FareProcessor() {
        Map<String, BigDecimal> fareMap_stop1 = new HashMap<>();
        Map<String, BigDecimal> fareMap_stop2 = new HashMap<>();
        Map<String, BigDecimal> fareMap_stop3 = new HashMap<>();
        
        fareMap_stop1.put(STOP_1, new BigDecimal("0.00"));
        fareMap_stop1.put(STOP_2, new BigDecimal("3.25"));
        fareMap_stop1.put(STOP_3, new BigDecimal("7.30"));
        fareMap_stop1.put(NO_TAP_OFF, new BigDecimal("7.30"));
        busStopMap.put(STOP_1, fareMap_stop1);
        
        fareMap_stop2.put(STOP_1, new BigDecimal("3.25"));
        fareMap_stop2.put(STOP_2, new BigDecimal("0.00"));
        fareMap_stop2.put(STOP_3, new BigDecimal("5.50"));
        fareMap_stop2.put(NO_TAP_OFF, new BigDecimal("5.50"));
        busStopMap.put(STOP_2, fareMap_stop2);
        
        fareMap_stop3.put(STOP_1, new BigDecimal("7.30"));
        fareMap_stop3.put(STOP_2, new BigDecimal("5.50"));
        fareMap_stop3.put(STOP_3, new BigDecimal("0.00"));
        fareMap_stop3.put(NO_TAP_OFF, new BigDecimal("7.30"));
        busStopMap.put(STOP_3, fareMap_stop3);
    }

    public BigDecimal getFare(String startBusStopId, String endBusStopId) {
        return busStopMap.get(startBusStopId).get(endBusStopId);
    }

}
