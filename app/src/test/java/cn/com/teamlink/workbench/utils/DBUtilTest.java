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
//package cn.com.teamlink.workbench.utils;
//
//import org.junit.Test;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
///**
// * DBUtilTest.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2016年12月27日 上午11:38:34
// */
//public class DBUtilTest {
//
//    @Test
//    public void query() throws Exception {
//        List<Map<String, Object>> results = DBUtil.query("SELECT count(*) as row_count FROM enum_data");
//        System.out.println(results.get(0).get("row_count"));
//
//        String str = "<action type=\"%1$s\">%2$s</action>";
//        String value = String.format(str,"_message_","error");
//        System.out.println(value);
//    }
//
//}