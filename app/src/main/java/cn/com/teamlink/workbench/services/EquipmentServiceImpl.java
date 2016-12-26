package cn.com.teamlink.workbench.services;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import cn.com.teamlink.workbench.utils.DBUtil;

public class EquipmentServiceImpl implements EquipmentService {

    private static final String TAG = "MainActivity";

    @Override
    public Map<String, Object> getSerialNumber(String localAddr) throws UnknownHostException {
        // InetAddress inetAddress = InetAddress.getLocalHost();
        // Log.i(TAG, "inetAddress.getHostAddress():" + inetAddress.getHostAddress());

        Log.i(TAG, "localAddr:" + localAddr);

        String sql = "SELECT local_addr, serial_no FROM mould WHERE local_addr = ?";
        List<Map<String, Object>> results = DBUtil.query(sql, localAddr);
        if(results.size() > 0) {
            return results.get(0);
        } else {
            throw new RuntimeException("找不到设备相关配置！");
        }
    }

    /*
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        // return inetAddress.getHostAddress().toString();
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }
    */
}
