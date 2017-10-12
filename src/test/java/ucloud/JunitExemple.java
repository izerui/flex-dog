package ucloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.utils.ApiClient;
import com.github.izerui.file.utils.ConfigUtils;
import com.github.izerui.file.vo.Deploy;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JunitExemple {

    private static ApiClient apiClient;

    static {
        try {
            Deploy deploy = new ObjectMapper().readValue(new File(ConfigUtils.rootPath + "deploy.json"), Deploy.class);
            apiClient = new ApiClient(deploy.getPublicKey(), deploy.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DescribeUHostInstance() throws Exception {


        Map<String, String> params = new HashMap<>();
        params.put("ProjectId", "org-skwhwp");
        params.put("Zone", "cn-gd-02");
        params.put("Region", "cn-gd");
        params.put("Offset", "0");
        params.put("Limit", "5000");
        params.put("Action", "DescribeUHostInstance");
        params.put("_timestamp", String.valueOf(System.currentTimeMillis()));

        String execute = apiClient.execute(params);
        System.out.println(execute);
    }
}
