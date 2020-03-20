package uploadimage;

import config.Common;
import ui.MainUI;

import java.io.File;

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
        new MainUI("123");
    }
}