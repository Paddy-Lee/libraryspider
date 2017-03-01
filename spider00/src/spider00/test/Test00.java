package spider00.test;

import org.junit.Test;

import spider00.controller.Service;
import spider00.requst.RequestModel;

public class Test00 {
	@Test
	public void getLinks() {
		String url = "http://222.200.122.171:7771/searchresult.aspx";
		String[] params = { "anywords", "dt", "cl", "dept", "sf", "ob", "page", "dp", "sm" };
		String[] values = { "你", "ALL", "ALL", "ALL", "M_PUB_YEAR", "DESC", "1", "20", "table" };
		RequestModel requestModel = new RequestModel(url, params, values, "tb", RequestModel.CLASS, RequestModel.GET);
		Service service = new Service(requestModel);
		service.getLink();

	}
}
