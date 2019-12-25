package com.letsup.habit.app.backend.service.impl;

import com.google.gson.Gson;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabFileService;
import com.letsup.habit.app.backend.util.HttpClientUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class HabFileServiceImpl implements HabFileService {

    @Value("${qn.accessKey}")
    private String accessKey;

    @Value("${qn.secretKey}")
    private String secretKey;

    @Value("${qn.pub.bucket1}")
    private String pubBucket1;

    @Value("${qn.pub.url.prefix}")
    private String prefix;

    @Override
    public String urlUpload(String httpUrl) throws GlobalException {
        StringBuilder stringBuilder = new StringBuilder(prefix);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = null;
        try {
            HttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpGet get = new HttpGet(httpUrl);
            HttpResponse httpResponse = httpClient.execute(get);
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                in = entity.getContent();

                //构造一个带指定Zone对象的配置类
                Configuration cfg = new Configuration(Zone.zone0());
                //...其他参数参考类注释
                UploadManager uploadManager = new UploadManager(cfg);
                //...生成上传凭证，然后准备上传
                //默认不指定key的情况下，以文件内容的hash值作为文件名
                String key = null;

                byte[] uploadBytes;
                byte[] buffer = new byte[1024 * 4];
                int n = 0;
                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
                uploadBytes = out.toByteArray();
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(pubBucket1);
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                stringBuilder = stringBuilder.append(putRet.key);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ApiResultConstants.ERROR, "文件系统繁忙");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return stringBuilder.toString();
    }

    @Override
    public String upload(MultipartFile file) throws GlobalException {
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StringBuilder stringBuilder = new StringBuilder(prefix);

        try {
            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.zone0());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = null;

            byte[] uploadBytes;
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            in = file.getInputStream();
            out = new ByteArrayOutputStream();
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            uploadBytes = out.toByteArray();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(pubBucket1);

            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            stringBuilder = stringBuilder.append(putRet.key);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ApiResultConstants.ERROR, "文件系统繁忙");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return stringBuilder.toString();

    }
}
