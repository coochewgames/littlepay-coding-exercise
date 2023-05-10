import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;


public class FareProcessor {
    private Map<BusStop, Map<BusStop, BigDecimal>> busStopMap = new HashMap<>();

    FareProcessor() {
        Map<BusStop, BigDecimal> fareMap_stop1 = new HashMap<>();
        Map<BusStop, BigDecimal> fareMap_stop2 = new HashMap<>();
        Map<BusStop, BigDecimal> fareMap_stop3 = new HashMap<>();
        
        fareMap_stop1.put(BusStop.STOP_1, new BigDecimal("0.00"));
        fareMap_stop1.put(BusStop.STOP_2, new BigDecimal("3.25"));
        fareMap_stop1.put(BusStop.STOP_3, new BigDecimal("7.30"));
        fareMap_stop1.put(BusStop.NO_TAP_ON, new BigDecimal("7.30"));
        fareMap_stop1.put(BusStop.NO_TAP_OFF, new BigDecimal("7.30"));
        busStopMap.put(BusStop.STOP_1, fareMap_stop1);
        
        fareMap_stop2.put(BusStop.STOP_1, new BigDecimal("3.25"));
        fareMap_stop2.put(BusStop.STOP_2, new BigDecimal("0.00"));
        fareMap_stop2.put(BusStop.STOP_3, new BigDecimal("5.50"));
        fareMap_stop2.put(BusStop.NO_TAP_ON, new BigDecimal("5.50"));
        fareMap_stop2.put(BusStop.NO_TAP_OFF, new BigDecimal("5.50"));
        busStopMap.put(BusStop.STOP_2, fareMap_stop2);
        
        fareMap_stop3.put(BusStop.STOP_1, new BigDecimal("7.30"));
        fareMap_stop3.put(BusStop.STOP_2, new BigDecimal("5.50"));
        fareMap_stop3.put(BusStop.STOP_3, new BigDecimal("0.00"));
        fareMap_stop3.put(BusStop.NO_TAP_ON, new BigDecimal("7.30"));
        fareMap_stop3.put(BusStop.NO_TAP_OFF, new BigDecimal("7.30"));
        busStopMap.put(BusStop.STOP_3, fareMap_stop3);
    }

    public BigDecimal getFare(BusStop startBusStopId, BusStop endBusStopId) {
        return busStopMap.get(startBusStopId).get(endBusStopId);
    }
}
