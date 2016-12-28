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

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * EquipmentService.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public interface EquipmentService {

    /**
     * 获取设备编号
     *
     * @param localAddr
     * @return
     * @throws UnknownHostException
     */
    Map<String, Object> getEquipmentSerialNumber(String localAddr) throws UnknownHostException;

    /**
     * 切换设备状态
     *
     * @param serialNo
     * @param status
     * @param statsuDesc
     * @return
     */
    boolean switchEquipmentStatus(String serialNo, int status, String statsuDesc);

    /**
     * 获取设备状态
     *
     * @param serialNo
     * @return
     */
    Map<String, Object> getEquipmentStatus(String serialNo);

    /**
     * 记录状态日志
     *
     * @param serialNo
     * @param equipmentName
     * @param status
     * @param statsuDesc
     * @return
     */
    boolean writingEquipmentStatusLog(String serialNo, String equipmentName, int status, String statsuDesc);

    /**
     * 获取工单列表
     *
     * @return
     */
    List<Map<String, Object>> getWorkOrder();

    /**
     * 换模
     *
     * @param serialNo
     * @param workOrderNo
     * @param mouldNo
     * @param partCode
     * @param partName
     * @return
     */
    boolean replacedMould(String serialNo, String workOrderNo, String mouldNo, String partCode, String partName);
}
