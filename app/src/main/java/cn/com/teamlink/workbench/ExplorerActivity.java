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
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cn.com.teamlink.workbench.utils.InputStreamVolleyRequest;

/**
 * ExplorerActivity.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public class ExplorerActivity extends AppCompatActivity {

    private static final String TAG = "ExplorerActivity";

    private GridView gridview;
    protected ArrayList<HashMap<String, String>> srcTable;
    // 适配器
    protected SimpleAdapter saTable;

    private InputStreamVolleyRequest request;


    public void onCreate(Bundle savedInstanceState) {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 强制为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explorer);
        gridview = (GridView) findViewById(R.id.explorer_grid_view);

        srcTable = new ArrayList<HashMap<String, String>>();
        saTable = new SimpleAdapter(
                this,
                // 数据来源
                srcTable,
                R.layout.explorer_item,
                new String[]{"ItemText"},
                new int[]{R.id.item_text_view}
        );

        // 添加并且显示
        gridview.setAdapter(saTable);
        // 添加消息处理
        gridview.setOnItemClickListener(new ItemClickListener());

        // 添加表头
        addHeader();

        // 添加数据测试
        addData();


        try {
            // 定义文件名
            String fileName = "fileDemo.txt";
            // 以私有模式创建文件
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            String text = "Some data";
            // 写入数据
            fos.write(text.getBytes());
            // 将缓冲区剩余数据写入文件
            fos.flush();
            // 关闭FileOutputStream
            fos.close();

            /*
            String fileName = "fileDemo.txt";
            FileInputStream fis = openFileInput(fileName);
            byte[] readBytes = new byte[fis.available()];
            while (fis.read(readBytes) != -1) {

            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // TODO ORIENTATION_LANDSCAPE
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // TODO ORIENTATION_PORTRAIT
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);

            // 显示所选Item的ItemText
            Toast.makeText(getApplicationContext(), (String) item.get("ItemText"), Toast.LENGTH_SHORT).show();

            request = new InputStreamVolleyRequest(Request.Method.GET, "http://www.guanmaoyun.com/main.html", new Response.Listener<byte[]>() {
                @Override
                public void onResponse(byte[] bytes) {
                    // HashMap<String, Object> map = new HashMap<String, Object>();
                    try {
                        if (bytes != null) {

                        /*
                        // Read file name from headers (We have configured API to send file name in "Content-Disposition" header in following format: "File-Name:File-Format" example "MyDoc:pdf"
                        String content = request.responseHeaders.get("Content-Disposition");
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

                                Log.d(TAG, "检验sdcard是否可用?");
                                //判断sdcard是否存在?
                                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    Log.d(TAG, "sdcard不可用!");
                                    Toast.makeText(getApplicationContext(), "没有找到SDCard!", Toast.LENGTH_LONG);
                                    return;
                                }
                                ;

                                // File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                // File path = Environment.getDownloadCacheDirectory();
                                // File path = Environment.getDataDirectory();
                                // Log.i("DEBUG::DataDirectory:", Environment.getDataDirectory().getAbsolutePath());
                                File path = Environment.getExternalStorageDirectory();

                                // 获取sdcard的大小
                                long blockSize = 0;
                                long blockCount = 0;
                                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                                // FIXME Make sure we're running on HONEYCOMB or higher to use APIs
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    blockSize = statFs.getBlockSizeLong();
                                    blockCount = statFs.getBlockSizeLong();
                                } else {
                                    blockSize = statFs.getBlockSize();
                                    blockCount = statFs.getBlockCount();
                                }
                                long sdCardSize = blockSize * blockCount;
                                Log.d(TAG, String.valueOf(sdCardSize));

                                // new java.io.File((getActivity().getApplicationContext().getFileStreamPath("FileName.xml").getPath()));
                                // org.apache.commons.io.FileUtils.copyInputStreamToFile(is, file);
                                Log.i("DEBUG::FILE PATH", path.getAbsolutePath());

                                File file = new File(path, filename);
                                // map.put("resume_path", file.toString());
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
    }

    public void addHeader() {
        String items[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        for (String strText : items) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemText", strText);
            srcTable.add(map);
        }
        saTable.notifyDataSetChanged(); //更新数据
    }

    public void addData() {

        for (int i = 0; i < 3; i++) {
            String items[] = {"语文", "数学", "英语", "体育", "计算机", "化学"};
            for (String strText : items) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemText", strText);
                srcTable.add(map);
            }
        }
        // 更新数据
        saTable.notifyDataSetChanged();
    }

    // 清空列表
    public void removeAll() {
        srcTable.clear();
        saTable.notifyDataSetChanged();
    }
}
