/*
 * Copyright (C) 2015 The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * EquipmentServiceImpl.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public class EquipmentServiceImpl implements EquipmentService {

    private static final String TAG = "EquipmentServiceImpl";

    @Override
    public Map<String, Object> getEquipmentSerialNumber(String localAddr) throws UnknownHostException {
        // InetAddress inetAddress = InetAddress.getLocalHost();
        // Log.i(TAG, "inetAddress.getHostAddress():" + inetAddress.getHostAddress());

        // Log.i(TAG, "localAddr:" + localAddr);

        String sql = "SELECT name, local_addr, serial_no FROM equipment WHERE local_addr = ?";
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

    @Override
    public Boolean switchEquipmentStatus(String serialNo, int status, String statsuDesc) {
        String sql = "UPDATE equipment_status SET status = ?, status_desc = ? WHERE serial_no = ?";
        int affectCount = DBUtil.execute(sql, status, statsuDesc, serialNo);
        return ((affectCount > 0)? true : false);
    }
}
