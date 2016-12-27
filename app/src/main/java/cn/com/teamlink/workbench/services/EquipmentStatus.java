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

/**
 * EquipmentStatus.java
 * 停机为0，生产为1，待人开机为3，待料为4，修模为5，试产为6，修机为7，保养为8，调机为9
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public enum EquipmentStatus {

    STOPED {
        public String toString() {
            return "停机";
        }
    },
    PRODUCE {
        public String toString() {
            return "生产";
        }
    },
    WAIT_START {
        public String toString() {
            return "待人开机";
        }
    },
    WAIT_MATERIAL {
        public String toString() {
            return "待料";
        }
    },
    FIX_MOULD {
        public String toString() {
            return "修模";
        }
    },
    TRIAL_PRODUCE {
        public String toString() {
            return "试产";
        }
    },
    REPAIR_MACHINE {
        public String toString() {
            return "修机";
        }
    },
    MAINTENANCE {
        public String toString() {
            return "保养";
        }
    },
    DEBUG_MACHINE {
        public String toString() {
            return "调机";
        }
    };
}