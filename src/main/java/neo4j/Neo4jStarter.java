package neo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gdj on 2017/7/16.
 */
public class Neo4jStarter {

    HtmlParser parser = new HtmlParser();

    Neo4jService neo4jService = new Neo4jSeviceImpl();

    public String diseaseName = parser.getData("scsjt").get(0).get(0);


    public static void main(String[] args){

        Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j","Neo4j"));
        Neo4jStarter s = new Neo4jStarter();
//        s.createDiseaseNode(driver);
//        s.createSymptomNode(driver);
//        s.createExamineNode(driver);
//        s.createTreatNode(driver);
//        s.createDrugNode(driver);
        s.createRelation(driver);

//        s.deleteNode(driver);
//        s.getAllNode(driver);
//        s.delteRelation(driver);
//        s.searchAllNodeRelation(driver);
//        s.searchBetweenNodeRelation(driver);
//        s.searchOtherRelation(driver);
    }

    /**
     * 创建疾病名称实体节点
     */
    public void createDiseaseNode(Driver driver){

        List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> vallist = new ArrayList<String>();

        vallist.add(diseaseName);
        params.put("name",vallist);
        list.add(params);
        neo4jService.createDiseaseNode(driver,list);
    }

    /**
     * 创建症状实体节点
     */
    public void createSymptomNode(Driver driver){

        List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> vallist = new ArrayList<String>();
        List<String> disease = new ArrayList<String>();

        vallist.add(diseaseName);
        vallist.addAll(parser.getData("scsjt").get(1));

        disease.add(vallist.get(0));
        vallist.remove(0);

        params.put("disease", disease);
        params.put("name",vallist);

        list.add(params);
        neo4jService.createSymptomNode(driver,list);
    }

    /**
     * 创建临床检查实体节点
     */
    public void createExamineNode(Driver driver){

        List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> vallist = new ArrayList<String>();
        List<String> disease = new ArrayList<String>();

        vallist.add(diseaseName);
        vallist.addAll(parser.getData("scsjt").get(2));

        disease.add(vallist.get(0));
        vallist.remove(0);

        params.put("disease", disease);
        params.put("name",vallist);

        list.add(params);
        neo4jService.createExamineNode(driver,list);
    }

    /**
     * 创建治疗方法实体节点
     */
    public void createTreatNode(Driver driver){

        List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> vallist = new ArrayList<String>();
        List<String> disease = new ArrayList<String>();

        vallist.add(diseaseName);
        vallist.addAll(parser.getData("scsjt").get(3));

        disease.add(vallist.get(0));
        vallist.remove(0);

        params.put("disease", disease);
        params.put("name",vallist);

        list.add(params);
        neo4jService.createTreatNode(driver,list);
    }

    /**
     * 创建药物实体节点
     */
    public void createDrugNode(Driver driver){

        List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> vallist = new ArrayList<String>();
        List<String> disease = new ArrayList<String>();

        vallist.add(diseaseName);
        vallist.addAll(parser.getData("scsjt").get(4));

        disease.add(vallist.get(0));
        vallist.remove(0);

        params.put("disease", disease);
        params.put("name",vallist);

        list.add(params);
        neo4jService.createDrugNode(driver,list);
    }

    public void createRelation(Driver driver){
        neo4jService.createNodeRelation(driver);
    }

    public void deleteNode(Driver driver){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "uzi2");
        neo4jService.deleteNode(driver, params);
    }

    public void getAllNode(Driver driver){
        neo4jService.getAllNode(driver);
    }



    public void delteRelation(Driver driver){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nameA", "Christian");
        params.put("nameB", "Dava");
        neo4jService.deleteNodeRelation(driver, params);
    }

    public void searchAllNodeRelation(Driver driver){
        neo4jService.searchAllNodeRelation(driver, "relName");
    }

    public void searchBetweenNodeRelation(Driver driver){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nameA", "Christian");
        params.put("nameB", "Dava");
        neo4jService.searchBetweenNodeRelation(driver,params);
    }

    public void searchOtherRelation(Driver driver){
        String node = "Aaron";
        neo4jService.searchOtherRelation(driver, node);
    }

}
