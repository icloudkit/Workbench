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
package cn.com.teamlink.workbench.utils;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * VolleyUtil.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016年12月27日 上午11:38:34
 */
public class VolleyUtil {

    public static void download(String url) {

    }

//    /**
//     * 下载文件
//     * @param  url
//     * @param  destFileName   xxx.jpg/xxx.png/xxx.txt
//     * @throws  ClientProtocolException
//     * @throws IOException
//     */
//    public static void getFile(String url, String destFileName) throws ClientProtocolException, IOException {
//        // 生成一个httpclient对象
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpget =newHttpGet(url);
//        HttpResponse response = httpclient.execute(httpget);
//        HttpEntity entity = response.getEntity();
//        InputStream in = entity.getContent();
//        File file =newFile(destFileName);
//        try{
//            FileOutputStream fout =newFileOutputStream(file);
//            intl = -1;
//            byte[] tmp =newbyte[1024];
//            while((l = in.read(tmp)) != -1) {
//                fout.write(tmp,0, l);
//                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
//            }
//            fout.flush();
//            fout.close();
//        }finally{
//            // 关闭低层流。
//            in.close();
//        }
//        httpclient.close();
//    }


//    @Override
//    public void onResponse(byte[] response) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        try {
//            if (response != null) {
//
//                //Read file name from headers (We have configured API to send file name in "Content-Disposition" header in following format: "File-Name:File-Format" example "MyDoc:pdf"
//
//                String content = request.responseHeaders.get("Content-Disposition")
//                        .toString();
//                StringTokenizer st = new StringTokenizer(content, "=");
//                String[] arrTag = st.toArray();
//
//                String filename = arrTag[1];
//                filename = filename.replace(":", ".");
//                Log.d("DEBUG::FILE NAME", filename);
//
//                try {
//                    long lenghtOfFile = response.length;
//
//                    //covert reponse to input stream
//                    InputStream input = new ByteArrayInputStream(response);
//
//                    //Create a file on desired path and write stream data to it
//                    File path = Environment.getExternalStorageDirectory();
//                    File file = new File(path, filename);
//                    map.put("resume_path", file.toString());
//                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
//                    byte data[] = new byte[1024];
//
//                    long total = 0;
//
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        output.write(data, 0, count);
//                    }
//
//                    output.flush();
//
//                    output.close();
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
//            e.printStackTrace();
//        }
//    }
}
