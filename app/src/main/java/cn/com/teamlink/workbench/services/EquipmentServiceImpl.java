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
import java.sql.Timestamp;
import java.util.Date;
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
    public boolean switchEquipmentStatus(String serialNo, int status, String statsuDesc) {
        String sql = "UPDATE equipment_status SET status = ?, status_desc = ?, timestamp = ? WHERE serial_no = ?";
        int affectCount = DBUtil.execute(sql, status, statsuDesc, new Timestamp(new Date().getTime()), serialNo);
        return ((affectCount > 0)? true : false);
    }

    @Override
    public boolean writingEquipmentStatusLog(String serialNo, String equipmentName, String workOrderNo, String mouldNo, String partCode, String partName, int status, String statsuDesc) {
        String sql = "INSERT INTO equipment_status_log (serial_no, equipment_name, type, type_desc, work_order_no, mould_no, part_code, part_name, status, status_desc, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int affectCount = DBUtil.execute(sql, serialNo, equipmentName, 1, "", workOrderNo, mouldNo, partCode, partName, status, statsuDesc, new Timestamp(new Date().getTime()));
        return ((affectCount > 0)? true : false);
    }

    @Override
    public Map<String, Object> getEquipmentStatus(String serialNo) {
        String sql = "SELECT equipment_name, type, type_desc, work_order_no, mould_no, part_code, part_name, status, status_desc, timestamp FROM equipment_status WHERE serial_no = ?";
        List<Map<String, Object>> results = DBUtil.query(sql, serialNo);
        if(results.size() > 0) {
            return results.get(0);
        } else {
            throw new RuntimeException("找不到设备相关状态配置！");
        }
    }

    @Override
    public List<Map<String, Object>> getWorkOrder() {
        String sql = "SELECT id, work_order_no, mould_no, part_code, part_name, cycle_time, plan_num FROM work_order";
        List<Map<String, Object>> results = DBUtil.query(sql);
        return results;
    }

    @Override
    public boolean replacedMould(String serialNo, String workOrderNo, String mouldNo, String partCode, String partName) {
        String sql = "UPDATE equipment_status SET work_order_no = ?, mould_no = ?, part_code = ?, part_name = ?, status = ?, status_desc = ?, timestamp = ? WHERE serial_no = ?";
        int affectCount = DBUtil.execute(sql, workOrderNo, mouldNo, partCode, partName,
                EquipmentStatus.REPLACED_MOULD.ordinal(), EquipmentStatus.REPLACED_MOULD.toString(),
                new Timestamp(new Date().getTime()),
                serialNo
        );
        return ((affectCount > 0)? true : false);
    }
}
