package dataprocesing;

import java.io.File;

/**
 * Created by Gdj on 2018/1/9.
 */
public class CountEntRel {

    // 统计四种实体的个数
    public static void countEnt(String filePath){

        int diseaseNumber = 0;
        int symptomNumber = 0;
        int testNumber = 0;
        int treatmentNumber = 0;

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++) {
            File fs = fa[i];
            String filename = fs.getName();

            // 读取文件内容
            String content = TxtPreprocess.txt2String(fs);

            String[] lines = content.split("\n");
            for (int j = 0; j<lines.length; j++){
                if (lines[j].contains("disease")){
                    diseaseNumber ++;
                }
                if (lines[j].contains("symptom")){
                    symptomNumber ++;
                }
                if (lines[j].contains("test")){
                    testNumber ++;
                }if (lines[j].contains("treatment")){
                    treatmentNumber ++;
                }
            }
        }

        int EntityTotal = diseaseNumber + symptomNumber + testNumber + treatmentNumber;

        System.out.println("diseaseNumber:" + diseaseNumber);
        System.out.println("symptomNumber:" + symptomNumber);
        System.out.println("testNumber:" + testNumber);
        System.out.println("treatmentNumber:" + treatmentNumber);

        System.out.println("EntityTotal:" + EntityTotal);

    }

    // 统计实体修饰的个数
    public static void countAssertion(String filePath){

        int absentNumber = 0;
        int familyNumber = 0;
        int presentNumber = 0;
        int conditionalNumber = 0;
        int possibleNumber = 0;
        int hypotheticalNumber = 0;
        int occasionalNumber = 0;

        int treatmentHistoryNumber = 0;
        int treatmentAbsentNumber = 0;
        int treatmentPresentNumber = 0;

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++) {
            File fs = fa[i];
            String filename = fs.getName();

            // 读取文件内容
            String content = TxtPreprocess.txt2String(fs);

            String[] lines = content.split("\n");
            for (int j = 0; j<lines.length; j++){
                if (lines[j].contains("treatment") && lines[j].contains("history")){
                    treatmentHistoryNumber ++;
                }
                if (lines[j].contains("treatment") && lines[j].contains("absent")){
                    treatmentAbsentNumber ++;
                }
                if (lines[j].contains("treatment") && lines[j].contains("present")){
                    treatmentPresentNumber ++;
                }
                if (lines[j].contains("present")){
                    presentNumber ++;
                }
                if (lines[j].contains("absent")){
                    absentNumber ++;
                }
                if (lines[j].contains("family")){
                    familyNumber ++;
                }
                if (lines[j].contains("conditional")){
                    conditionalNumber ++;
                }
                if (lines[j].contains("possible")){
                    possibleNumber ++;
                }
                if (lines[j].contains("hypothetical")){
                    hypotheticalNumber ++;
                }
                if (lines[j].contains("occasional")){
                    occasionalNumber ++;
                }
            }
        }

        int AssertionTotal = absentNumber + presentNumber + familyNumber +
                conditionalNumber + possibleNumber + hypotheticalNumber + occasionalNumber + treatmentHistoryNumber;

        System.out.println("absentNumber:" + absentNumber);
        System.out.println("presentNumber:" + presentNumber);
        System.out.println("familyNumber:" + familyNumber);
        System.out.println("conditionalNumber:" + conditionalNumber);
        System.out.println("possibleNumber:" + possibleNumber);
        System.out.println("hypotheticalNumber:" + hypotheticalNumber);
        System.out.println("occasionalNumber:" + occasionalNumber);
        System.out.println("historyNumber:" + treatmentHistoryNumber);

        System.out.println("AssertionTotal:" + AssertionTotal);
    }


    // 统计实体关系的个数
    public static void countRel(String filePath){

        int TrIDNumber = 0;
        int TrWDNumber = 0;
        int TrCDNumber = 0;
        int TrADNumber = 0;
        int TrNADNumber = 0;
        int TrISNumber = 0;
        int TrWSNumber = 0;
        int TrCSNumber = 0;
        int TrASNumber = 0;
        int TrNASNumber = 0;
        int TeRDNumber = 0;
        int TeCDNumber = 0;
        int TeRSNumber = 0;
        int TeASNumber = 0;
        int DCSNumber = 0;
        int SIDNumber = 0;

        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(filePath + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i=0; i<fa.length; i++) {
            File fs = fa[i];
            String filename = fs.getName();

            // 读取文件内容
            String content = TxtPreprocess.txt2String(fs);

            String[] lines = content.split("\n");
            for (int j = 0; j<lines.length; j++){
                if (lines[j].contains("TrID")){
                    TrIDNumber ++;
                }
                if (lines[j].contains("TrWD")){
                    TrWDNumber ++;
                }
                if (lines[j].contains("TrCD")){
                    TrCDNumber ++;
                }
                if (lines[j].contains("TrAD")){
                    TrADNumber ++;
                }
                if (lines[j].contains("TrNAD")){
                    TrNADNumber ++;
                }
                if (lines[j].contains("TrIS")){
                    TrISNumber ++;
                }
                if (lines[j].contains("TrWS")){
                    TrWSNumber ++;
                }
                if (lines[j].contains("TrCS")){
                    TrCSNumber ++;
                }
                if (lines[j].contains("TrAS")){
                    TrASNumber ++;
                }
                if (lines[j].contains("TrNAS")){
                    TrNASNumber ++;
                }
                if (lines[j].contains("TeRD")){
                    TeRDNumber ++;
                }
                if (lines[j].contains("TeCD")){
                    TeCDNumber ++;
                }
                if (lines[j].contains("TeRS")){
                    TeRSNumber ++;
                }
                if (lines[j].contains("TeAS")){
                    TeASNumber ++;
                }
                if (lines[j].contains("DCS")){
                    DCSNumber ++;
                }
                if (lines[j].contains("SID")){
                    SIDNumber ++;
                }
            }
        }

        int RelationTotal = TrIDNumber + TrWDNumber + TrCDNumber + TrADNumber + TrNADNumber +
                TrISNumber +TrWSNumber +TrCSNumber + TrASNumber + TrNASNumber +
                TeRDNumber + TeCDNumber + TeRSNumber + TeASNumber + DCSNumber + SIDNumber;

        System.out.println("TrIDNumber:" + TrIDNumber);
        System.out.println("TrWDNumber:" + TrWDNumber);
        System.out.println("TrCDNumber:" + TrCDNumber);
        System.out.println("TrADNumber:" + TrADNumber);
        System.out.println("TrNADNumber:" + TrNADNumber);
        System.out.println("TrISNumber:" + TrISNumber);
        System.out.println("TrWSNumber:" + TrWSNumber);
        System.out.println("TrCSNumber:" + TrCSNumber);
        System.out.println("TrASNumber:" + TrASNumber);
        System.out.println("TrNASNumber:" + TrNASNumber);
        System.out.println("TeRDNumber:" + TeRDNumber);
        System.out.println("TeCDNumber:" + TeCDNumber);
        System.out.println("TeRSNumber:" + TeRSNumber);
        System.out.println("TeASNumber:" + TeASNumber);
        System.out.println("DCSNumber:" + DCSNumber);
        System.out.println("SIDNumber:" + SIDNumber);

        System.out.println("RelationTotal:" + RelationTotal);
    }


    public static void main(String[] args){
        String dischargeEntPath = "E:\\emrData\\xmlData\\dischargeEnt\\";    // 出院小结实体文件目录
        System.out.println("出院小结实体统计");
        countEnt(dischargeEntPath);
        System.out.println("出院小结实体修饰统计");
        countAssertion(dischargeEntPath);

        String dischargeRelPath = "E:\\emrData\\xmlData\\dischargeRel\\";    // 出院小结实体关系文件目录
        System.out.println("出院小结实体关系统计");
        countRel(dischargeRelPath);

        String progressEntPath = "E:\\emrData\\xmlData\\progressEnt\\";    // 病程记录实体文件目录
        System.out.println("病程记录实体统计");
        countEnt(progressEntPath);
        System.out.println("病程记录实体修饰统计");
        countAssertion(progressEntPath);

        String progressRelPath = "E:\\emrData\\xmlData\\progressRel\\";    // 出院小结实体关系文件目录
        System.out.println("病程记录实体关系统计");
        countRel(progressRelPath);
    }

}
