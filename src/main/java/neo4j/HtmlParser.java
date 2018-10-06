package neo4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gdj on 2017/7/30.
 * 用于从url链接里爬取数据
 */

public class HtmlParser {

    public List<List<String>> lists = new ArrayList<List<String>>();


    public List<List<String>> getData(String diseaseName){

        try {
            String url = "http://jbk.39.net/" + diseaseName + "/";
            String[] strings = {"zztz","jcjb","yyzl"};  //典型症状、临床检查、治疗方法

            //疾病名称disease
            Document document =  Jsoup.connect(url).get();
            Elements disease = document.select("h1");
//            System.out.println(disease.eachText());

            //症状symptom
            Document sDoucument = Jsoup.connect(url+"zztz").get();
            Elements symptom = sDoucument.getElementsByClass("links").get(0).getElementsByTag("a");
//            System.out.println(symptom.eachText());

            //临床检查examine
            Document eDoucument = Jsoup.connect(url+"jcjb").get();
            Elements examine = eDoucument.getElementsByClass("checkbox-data").get(0).getElementsByTag("a");
//            System.out.println(examine.eachText());

            //治疗方法treat
            Document tDoucument = Jsoup.connect(url+"yyzl").get();
            Elements treat = tDoucument.getElementsByClass("info").get(0).getElementsByClass("blue");
//            System.out.println(treat.eachText());

            //药物drug
            Elements drug = document.getElementsByClass("drug").get(0).getElementsByAttribute("title");
//            System.out.println(drug.eachText());

            lists.add(disease.eachText());
            lists.add(symptom.eachText());
            lists.add(examine.eachText());
            ArrayList<String> treatList = new ArrayList<String>(Arrays.asList(treat.text().split("、")));
            lists.add(treatList);
            lists.add(drug.eachText());

        }catch (IOException e){

        }
        return lists;
    }

//    public static void main(String[] args) {
//        getData("scsjt");
//        getData("xejxlwy");
//        getData("zdml");
//    }
}
