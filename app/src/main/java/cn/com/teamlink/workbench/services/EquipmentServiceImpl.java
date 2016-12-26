package cn.com.teamlink.workbench.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.com.teamlink.workbench.utils.DBUtil;

public class EquipmentServiceImpl implements EquipmentService {

    @Override
    public String getSerialNumber() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String sql = "SELECT serial_no FROM mould WHERE local_addr = ?";
        DBUtil.query(sql, inetAddress.getHostAddress());
        return null;
    }
}
