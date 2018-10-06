package dataprocesing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Gdj on 2017/10/30.
 *
 */
public class TxtPreprocess {

    // 读取文件内容
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){  //使用readLine方法，一次读一行
                result.append(s);
                result.append("\n");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    // 对病程记录进行数据清洗，去掉诊断及依据和鉴别诊断，最后只剩下病程特点（去掉查体检查等部分）、初步诊断和诊疗计划
    public static void progressProcess(){
        String filePath = "E:\\emrData\\newTxtData\\progress\\";    // 病程记录文件目录
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();

        for (int i=0; i<fa.length; i++){
            File fs = fa[i];
            String filename = fs.getName();
            String content = txt2String(fs);
            content = content.replaceAll(":", "：");
            content = content.replaceAll("病历特点", "病例特点");
            content = content.replaceAll("病情特点", "病例特点");
            content = content.replaceAll("根据上述病史特点初步诊断为：","初步诊断：");
            content = content.replaceAll("目前诊断：","初步诊断：");
            content = content.replaceAll("\n诊断：","\n初步诊断：");
            content = content.replaceAll("目前诊断及依据：","诊断及依据：");
            content = content.replaceAll("诊断依据：","诊断及依据：");
            content = content.replaceAll("病例特点\n","病例特点：\n");

            // 第一行没有病例特点四个字的加上“病例特点：”
            if ( !content.contains("病例特点") ){
                content = "病例特点：" + content;
            }

            // 没有诊疗计划的在最后加上空的诊疗计划
            if ( !content.contains("诊疗计划：") ){
                content = content + "诊疗计划：" ;
            }

            // 有诊断及依据的去掉诊断依据
            if ( content.contains("诊断及依据：") ) {
                String s1 = content.split("诊断及依据")[0];
                String s2 = content.split("诊疗计划：")[1];
                content = s1 + "诊疗计划：" + "\n" + s2;
            }

            // 没有初步诊断的加上空的初步诊断
            if ( !content.contains("初步诊断：") ){
                String content1 = content.split("诊疗计划：")[0];
                String content2 = content.split("诊疗计划：")[1];
                content = content1 + "初步诊断：" + "\n" + "诊疗计划：" + content2;
            }

            // 去掉病例特点中的查体部分
            content = deleteSection(content, "查体：");
            content = deleteSection(content, "体检：");
            content = deleteSection(content, "体格检查：");

            // 去掉空行
            content = content.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
            // System.out.println(content);
            try {
                HtmlToTxt.writeTxtFile(content, filePath + filename);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    // 去掉content中的一段string
    public static String deleteSection(String content, String string){
        if ( content.contains(string) ){
            int index = content.indexOf(string);
            int sign1 = index - 6 ;
            int sign2 = index;
            while (content.charAt(sign1) != '\n'){
                sign1++;
            }
            while (content.charAt(sign2) != '\n'){
                sign2++;
            }

            // 如果查体或者体格检查几个字前面没有分行的话。
            if (sign1 == sign2){
                sign1 = content.indexOf(string);
            }
            String substring = content.substring(sign1, sign2);
            int begin = content.indexOf(substring);
            content = content.substring(0, begin) + content.substring(begin + substring.length());
        }
        return content;
    }

    // 对出院小结进行数据清洗，剩下入院情况、入院诊断、诊疗经过、出院诊断、出院情况、出院医嘱
    public static void dischargeProcess(){
        String filePath = "E:\\emrData\\newTxtData\\discharge\\";    // 出院小结文件目录
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++){
            File fs = fa[i];
            String filename = fs.getName();
            String content = txt2String(fs);

            // 去掉主要化验及特殊检查
            if ( content.contains("主要化验及特殊检查") ){
                String s1 = content.split("主要化验及特殊检查")[0];
                String s2 = content.split("诊疗经过")[1];
                content = s1 + "诊疗经过" + "\n" + s2;
            }
            if ( content.contains("主要化验及") ){
                String s1 = content.split("主要化验及")[0];
                String s2 = content.split("诊疗经过")[1];
                content = s1 + "诊疗经过" + "\n" + s2;
            }

            // 去掉病理结果和治疗结果，死亡就没有出院
            if ( !content.contains("死亡原因") ){
                if ( content.contains("病理结果") ){
                    String content1 = content.split("病理结果")[0];
                    String content2 = content.split("出院诊断")[1];
                    content = content1 + "出院诊断" + "\n" + content2;
                }
                if ( content.contains("治疗结果") ){
                    content = content.split("治疗结果")[0];
                }
            }

            if ( !content.contains("出院诊断") ) content = content + "出院诊断" + "\n";
            if ( !content.contains("出院情况") ) content = content + "出院情况" + "\n";
            if ( !content.contains("出院医嘱") ) content = content + "出院医嘱" + "\n";

            // 去掉空行
            content = content.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
            // System.out.println(content);
            try {
                HtmlToTxt.writeTxtFile(content, filePath + filename);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    /**
     *
     * @param typename 表示是病程记录还是出院小结
     * 对病程记录和出院小结：把英文下的“,”、“;”换成中文下的“，”、“；”，按照“。”和“；”进行分行
     *
     */
    public static void divideLine(String typename){
        String filePath = "E:\\emrData\\newTxtData\\" + typename + "\\";    // 病程记录文件目录
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++) {
            File fs = fa[i];
            String filename = fs.getName();
            String content = txt2String(fs);

            content = content.replaceAll(";", "；");
            content = content.replaceAll(",", "，");
            content = content.replaceAll("。", "。\n");

            // 暂时不对"；"做换行处理
            // content = content.replaceAll("；", "；\n");

            content = content.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");

            content = content.replaceAll("检查日期；", "检查日期：");
            content = content.replaceAll("项目；", "项目：");
            content = content.replaceAll("结果；", "结果：");
            content = content.replaceAll("检查单位；", "检查单位：");

            try {
                HtmlToTxt.writeTxtFile(content, filePath + filename);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args){
//         progressProcess();
//        dischargeProcess();
        divideLine("discharge");
    }
}
