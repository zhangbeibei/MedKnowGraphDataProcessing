package neo4j;

import org.neo4j.driver.v1.Driver;

import java.util.List;
import java.util.Map;

/**
 * Created by Gdj on 2017/7/16.
 * neo4j服务操作接口
 */
public interface Neo4jService {

    // 创建疾病节点
    public void createDiseaseNode(Driver driver, List<Map<String, List<String>>> list);

    // 创建症状节点
    public void createSymptomNode(Driver driver, List<Map<String, List<String>>> list);

    // 创建症状节点
    public void createExamineNode(Driver driver, List<Map<String, List<String>>> list);

    // 创建症状节点
    public void createTreatNode(Driver driver, List<Map<String, List<String>>> list);

    // 创建症状节点
    public void createDrugNode(Driver driver, List<Map<String, List<String>>> list);


    // 删除节点
    public void deleteNode(Driver driver, Map<String, Object> params);
    // 得到所有节点
    public void getAllNode(Driver driver);
    // 创建节点关系
    public void createNodeRelation(Driver driver);
    // 删除节点关系
    public void deleteNodeRelation(Driver driver, Map<String, Object> params);
    // 查询所有节点关系
    public void searchAllNodeRelation(Driver driver, String relabe1);
    // 查询两个节点之间的关系
    public void searchBetweenNodeRelation(Driver driver, Map<String, Object> params);
    // 查询此节点关联的其它节点
    public void searchOtherRelation(Driver driver, String node);
}
