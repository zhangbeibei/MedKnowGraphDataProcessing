package neo4j;

import neo4j.Neo4jService;
import org.neo4j.driver.v1.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gdj on 2017/7/16.
 */
public class Neo4jSeviceImpl implements Neo4jService {


    // 创建疾病名称节点
    public void createDiseaseNode(Driver driver, List<Map<String, List<String>>> list){
        Session session = driver.session();
        List<String> valList = list.get(0).get("name");
        Map<String, Object> params = new HashMap<String, Object>();
        for (String val:valList){
            params.put("name", val);
            session.run("CREATE (n:Disease{ name:{name} })", params);
        }
        session.close();
        driver.close();
    }

    // 创建症状节点
    public void createSymptomNode(Driver driver, List<Map<String, List<String>>> list){
        Session session = driver.session();
        List<String> valList = list.get(0).get("name");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("disease", list.get(0).get("disease"));

        for (String val:valList){
            params.put("name", val);
            session.run("CREATE (n:Symptom{ name:{name}, disease:{disease} })", params);
        }
        session.close();
        driver.close();
    }


    // 创建临床检查节点
    public void createExamineNode(Driver driver, List<Map<String, List<String>>> list){
        Session session = driver.session();
        List<String> valList = list.get(0).get("name");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("disease", list.get(0).get("disease"));

        for (String val:valList){
            params.put("name", val);
            session.run("CREATE (n:Examine{ name:{name}, disease:{disease} })", params);
        }
        session.close();
        driver.close();
    }

    // 创建临治疗方法节点
    public void createTreatNode(Driver driver, List<Map<String, List<String>>> list){
        Session session = driver.session();
        List<String> valList = list.get(0).get("name");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("disease", list.get(0).get("disease"));

        for (String val:valList){
            params.put("name", val);
            session.run("CREATE (n:Treat{ name:{name}, disease:{disease} })", params);
        }
        session.close();
        driver.close();
    }

    // 创建药物节点
    public void createDrugNode(Driver driver, List<Map<String, List<String>>> list){
        Session session = driver.session();
        List<String> valList = list.get(0).get("name");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("disease", list.get(0).get("disease"));

        for (String val:valList){
            params.put("name", val);
            session.run("CREATE (n:Drug{ name:{name}, disease:{disease} })", params);
        }
        session.close();
        driver.close();
    }



    // 删除节点
    public void deleteNode(Driver driver, Map<String, Object> params){
        Session session = driver.session();
        session.run("MATCH (n:Student{ name:{name} }) DELETE n", params);
        session.close();
        driver.close();
    }

    // 得到所有节点
    public void getAllNode(Driver driver){
        Session session = driver.session();
        StatementResult result = session.run("START n=node(*) RETURN n.name AS name");
        while (result.hasNext()){
            Record record = result.next();
            System.out.println(record.get("name").asString());
        }
        session.close();
        driver.close();
    }

    // 创建节点关系
    public void createNodeRelation(Driver driver){
        Session session = driver.session();
//        session.run("MATCH (a:Disease),(b:Symptom) WHERE a.name=b.disease CREATE (a)-[r:Symptom]->(b)");
//        session.run("MATCH (a:Disease),(b:Examine) WHERE a.name=b.disease CREATE (a)-[r:Examine]->(b)");
        session.run("MATCH (a:Disease),(b:Treat) WHERE a.name=b.disease CREATE (a)-[r:Treat]->(b)");
//        session.run("MATCH (a:Disease),(b:Drug) WHERE a.name=b.disease CREATE (a)-[r:Drug]->(b)");
        session.close();
        driver.close();

    }

    // 删除节点关系
    public void deleteNodeRelation(Driver driver, Map<String, Object> params){
        Session session = driver.session();
        session.run("MATCH (a:Student { name:{nameA} })-[r:relName]->(b:Student { name:{nameB} }) DELETE r",params);
        session.close();
        driver.close();
    }

    // 查询所有节点关系
    public void searchAllNodeRelation(Driver driver, String relabel){
        Session session = driver.session();
        StatementResult result = session.run("MERGE (m)-[r:"+relabel+"]->(n) RETURN r.relAttr AS relValue");
        while (result.hasNext()){
            Record record = result.next();
            System.out.println(record.get("relValue").asString());
        }
        session.close();
        driver.close();
    }

    // 查询两个节点之间的关系
    public void searchBetweenNodeRelation(Driver driver, Map<String, Object> params){
        Session session = driver.session();
        StatementResult result = session.run("MERGE (n:Student { name:{nameA} })-[r:relName]->(m:Student { name:{nameB} }) RETURN r.relAttr AS relValue",params);
        while(result.hasNext()){
            Record record = result.next();
            System.out.println(record.get("relValue").asString());
        }
        session.close();
        driver.close();
    }

    // 查询此节点关联的其它节点
    public void searchOtherRelation(Driver driver, String node){
        Session session = driver.session();
        StatementResult result = session.run("MATCH (n:Student {name:{name}})-[r:relName]->(m:Student) RETURN DISTINCT m.name As nodeName", Values.parameters("name",node));
        while(result.hasNext()){
            Record record = result.next();
            System.out.println(record.get("nodeName").asString());
        }
        session.close();
        driver.close();
    }
}
