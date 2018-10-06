package dataprocesing;


import java.io.*;

/**
 * Created by Gdj on 2017/10/30.
 * 把病程记录和出院小结的txt文件转为xml文件
 */

public class TxtToXml {

    private String strTxtFileName; // txt文件名

    private String strXmlFileName; // xml文件名

    public TxtToXml() {
        strTxtFileName = new String();
        strXmlFileName = new String();
    }

    // 病程记录的txt转为xml
    public void progressTxtToXml(String strTxt, String strXml) {
        strTxtFileName = strTxt;
        strXmlFileName = strXml;
        String strTmp; //临时存放
        try {

            BufferedReader inTxt = new BufferedReader(new FileReader(strTxtFileName));
            BufferedWriter outXml = new BufferedWriter(new FileWriter(strXmlFileName));

            outXml.write("<?xml version= \"1.0\" encoding=\"UTF-8\"?>");
            outXml.newLine();
            outXml.write("<progress>");
            String string = "";  //这里的string为txt文件内容文本
            while ((strTmp = inTxt.readLine()) != null){
                string = string + strTmp + "\n";
            }
            String arrTmp[] = new String[3];
            arrTmp[0] = string.split("病例特点：")[1].split("初步诊断")[0];
            arrTmp[1] = string.split("初步诊断：")[1].split("诊疗计划")[0];
            arrTmp[2] = string.split("诊疗计划：")[1];

            // 病例特点、初步诊断和诊疗计划后没有换行的进行换行。
            for (int i=0 ; i<2 ; i++){
                if (arrTmp[i].charAt(0) != '\n'){
                    arrTmp[i] = "\n" +arrTmp[i];
                }
            }

            outXml.newLine();
            outXml.write("<病例特点>" + arrTmp[0] + "</病例特点>");
            outXml.newLine();
            outXml.write("<初步诊断>" + arrTmp[1] + "</初步诊断>");
            outXml.newLine();
            outXml.write("<诊疗计划>" + arrTmp[2] + "</诊疗计划>");

            outXml.newLine();
            outXml.write("</progress>");
            outXml.flush();

            System.out.println(strXml + "写入成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 出院小结的txt转为xml
    public void dischargeTxtToXml(String strTxt, String strXml) {
        strTxtFileName = strTxt;
        strXmlFileName = strXml;
        String strTmp; //临时存放
        try {

            BufferedReader inTxt = new BufferedReader(new FileReader(strTxtFileName));
            BufferedWriter outXml = new BufferedWriter(new FileWriter(strXmlFileName));

            outXml.write("<?xml version= \"1.0\" encoding=\"UTF-8\"?>");
            outXml.newLine();
            outXml.write("<discharge>");
            String string = "";
            while ((strTmp = inTxt.readLine()) != null){
                string = string + strTmp + "\n";
            }
            String arrTmp[] = new String[6];

            arrTmp[0] = string.split("入院情况")[1].split("入院诊断")[0];
            arrTmp[1] = string.split("入院诊断")[1].split("诊疗经过")[0];
            arrTmp[2] = string.split("诊疗经过")[1].split("出院诊断")[0];
            arrTmp[3] = string.split("出院诊断")[1].split("出院情况")[0];
            arrTmp[4] = string.split("出院情况")[1].split("出院医嘱")[0];
            arrTmp[5] = string.split("出院医嘱")[1];

            outXml.newLine();
            outXml.write("<入院情况>" + arrTmp[0] + "</入院情况>");
            outXml.newLine();
            outXml.write("<入院诊断>" + arrTmp[1] + "</入院诊断>");
            outXml.newLine();
            outXml.write("<诊疗经过>" + arrTmp[2] + "</诊疗经过>");
            outXml.newLine();
            outXml.write("<出院诊断>" + arrTmp[3] + "</出院诊断>");
            outXml.newLine();
            outXml.write("<出院情况>" + arrTmp[4] + "</出院情况>");
            outXml.newLine();
            outXml.write("<出院医嘱>" + arrTmp[5] + "</出院医嘱>");

            outXml.newLine();
            outXml.write("</discharge>");
            outXml.flush();
            System.out.println(strXml + "写入成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param typename progress或者discharge，代表是病程记录还是出院小结
     */
    public static void progressXml(String typename ) {

        String filePath = "E:\\emrData\\newTxtData\\" + typename + "\\";    // txt文件目录

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0 ; i<fa.length ; i++){
            File fs = fa[i];
            String txtFileName = fs.getName();
            System.out.println(txtFileName);

            String txtName = filePath + txtFileName;

            // 转换后成功生成的xml文件
            String xmlName = "E:\\emrData\\newXmlData\\" + typename + "\\" + txtFileName.split(".txt")[0] + ".xml" ;

            TxtToXml thisClass = new TxtToXml();

            if (typename == "progress"){
                thisClass.progressTxtToXml(txtName, xmlName);
            }else if (typename == "discharge"){
                thisClass.dischargeTxtToXml(txtName, xmlName);
            }else {
                System.out.println("这不是病程记录或者出院小结！");
            }
        }
    }

    public static void main(String[] args) {

        // progressXml("progress");    // 对病程记录转为xml
         progressXml("discharge");          // 对出院小结转为xml

    }

}
