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
package cn.com.teamlink.workbench;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.teamlink.workbench.services.EquipmentService;
import cn.com.teamlink.workbench.services.EquipmentServiceImpl;
import cn.com.teamlink.workbench.services.EquipmentStatus;

/**
 * MainActivity.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String SHARED_PREFERENCES_NAME = "settings";

    private static final String EQUIPMENT_STATUS_TEXT_TEMPLATE = "当前机台：%1$s 当前状态：正在%2$s，开始时间：%3$s";

    private static final int STATUS_UPDATE_HANDLE = 0;
    private static final int SHOW_POPUP_WINDOW_HANDLE = 1;

    private SharedPreferences preferences;

    private EquipmentService equipmentService;

    private ArrayList<HashMap<String, Object>> mPopupWindowListItem;

    private Toolbar mToolbar;
    private Button mScheduledDowntimeButton,
            mWaitStartButton,
            mWaitMaterialButton,
            mReplacedMouldButton,
            mFixMouldButton,
            mProduceButton,
            mTrialProduceButton,
            mRepairMachineButton,
            mMaintenanceButton,
            mDebugMachineButton;
    private TextView mEquipmentStatusTextView;
    private PopupWindow popupWindow;
    private ListView mPopupMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        // view.setKeepScreenOn(true)

        initViews();

        /*
        if (savedInstanceState != null && savedInstanceState.containsKey("main_status")) {
            // mSelectPosition = savedInstanceState.getInt("main_status");
        }
        */

        preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        equipmentService = new EquipmentServiceImpl();
        // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
        new Thread(networkTask).start();
    }

    @Override
    protected void onResume() {
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // 更新状态
        new Thread(statusUpdateTask).start();

        super.onResume();
    }

    /**
     * 加载菜单布局
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initViews() {

        // 用toolbar替换actionbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // app logo
        // mToolbar.setLogo(R.mipmap.ic_launcher);
        // title
        mToolbar.setTitle("联机管理");
        // sub title
        mToolbar.setSubtitle(R.string.switch_status);
        // 以上3个属性必须在setSupportActionBar(mToolbar)之前调用
        setSupportActionBar(mToolbar);

        // 设置导航Icon，必须在setSupportActionBar(mToolbar)之后设置
        // mToolbar.setNavigationIcon(android.R.drawable.ic_menu_more);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // 添加菜单点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mToolbar, "Click setNavigationIcon", Snackbar.LENGTH_SHORT).show();
            }
        });
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        mScheduledDowntimeButton = (Button) findViewById(R.id.scheduled_downtime_button);
        mScheduledDowntimeButton.setOnClickListener(mOnClickListener);
        mWaitStartButton = (Button) findViewById(R.id.wait_start_button);
        mWaitStartButton.setOnClickListener(mOnClickListener);
        mWaitMaterialButton = (Button) findViewById(R.id.wait_material_button);
        mWaitMaterialButton.setOnClickListener(mOnClickListener);
        mReplacedMouldButton = (Button) findViewById(R.id.replaced_mould_button);
        mReplacedMouldButton.setOnClickListener(mOnClickListener);
        mFixMouldButton = (Button) findViewById(R.id.fix_mould_button);
        mFixMouldButton.setOnClickListener(mOnClickListener);
        mProduceButton = (Button) findViewById(R.id.produce_button);
        mProduceButton.setOnClickListener(mOnClickListener);
        mTrialProduceButton = (Button) findViewById(R.id.trial_produce_button);
        mTrialProduceButton.setOnClickListener(mOnClickListener);
        mRepairMachineButton = (Button) findViewById(R.id.repair_machine_button);
        mRepairMachineButton.setOnClickListener(mOnClickListener);
        mMaintenanceButton = (Button) findViewById(R.id.maintenance_button);
        mMaintenanceButton.setOnClickListener(mOnClickListener);
        mDebugMachineButton = (Button) findViewById(R.id.debug_machine_button);
        mDebugMachineButton.setOnClickListener(mOnClickListener);
        mEquipmentStatusTextView = (TextView) findViewById(R.id.equipment_status_text_view);

        // FIXME PopupWindow
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.layout_popup_menu, null);
        // 创建PopupWindow对象 ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow = new PopupWindow(view, 1200, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        // new ColorDrawable(0) getResources().getDrawable(R.drawable.popup_window_background)
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_dropdown_panel_pi));
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        // 设置动画样式
        // popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 设置窗口消失事件
        // popupWindow.setOnDismissListenerd(new PopupWindow.OnDismissListener(){});
            /*
            // 点击PopupWindow区域外部,PopupWindow消失
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            */

        mPopupMenuListView = (ListView) view.findViewById(R.id.main_popup_menu_list_view);
        mPopupMenuListView.setFastScrollAlwaysVisible(true);
        mPopupMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.i(TAG, position + " " + id);
                    Map<String, Object> item = mPopupWindowListItem.get(position);
                    final String workOrderNo = String.valueOf(item.get("work_order_no"));
                    final String mouldNo = String.valueOf(item.get("mould_no"));
                    final String partCode = String.valueOf(item.get("part_code"));
                    final String partName = String.valueOf(item.get("part_name"));
                    // final String cycleTime = String.valueOf(item.get("cycle_time"));
                    // final String planNum = String.valueOf(item.get("plan_num"));

                    Log.i(TAG, workOrderNo + " " + mouldNo);
                    Log.i(TAG, item.get("work_order_no") + " " + item.get("mould_no"));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            equipmentService.replacedMould(
                                    preferences.getString("serial_no", ""),
                                    workOrderNo,
                                    mouldNo,
                                    partCode,
                                    partName
                            );

                            equipmentService.writingEquipmentStatusLog(
                                    preferences.getString("serial_no", ""),
                                    preferences.getString("name", ""),
                                    workOrderNo,
                                    mouldNo,
                                    partCode,
                                    partName,
                                    EquipmentStatus.REPLACED_MOULD.ordinal(),
                                    EquipmentStatus.REPLACED_MOULD.toString()
                            );

                            // 更新状态
                            new Thread(statusUpdateTask).start();

                            Snackbar.make(mToolbar, "换模成功！", Snackbar.LENGTH_SHORT).show();
                        }
                    }).start();
                } catch (Exception e) {
                    Snackbar.make(mToolbar, "换模失败！", Snackbar.LENGTH_SHORT).show();
                }
                popupWindow.dismiss();
            }
        });
    }

    // 获取本机WIFI
    private String getLocalWIFIAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

    // 3G网络IP
    private String getLocalWLANAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                /*
                // 因为使用android.support.v7.widget.SearchView类，可以在onCreateOptionsMenu(Menu menu)中直接设置监听事件
                case R.id.action_search:
                    Snackbar.make(mToolbar, "Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                */
                case R.id.action_share:
                    // TODO
                    // Snackbar.make(mToolbar, "Click Share", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_more:
                    // TODO
                    // Snackbar.make(mToolbar, "Click More", Snackbar.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.scheduled_downtime_button:
                    // Toast.makeText(getApplicationContext(), "scheduled_downtime_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();
                    break;
                case R.id.wait_start_button:
                    // Toast.makeText(getApplicationContext(), "wait_start_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.WAIT_START.ordinal(),
                                        EquipmentStatus.WAIT_START.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.WAIT_START.ordinal(),
                                        EquipmentStatus.WAIT_START.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();
                    break;
                case R.id.wait_material_button:
                    // Toast.makeText(getApplicationContext(), "wait_material_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.WAIT_MATERIAL.ordinal(),
                                        EquipmentStatus.WAIT_MATERIAL.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.WAIT_MATERIAL.ordinal(),
                                        EquipmentStatus.WAIT_MATERIAL.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();

                    break;
                case R.id.replaced_mould_button:
                    // Toast.makeText(getApplicationContext(), "replaced_mould_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (mPopupWindowListItem == null) {
                                    mPopupWindowListItem = new ArrayList<HashMap<String, Object>>();

                                    List<Map<String, Object>> workOrders = equipmentService.getWorkOrder();
                                    for (Map<String, Object> workOrder : workOrders) {
                                        String id = String.valueOf(workOrder.get("id"));
                                        String workOrderNo = String.valueOf(workOrder.get("work_order_no"));
                                        String mouldNo = String.valueOf(workOrder.get("mould_no"));
                                        String partCode = String.valueOf(workOrder.get("part_code"));
                                        String partName = String.valueOf(workOrder.get("part_name"));
                                        String cycleTime = String.valueOf(workOrder.get("cycle_time"));
                                        String planNum = String.valueOf(workOrder.get("plan_num"));

                                        HashMap<String, Object> workOrderItem = new HashMap<String, Object>();
                                        workOrderItem.put(
                                                "item_title",
                                                new StringBuffer()
                                                        .append(workOrderNo)
                                                        .append(" ")
                                                        .append("(")
                                                        .append(mouldNo).append("|")
                                                        .append(partCode).append("|")
                                                        .append(partName)
                                                        .append(")")
                                        );
                                        workOrderItem.put("work_order_no", workOrderNo);
                                        workOrderItem.put("mould_no", mouldNo);
                                        workOrderItem.put("part_code", partCode);
                                        workOrderItem.put("part_name", partName);
                                        mPopupWindowListItem.add(workOrderItem);
                                    }

                                    mPopupMenuListView.setAdapter(
                                            // BaseAdapter mSimpleAdapter =
                                            // SimpleAdapter mSimpleAdapter = new SimpleAdapter(getApplicationContext(), mPopupWindowListItem, R.layout.main_popup_menu_item, new String[]{"item_icon", "item_title"}, new int[]{R.id.popup_menu_item_icon, R.id.popup_menu_item_title});
                                            new BaseAdapter() {

                                                @Override
                                                public int getCount() {
                                                    return mPopupWindowListItem.size();
                                                }

                                                @Override
                                                public Object getItem(int i) {
                                                    return null;
                                                }

                                                @Override
                                                public long getItemId(int i) {
                                                    return 0;
                                                }

                                                @Override
                                                public View getView(int position, View convertView, ViewGroup parent) {

                                                    if (convertView == null) {
                                                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                                                        convertView = inflater.inflate(R.layout.popup_menu_list_item, parent, false);
                                                    }

                                                    TextView popupMenuItemTitle = (TextView) convertView.findViewById(R.id.popup_menu_list_item_title);
                                                    popupMenuItemTitle.setText(mPopupWindowListItem.get(position).get("item_title").toString());

                                                    return convertView;
                                                }
                                            }
                                    );
                                }

                                Message msg = new Message();
                                msg.what = SHOW_POPUP_WINDOW_HANDLE;
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                Log.e(TAG, e.getLocalizedMessage(), e);
                            }
                        }
                    }).start();
                    break;
                case R.id.fix_mould_button:
                    // Toast.makeText(getApplicationContext(), "fix_mould_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.FIX_MOULD.ordinal(),
                                        EquipmentStatus.FIX_MOULD.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();

                    break;
                case R.id.produce_button:
                    // Toast.makeText(getApplicationContext(), "produce_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.PRODUCE.ordinal(),
                                        EquipmentStatus.PRODUCE.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();

                    break;
                case R.id.trial_produce_button:
                    // Toast.makeText(getApplicationContext(), "trial_produce_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.TRIAL_PRODUCE.ordinal(),
                                        EquipmentStatus.TRIAL_PRODUCE.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // 更新状态
                            new Thread(statusUpdateTask).start();
                        }
                    }).start();

                    break;
                case R.id.repair_machine_button:
                    // Toast.makeText(getApplicationContext(), "repair_machine_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.REPAIR_MACHINE.ordinal(),
                                        EquipmentStatus.REPAIR_MACHINE.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );

                                // 更新状态
                                new Thread(statusUpdateTask).start();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    break;
                case R.id.maintenance_button:
                    // Toast.makeText(getApplicationContext(), "maintenance_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.MAINTENANCE.ordinal(),
                                        EquipmentStatus.MAINTENANCE.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );

                                // 更新状态
                                new Thread(statusUpdateTask).start();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    break;
                case R.id.debug_machine_button:
                    // Toast.makeText(getApplicationContext(), "debug_machine_button", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 切换状态
                                equipmentService.switchEquipmentStatus(
                                        preferences.getString("serial_no", ""),
                                        EquipmentStatus.DEBUG_MACHINE.ordinal(),
                                        EquipmentStatus.DEBUG_MACHINE.toString()
                                );

                                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                                        .getEquipmentStatus(preferences.getString("serial_no", ""));


                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        String.valueOf(equipmentStatus.get("work_order_no")),
                                        String.valueOf(equipmentStatus.get("mould_no")),
                                        String.valueOf(equipmentStatus.get("part_code")),
                                        String.valueOf(equipmentStatus.get("part_name")),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );

                                // 更新状态
                                new Thread(statusUpdateTask).start();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    break;
                default:
                    break;
            }
        }
    };


    private Runnable statusUpdateTask = new Runnable() {
        @Override
        public void run() {
            try {
                // handler.postDelayed(this, 1000);

                Map<String, Object> equipmentStatus = new EquipmentServiceImpl()
                        .getEquipmentStatus(preferences.getString("serial_no", ""));

                String value = String.format(
                        EQUIPMENT_STATUS_TEXT_TEMPLATE,
                        equipmentStatus.get("equipment_name"),
                        equipmentStatus.get("status_desc"),
                        equipmentStatus.get("timestamp")
                );
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                Message msg = new Message();
                msg.what = STATUS_UPDATE_HANDLE;
                Bundle data = new Bundle();
                data.putString("text", value);
                // data.putSerializable("data", "");
                msg.setData(data);
                handler.sendMessage(msg);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case STATUS_UPDATE_HANDLE:
                    Bundle data = msg.getData();
                    String val = data.getString("text");
                    // getIntent().getSerializableExtra("data");

                    Log.i(TAG, "请求结果为：" + val);

                    // UI界面的更新等相关操作
                    mEquipmentStatusTextView.setText(val);
                    break;
                case SHOW_POPUP_WINDOW_HANDLE:
                    if (popupWindow.isShowing()) {
                        // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
                        popupWindow.dismiss();
                    } else {
                        // 显示窗口
                        // 设置显示PopupWindow的位置位于View的右下方，x,y表示坐标偏移量
                        // popupWindow.showAsDropDown(v, -460, 0);
                        // （以某个View为参考）,表示弹出窗口以parent组件为参考，位于左侧，偏移-90。
                        // Gravity.TOP|Gravity.LEFT, 0, 150
                        // popupWindow.showAtLocation(v, Gravity.RIGHT, 0, 0);
                        popupWindow.showAtLocation(findViewById(R.id.activity_main), Gravity.CENTER, 0, 0);
                        popupWindow.update();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO 在这里进行网络请求相关操作

            /*
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            // getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
            String serialNo = sharedPreferences.getString("serial_no", "");
            */

            SharedPreferences.Editor editor = preferences.edit();
            try {
                Map<String, Object> resultMap = equipmentService.getEquipmentSerialNumber(getLocalWIFIAddress());

                // 本机IP，设备编号
                editor.putString("name", resultMap.get("name").toString());
                editor.putString("local_addr", resultMap.get("local_addr").toString());
                editor.putString("serial_no", resultMap.get("serial_no").toString());
                editor.commit();

                /*
                Snackbar.make(
                        mToolbar,
                        new StringBuffer()
                                .append(resultMap.get("name").toString())
                                .append(":")
                                .append(resultMap.get("local_addr").toString())
                                .append(":")
                                .append(resultMap.get("serial_no").toString()),
                        Snackbar.LENGTH_LONG).show();
                */

                Log.i(
                        TAG,
                        new StringBuffer()
                                .append(resultMap.get("name").toString())
                                .append(":")
                                .append(resultMap.get("local_addr").toString())
                                .append(":")
                                .append(resultMap.get("serial_no").toString())
                                .toString()
                );

            } catch (UnknownHostException ex) {
                Log.e(TAG, "获取本机IP地址失败！", ex);
            } catch (Exception ex) {
                Log.e(TAG, "获取设备信息失败！", ex);
            }

            /*
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("data", "value");
            // data.putSerializable("data", "");
            msg.setData(data);
            handler.sendMessage(msg);
            */
        }
    };
}
