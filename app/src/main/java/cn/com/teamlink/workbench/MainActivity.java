package cn.com.teamlink.workbench;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;

import cn.com.teamlink.workbench.services.EquipmentService;
import cn.com.teamlink.workbench.services.EquipmentServiceImpl;

/**
 * 状态转换
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EquipmentService equipmentService = null;
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // view.setKeepScreenOn(true)

        // 用toolbar替换actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // app logo
        // toolbar.setLogo(R.mipmap.ic_launcher);
        // title
        toolbar.setTitle(R.string.switch_status);
        // sub title
        // toolbar.setSubtitle(ToolBar subtitle");
        // 以上3个属性必须在setSupportActionBar(toolbar)之前调用
        setSupportActionBar(toolbar);

        // 设置导航Icon，必须在setSupportActionBar(toolbar)之后设置
        // toolbar.setNavigationIcon(android.R.drawable.ic_menu_more);
        // 添加菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar, "Click setNavigationIcon", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);

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
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("data");
            // getIntent().getSerializableExtra("data");
            Log.i("mylog", "请求结果为：" + val);

            // TODO UI界面的更新等相关操作
        }
    };
    */

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO 在这里进行网络请求相关操作

            /*
            SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            // getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
            String serialNo = sharedPreferences.getString("serial_no", "");
            */

            // 使用SharedPreferences进行数据存储
            SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                Map<String, Object> resultMap = equipmentService.getSerialNumber(getLocalWIFIAddress());

                // 本机IP，设备编号
                editor.putString("local_addr", resultMap.get("local_addr").toString());
                editor.putString("serial_no", resultMap.get("serial_no").toString());
                editor.commit();

                Snackbar.make(
                        toolbar,
                        new StringBuffer()
                                .append(resultMap.get("local_addr").toString())
                                .append(":")
                                .append(resultMap.get("local_addr").toString()),
                        Snackbar.LENGTH_LONG).show();

                Log.i(
                        TAG,
                        new StringBuffer()
                                .append(resultMap.get("local_addr").toString())
                                .append(":")
                                .append(resultMap.get("local_addr").toString())
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

    /**
     * 该方法是用来加载菜单布局的
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

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                /*
                //因为使用android.support.v7.widget.SearchView类，可以在onCreateOptionsMenu(Menu menu)中直接设置监听事件
                case R.id.action_search:
                    Snackbar.make(toolbar, "Click Search", Snackbar.LENGTH_SHORT).show();
                    break;
                */
                case R.id.action_share:
                    Snackbar.make(toolbar, "Click Share", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar, "Click More", Snackbar.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };

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
    public static String getLocalWLANAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
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
}
