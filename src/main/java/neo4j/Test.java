package neo4j;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gdj on 2017/7/30.
 */
public class Test {

    // 创建症状节点
    public void createSymptomNode(Driver driver, List<String> list){
        Session session = driver.session();
        Map<String, Object> params = new HashMap<String, Object>();
        for (String val:list){
            session.run("CREATE (n:Symptom{ name:val,disease: d})");
        }
        session.close();
        driver.close();
    }

}
