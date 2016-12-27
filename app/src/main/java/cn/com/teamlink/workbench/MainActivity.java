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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import cn.com.teamlink.workbench.services.EquipmentService;
import cn.com.teamlink.workbench.services.EquipmentServiceImpl;
import cn.com.teamlink.workbench.services.EquipmentStatus;
import cn.com.teamlink.workbench.utils.InputStreamVolleyRequest;

/**
 * MainActivity.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SHARED_PREFERENCES_NAME = "settings";

    private static final String EQUIPMENT_STATUS_TEXT_TEMPLATE = "当前机台：%1$s 当前状态：%2$s，开始时间：%3$s";

    private static final int STATUS_UPDATE_HANDLE = 0;

    private SharedPreferences preferences;
    private InputStreamVolleyRequest request = null;

    private EquipmentService equipmentService = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        request = new InputStreamVolleyRequest(Request.Method.GET, "http://www.guanmaoyun.com/main.html", new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] bytes) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                try {
                    if (bytes != null) {

                        // Read file name from headers (We have configured API to send file name in "Content-Disposition" header in following format: "File-Name:File-Format" example "MyDoc:pdf"
                        String content = request.responseHeaders.get("Content-Disposition");
                        /*
                        StringTokenizer st = new StringTokenizer(content, "=");

                        int numberOfTokens = st.countTokens();
                        String[] arrTag = new String[numberOfTokens];
                        int x = 0;
                        while (st.hasMoreTokens()) {
                            arrTag[x] = st.nextToken();
                        }

                        String filename = arrTag[1];
                        */

                        String filename = "test.html";
                        // filename = filename.replace(":", ".");
                        Log.i("DEBUG::FILE NAME", filename);

                        InputStream input = null;
                        BufferedOutputStream output = null;
                        try {
                            long lenghtOfFile = bytes.length;

                            //covert reponse to input stream
                            input = new ByteArrayInputStream(bytes);

                            //Create a file on desired path and write stream data to it
                            // File path = Environment.getExternalStorageDirectory();
                            // File sd = Environment.getExternalStorageDirectory();
                            // sd.canWrite();

                            // File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            // File path = Environment.getDownloadCacheDirectory();
                            File path = Environment.getDataDirectory();
                            Log.i("DEBUG::DataDirectory:", Environment.getDataDirectory().getAbsolutePath());
                            // new java.io.File((getActivity().getApplicationContext().getFileStreamPath("FileName.xml").getPath()));
                            // org.apache.commons.io.FileUtils.copyInputStreamToFile(is, file);
                            Log.i("DEBUG::FILE PATH", path.getAbsolutePath());

                            File file = new File(path, filename);
                            map.put("resume_path", file.toString());

                            Log.i("DEBUG::FILE PATH", file.getAbsolutePath());

                            output = new BufferedOutputStream(new FileOutputStream(file));
                            byte data[] = new byte[1024];

                            int count = 0;
                            long total = 0;

                            while ((count = input.read(data)) != -1) {
                                total += count;
                                output.write(data, 0, count);
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } finally {
                            if (output != null) {
                                output.flush();
                            }
                            if (output != null) {
                                output.close();
                            }
                            if (output != null) {
                                input.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);

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
        mToolbar.setTitle(R.string.switch_status);
        // sub title
        // mToolbar.setSubtitle(ToolBar subtitle");
        // 以上3个属性必须在setSupportActionBar(mToolbar)之前调用
        setSupportActionBar(mToolbar);

        // 设置导航Icon，必须在setSupportActionBar(mToolbar)之后设置
        // mToolbar.setNavigationIcon(android.R.drawable.ic_menu_more);
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
    }

    // 获取本机WIFI
    private String getLocalWIFIAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        //返回整型地址转换成“*.*.*.*”地址
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
                //因为使用android.support.v7.widget.SearchView类，可以在onCreateOptionsMenu(Menu menu)中直接设置监听事件
                case R.id.action_search:
                    Snackbar.make(mToolbar, "Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                */
                case R.id.action_share:
                    Snackbar.make(mToolbar, "Click Share", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_more:
                    Snackbar.make(mToolbar, "Click More", Snackbar.LENGTH_SHORT).show();
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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.STOPED.ordinal(),
                                        EquipmentStatus.STOPED.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.WAIT_START.ordinal(),
                                        EquipmentStatus.WAIT_START.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.WAIT_MATERIAL.ordinal(),
                                        EquipmentStatus.WAIT_MATERIAL.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    break;
                case R.id.replaced_mould_button:
                    // Toast.makeText(getApplicationContext(), "replaced_mould_button", Toast.LENGTH_SHORT).show();

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.FIX_MOULD.ordinal(),
                                        EquipmentStatus.FIX_MOULD.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.PRODUCE.ordinal(),
                                        EquipmentStatus.PRODUCE.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.TRIAL_PRODUCE.ordinal(),
                                        EquipmentStatus.TRIAL_PRODUCE.toString()
                                );
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.REPAIR_MACHINE.ordinal(),
                                        EquipmentStatus.REPAIR_MACHINE.toString()
                                );
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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.MAINTENANCE.ordinal(),
                                        EquipmentStatus.MAINTENANCE.toString()
                                );
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

                                equipmentService.writingEquipmentStatusLog(
                                        preferences.getString("serial_no", ""),
                                        preferences.getString("name", ""),
                                        EquipmentStatus.DEBUG_MACHINE.ordinal(),
                                        EquipmentStatus.DEBUG_MACHINE.toString()
                                );
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

            // 更新状态
            new Thread(statusUpdateTask).start();
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
