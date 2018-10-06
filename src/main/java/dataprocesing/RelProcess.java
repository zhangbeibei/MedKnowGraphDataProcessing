package dataprocesing;

import java.io.*;

/**
 * Created by Gdj on 2017/12/3.
 * 修改关系文件.xml.ent.rel的格式
 * 把实体组之间的关系转化为实体与实体之间的关系
 */
public class RelProcess {


    // 出院小结的关系文件处理
    public static void relProcess(String filePath){

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

            // 读取文件内容
            String content = TxtPreprocess.txt2String(fs);
            System.out.println(content);
            String newContent = entityGroup2entity(content);
            System.out.println(newContent);

            // 写入文件内容
            try{
                HtmlToTxt.writeTxtFile(newContent, filePath + filename);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    // 对内容直接进行修改，把实体组之间关系转化为实体之间关系
    public static String entityGroup2entity(String content){
        String newContent = "";

        String[] strings = content.split("\n");

        for (int s = 0; s<strings.length; s++) {
            String string = strings[s];
            String string1 = string.split("[;][}][||]")[0].split("E[=][{]")[1];
            String string2 = string.split("[|][|]")[1];
            String string3 = string.split("[||]E=[{]")[1].split("[;][}]")[0];

            String[] entity1 = string1.split("[;]");
            String[] entity2 = string3.split("[;]");

            String[] sentence = new String[entity1.length * entity2.length];

            int count = 0;
            for (int w = 0; w < entity1.length; w++) {
                for (int j = 0; j < entity2.length; j++) {
                    sentence[count] = "C=" + entity1[w] + "||" + string2 + "||" + "C=" + entity2[j];
                    count++;
                }
            }

            for (int k = 0; k < sentence.length; k++) {
                newContent += sentence[k] + "\n";
            }
            newContent = newContent.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
        }
        return newContent;
    }

    public static void main(String[] args){

        // String filePath = "E:\\emrData\\xmlData\\dischargeRel\\";    // 出院小结关系文件目录
        String filePath = "E:\\emrData\\xmlData\\progressRel\\";    // 病程记录关系文件目录

        relProcess(filePath);
    }
}
