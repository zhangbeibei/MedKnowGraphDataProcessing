package dataprocesing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * Created by Gdj on 2017/10/26.
 * 把原本按月份分类的电子病历html文件按照科室分类
 */
public class ChangeFileFolders {

    // 移动一个文件夹里的文件到另一个文件夹
    public static void moveFileFolders(String startPath, String fileName, String endPath){
        try {
            File startFile = new File(startPath + fileName);
            File tmpFile = new File(endPath);  // 获取文件夹路径
            // 判断文件夹是否创建，没有创建则创建新文件夹
            if(!tmpFile.exists()){
                tmpFile.mkdirs();
            }
            if (startFile.renameTo(new File(endPath + fileName))){
                System.out.println(fileName + " is moved successful!");
            }else {
                System.out.println(fileName + " is failed to move!");
            }
        }catch (Exception e){
            System.out.println(fileName + " is not legal!");
            e.printStackTrace();
        }
    }

    //  得到一个html文件中的科室名称
    public static String getDepartment(String path, String filename){
        String departmentName = "";
        try {
            File input = new File(path + filename);
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/" );

            // Element element = doc.getElementsByClass("td1").get(1);

            // 产科出院小结的格式和其它的不一样
            if (doc.select("p").eachText().get(6).endsWith("结")){
                return "产科";
            }

            Element element = null;
            if (doc.select("table").size() == 8){
                element = doc.select("table").get(4);
            }else {
                element = doc.select("table").get(5);
            }
            departmentName = element.text().split("科室")[1].split("病区")[0].trim();

            // 不同文件里“科室”后面的冒号不一致
            if (departmentName.charAt(0) == '：'){
                // 去掉第一个字符是中文格式下的"："
                departmentName = departmentName.split("：")[1];
            }else if (departmentName.charAt(0) == ':'){
                // 去掉第一个字符是英文格式下的":"
                departmentName = departmentName.split(":")[1];
            }
            //  System.out.println(departmentName);
        }catch (IOException e){
            e.printStackTrace();
        }
        return departmentName;
    }

    // 开始移动
    public static void move(){
        String startPath = "E:\\2015\\12\\";  //移动之前文件路径
        File file = new File(startPath);
        if (!file.exists()){
            System.out.println(startPath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++){
            File fs = fa[i];
            String filename = fs.getName();
            System.out.println(filename);
            String departmentName = getDepartment(startPath, filename);
            System.out.println(departmentName);
            String endPath = "E:\\emrData\\cyxj\\" + departmentName +"\\";
            System.out.println(endPath);
            moveFileFolders(startPath, filename, endPath);
        }
    }

    public static void main(String[] args){
        move();
    }
}
