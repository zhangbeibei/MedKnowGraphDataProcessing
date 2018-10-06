package dataprocesing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Gdj on 2017/10/28.
 * 将原始html格式的电子病历去除冗余信息保存为txt文本
 */
public class HtmlToTxt {

    // 处理病程记录progress，对于病程记录只保留首次病程记录,保留分行，去掉多余空格和空行
    public static String retainFirstRecrod(String filePath){
        String text = "";
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/" );
            Elements elements = doc.select("p");
            for (int i=0; i<elements.size(); i++){
                Element element = elements.get(i);
                String body = element.text();
                text = text + body + "\n";
            }

            // 去掉html文件里的nbsp和其它格式下（unicode）的空格
            text = text.replaceAll("\u00A0", "");
            text = text.replaceAll("\u3000", "");

            // 只保留首次病程记录
            text = text.split("记录者")[0];
            text = text.split("签名：*")[0];
            text = text.split("首次病程记录")[1];
            if ( text.contains("其病例特点如下：") ){
                text = text.split("其病例特点如下：")[1];
                text = "病例特点:" + "\n" +text;
            }
            // 查房记录以“*”和空行分隔，这里是为了去掉查房记录
            text = text.split("[*]\n")[0];

            // 去掉首次病历中的鉴别诊断
            if ( text.contains("鉴别诊断") ){
                String text1 = text.split("鉴别诊断")[0];
                String text2 = "";
                if ( !text.contains("诊疗计划") ){
                    text2 = text.split("诊疗计划")[1];
                }else{
                    text2 = text.split("诊疗意见")[1];
                }
                text = text1 + "诊疗计划" + text2;
            }

            // 去除空行
            text = text.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
        }catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }

    // 处理出院小结discharge,保留分行，去掉多余空格和空行
    public static String handleDischargeRecrod(String filePath){
        String text = "";
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/" );
            Elements elements = doc.select("p");
            for (int i=0; i<elements.size(); i++){
                Element element = elements.get(i);
                String body = element.text();
                text = text + body + "\n";
            }

            // 去掉html文件里的nbsp和其它格式下（unicode）的空格
            text = text.replaceAll("\u00A0", "");
            text = text.replaceAll("\u3000", "");

            String text1 = text.split("入院情况")[1].split("主治医师")[0].split("主任医师：")[0];
            text = "入院情况" + "\n" + text1;

            // 去除空行
            text = text.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
        }catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }

    // 创建文件
    public static boolean createFile(String filePath)throws Exception{
        boolean flag=false;
        File file = new File(filePath);
        try{
            if(!file.exists()){
                file.createNewFile();
                flag=true;
                System.out.println(filePath + "创建成功");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    // 往txt文件写入内容
    public static boolean writeTxtFile(String content,String filePath)throws Exception{
        File file = new File(filePath);
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(file);
            o.write(content.getBytes("utf-8"));
            o.close();
            flag=true;
            System.out.println(filePath + "写入成功");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }

    // 把病程记录的html转为txt
    public static void progressTxt(){
        String filePath = "E:\\emrData\\readyAnnotatedData\\progress\\";    // 病程记录文件目录
//        String filePath = "E:\\emrData\\readyAnnotatedData\\discharge\\";  // 出院小结文件目录

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();

        for (int i=0; i<fa.length; i++){
            File fs = fa[i];
            String filename = fs.getName();
            System.out.println(filename);
            String content = retainFirstRecrod(filePath + filename);
//            String content = handleDischargeRecrod(filePath + filename);

            String filename1 = filename.split(".html")[0];
            String newtxtFilePath = "E:\\emrData\\newTxtData\\progress\\" + filename1 + ".txt";
//            String newtxtFilePath = "E:\\emrData\\newTxtData\\discharge\\" + filename1 + ".txt";

            try{
                createFile(newtxtFilePath);
                writeTxtFile(content, newtxtFilePath);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args){
        progressTxt();
    }
}
