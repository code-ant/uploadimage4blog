package uploadimage;

import config.Common;
import lombok.extern.slf4j.Slf4j;
import ui.MainUI;

import java.io.File;

@Slf4j
public class HomePage{
    public static void main(String[] args) {
        //TODO check configuration
        File file = new File(Common.USER_HOME_DIR+Common.TEMP_FILE_PATH);
        if(!file.exists()){
//            if(file.isDirectory()){
                System.out.println(22222);
                file.mkdirs();
//            }
        }
        log.info("init MainUI");
        new MainUI("123");
    }
}