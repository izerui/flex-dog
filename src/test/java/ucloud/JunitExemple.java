package ucloud;

import com.github.izerui.file.utils.ApiClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JunitExemple {

    private static ApiClient apiClient = new ApiClient(
            "YDpdHw/bRMv/E4ROKDPVcL3PVMO2dnHO2NnX7gi4Dng9zRtX7qC3aw=="
            , "a4593c02274873fa0ae03f7ccb1d546edd950fe4");

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
