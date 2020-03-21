package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import config.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Utils {
    public static void uploadImage() {
        Configuration configuration = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(configuration);

        String accessKey = getConfiguration("qiniuAccessKey");
        String secretKey = getConfiguration("qiniuSecretKey");
        String bucket = getConfiguration("qiniuBucket");

        log.info("accessKey:{},sceretKey:{},bucket:{}",accessKey, secretKey, bucket);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = format.format(date);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, key);
        Response response;

        {
            try {
                response = uploadManager.put(Common.USER_HOME_DIR + Common.TEMP_FILE_PATH + Common.TEMP_IMAGE_NAME_PNG,
                        key, upToken);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getConfiguration(String key){
        File config = new File(Common.USER_HOME_DIR+Common.TEMP_FILE_PATH+"config.json");
        try {
            String configContent = FileUtils.readFileToString(config);
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(configContent);
            return element.getAsJsonObject().get(key).getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
