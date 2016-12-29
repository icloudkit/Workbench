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

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    protected ArrayList<HashMap<String, String>> gridViewListItems;
    // 适配器
    protected BaseAdapter gridViewListItemAdapter;

    private InputStreamVolleyRequest request;


    public void onCreate(Bundle savedInstanceState) {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 强制为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explorer);

        try {
            gridview = (GridView) findViewById(R.id.explorer_grid_view);

            gridViewListItems = new ArrayList<HashMap<String, String>>();
            /*
            gridViewListItemAdapter = new SimpleAdapter(
                    this,
                    // 数据来源
                    gridViewListItems,
                    R.layout.explorer_item,
                    new String[]{"item_text"},
                    new int[]{R.id.item_text_view}
            );
            */

            gridViewListItemAdapter =new BaseAdapter() {

                @Override
                public int getCount() {
                    return gridViewListItems.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public int getItemViewType(int position) {
                    // TODO Auto-generated method stub
                    Map<String, String> item = gridViewListItems.get(position);
                    if (item.get("type").equals("GRID_HEADER")) {
                        return 1;
                    }
                    return 0;
                }

                @Override
                public int getViewTypeCount() {
                    return 2;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    if (getItemViewType(position) == 0) {
                        GridViewHeaderHolder gridViewHeaderHolder = null;

                        if (convertView != null) {
                            gridViewHeaderHolder = (GridViewHeaderHolder) convertView.getTag();
                        } else {
                            gridViewHeaderHolder = new GridViewHeaderHolder();

                            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                            convertView = inflater.inflate(R.layout.explorer_item, parent, false);

                            gridViewHeaderHolder.itemTextView = (TextView) convertView.findViewById(R.id.item_text_view);
                            convertView.setTag(gridViewHeaderHolder);
                        }

                        gridViewHeaderHolder.itemTextView.setText(gridViewListItems.get(position).get("item_text").toString());

                    } else {

                        GridViewItemHolder gridViewItemHolder = null;

                        if (convertView != null) {
                            gridViewItemHolder = (GridViewItemHolder) convertView.getTag();
                        } else {
                            gridViewItemHolder = new GridViewItemHolder();

                            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                            convertView = inflater.inflate(R.layout.explorer_item, parent, false);
                            convertView.setBackgroundResource(R.color.deepGray);

                            gridViewItemHolder.itemTextView = (TextView) convertView.findViewById(R.id.item_text_view);
                            convertView.setTag(gridViewItemHolder);
                        }

                        gridViewItemHolder.itemTextView.setText(gridViewListItems.get(position).get("item_text").toString());

                    }

                    return convertView;
                }
            };

            // 添加并且显示
            gridview.setAdapter(gridViewListItemAdapter);
            // 添加消息处理
            gridview.setOnItemClickListener(new ItemClickListener());

            // 添加表头
            addHeader();

            // 添加数据测试
            addData();
        } catch (Exception e) {
            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("提示").
                    setMessage(e.getMessage()).
                    setIcon(R.mipmap.ic_launcher).
                    create();
            alertDialog.show();
        }


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

            // String fileName = "fileDemo.txt";
            FileInputStream fis = openFileInput(fileName);
            byte[] readBytes = new byte[fis.available()];
            while (fis.read(readBytes) != -1) {

            }

            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("提示").
                    setMessage(new String(readBytes)).
                    setIcon(R.mipmap.ic_launcher).
                    create();
            alertDialog.show();
        } catch (Exception e) {

            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("提示").
                    setMessage(e.getLocalizedMessage()).
                    setIcon(R.mipmap.ic_launcher).
                    create();
            alertDialog.show();

            /*
            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("确定删除？").
                    setMessage("您确定删除该条信息吗？").
                    setIcon(R.mipmap.ic_launcher).
                    setPositiveButton(
                            "确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }
                    ).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    setNeutralButton("查看详情", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    create();
            alertDialog.show();
            */


            /*
            final String[] arrayFruit = new String[]{"苹果", "橘子", "草莓", "香蕉"};
            Dialog alertDialog = new AlertDialog
                    .Builder(this).
                    setTitle("你喜欢吃哪种水果？").
                    setIcon(R.mipmap.ic_launcher)
                    .setItems(
                            arrayFruit,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ExplorerActivity.this, arrayFruit[which], Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    .setNegativeButton(
                            "取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }
                    ).create();
            alertDialog.show();
            */


            /*
            // int selectedFruitIndex = 0;
            final String[] arrayFruit = new String[]{"苹果", "橘子", "草莓", "香蕉"};

            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("你喜欢吃哪种水果？")
                    .setIcon(R.mipmap.ic_launcher)
                    .setSingleChoiceItems(arrayFruit, 0, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedFruitIndex = which;
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Dialog_AlertDialogDemoActivity.this, arrayFruit[selectedFruitIndex], Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).create();
            alertDialog.show();
            */

            /*
            final String[] arrayFruit = new String[]{"苹果", "橘子", "草莓", "香蕉"};
            final boolean[] arrayFruitSelected = new boolean[]{true, true, false, false};

            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("你喜欢吃哪种水果？")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMultiChoiceItems(arrayFruit, arrayFruitSelected, new DialogInterface.OnMultiChoiceClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            arrayFruitSelected[which] = isChecked;
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < arrayFruitSelected.length; i++) {
                                if (arrayFruitSelected[i] == true) {
                                    stringBuilder.append(arrayFruit[i] + "、");
                                }
                            }
                            Toast.makeText(ExplorerActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).create();
            alertDialog.show();
            */


            /*
            // 取得自定义View
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View myLoginView = layoutInflater.inflate(R.layout.login, null);

            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("用户登录")
                    .setIcon(R.mipmap.ic_launcher)
                    .setView(myLoginView)
                    .setPositiveButton("登录", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).create();
            alertDialog.show();
            */
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

            try {
                /*
                HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);

                // 显示所选Item的ItemText
                Toast.makeText(getApplicationContext(), (String) item.get("item_text"), Toast.LENGTH_SHORT).show();
                */

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
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
                mRequestQueue.add(request);

            } catch (Exception e) {

                Snackbar.make(view, e.getLocalizedMessage() + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                /*
                Dialog alertDialog = new AlertDialog.Builder(getApplicationContext()).
                        setTitle("提示").
                        setMessage(e.getLocalizedMessage()).
                        setIcon(R.mipmap.ic_launcher).
                        create();
                alertDialog.show();
                */
            }
        }
    }

    public void addHeader() {
        String items[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        for (String item : items) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("item_text", item);
            map.put("type", "GRID_HEADER");
            gridViewListItems.add(map);
        }
        gridViewListItemAdapter.notifyDataSetChanged();
    }

    public void addData() {

        for (int i = 0; i < 3; i++) {
            String items[] = {"语文", "数学~~~~~~~~~~~~", "英语", "体育", "计算机", "化学~~~~~~~~~~~~"};
            for (String item : items) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("item_text", item);
                map.put("type", "GRID_ITEM");
                gridViewListItems.add(map);
            }
        }
        // 更新数据
        gridViewListItemAdapter.notifyDataSetChanged();
    }

    // 清空列表
    public void removeAll() {
        gridViewListItems.clear();
        gridViewListItemAdapter.notifyDataSetChanged();
    }

    static class GridViewHeaderHolder {
        TextView itemTextView;
    }

    static class GridViewItemHolder {
        TextView itemTextView;
    }
}
