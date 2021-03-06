///*
// * Copyright (C) 2015 The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package cn.com.teamlink.workbench.services;
//
//import org.junit.Test;
//
//import java.util.Map;
//
//import cn.com.teamlink.workbench.utils.DBUtil;
//
//import static org.junit.Assert.*;
//
///**
// * EquipmentServiceImplTest.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016年12月27日 上午11:38:34
// */
//public class EquipmentServiceImplTest {
//
//    @Test
//    public void getSerialNumber() throws Exception {
//        Map<String, Object> resultMap = new EquipmentServiceImpl().getEquipmentSerialNumber("127.0.0.1");
//        System.out.println(new StringBuffer()
//                .append(resultMap.get("name").toString())
//                .append(":")
//                .append(resultMap.get("local_addr").toString())
//                .append(":")
//                .append(resultMap.get("serial_no").toString()));
//    }
//
//    @Test
//    public void switchEquipmentStatus() {
//        System.out.println(EquipmentStatus.DEBUG_MACHINE.ordinal());
//        System.out.println(EquipmentStatus.DEBUG_MACHINE.toString());
//        new EquipmentServiceImpl().switchEquipmentStatus(
//                "6b95cb3a-cb4e-11e6-952f-507b9db02f92",
//                EquipmentStatus.DEBUG_MACHINE.ordinal(),
//                EquipmentStatus.DEBUG_MACHINE.toString()
//        );
//    }
//
//    @Test
//    public void getEquipmentStatus() {
//        Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
//                .getEquipmentStatus("6b95cb3a-cb4e-11e6-952f-507b9db02f94");
//        System.out.println(equipmentStatus.get("status_desc"));
//    }
//
//    @Test
//    public void writingEquipmentStatusLog() {
//        new EquipmentServiceImpl().writingEquipmentStatusLog(
//                "6b95cb3a-cb4e-11e6-952f-507b9db02f92",
//                "MOULD1",
//                "",
//                "",
//                "",
//                "",
//                EquipmentStatus.DEBUG_MACHINE.ordinal(),
//                EquipmentStatus.DEBUG_MACHINE.toString()
//        );
//    }
//
//}