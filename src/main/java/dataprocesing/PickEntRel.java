package dataprocesing;

import java.io.File;

/**
 * Created by Gdj on 2018/1/9.
 */
public class PickEntRel {

    public static void main(String[] args){

//        String filePath = "E:\\emrData\\xmlData\\discharge\\";    // 出院小结文件目录
//        String endPath = "E:\\emrData\\xmlData\\dischargeEnt\\";

        String filePath = "E:\\emrData\\xmlData\\progress\\";    // 病程记录文件目录
        String endPath = "E:\\emrData\\xmlData\\progressEnt\\";

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++) {
            File fs = fa[i];
            String filename = fs.getName();
            System.out.println(filename);

            if (filename.contains(".ent")){
                System.out.println("1111111");
                ChangeFileFolders.moveFileFolders(filePath,filename, endPath);
            }

        }
    }


}
