package cn.com.teamlink.workbench.services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public interface EquipmentService {

    Map<String, Object> getSerialNumber(String localAddr) throws UnknownHostException;
}
