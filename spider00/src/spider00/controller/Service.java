package spider00.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import spider00.requst.RequestModel;
import spider00.response.ResponseModel;

public class Service {
	RequestModel request = null;

	public Service() {

	}

	public Service(RequestModel requestModel) {
		this.request = requestModel;
	}

	public void getLink() {
		String url = request.getUrl();
		String[] params = request.getParams();
		String[] values = request.getValues();
		String resultTagName = request.getResultTagName();
		int type = request.getType();
		int requestMethod = request.getRequestMethod();

		 String comurl = url;
		 for (int i = 0; i < params.length; i++) {
		 if (i == 0) {
		 try {
		 values[i] = URLEncoder.encode(values[i], "GBK").toLowerCase();
		 } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
		 }
		 comurl += "?" + params[i] + "=" + values[i];
		 } else {
		 comurl += "&" + params[i] + "=" + values[i];
		 }
		 }
		 System.out.println(comurl);
		 Document document = null;
		 try {
		 document = Jsoup.connect(comurl).get();
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

//		Connection connection = Jsoup.connect(url);
//		for (int i = 0; i < params.length; i++) {
//				connection.data(params[i], values[i]);
//			}


//		Document document = null;
//		switch (requestMethod) {
//		case RequestModel.GET:
//			try {
//				document = connection.timeout(100000).get();
//				break;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		case RequestModel.POST:
//			try {
//				document = connection.timeout(100000).post();
//				break;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		Elements elements = new Elements();
		switch (type) {
		case RequestModel.CLASS:
			elements = document.getElementsByClass(resultTagName);
			System.out.println("getele");
			break;
		case RequestModel.ID:
			Element element = document.getElementById(resultTagName);
			elements.add(element);
			break;
		case RequestModel.SELECTION:
			elements = document.select(resultTagName);
			break;
		default:
			if (resultTagName.isEmpty()) {
				elements = document.getElementsByTag("body");
			}
		}

		List<ResponseModel> responses = new ArrayList<>();
		for (Element element : elements) {
			Elements links = element.getElementsByTag("a");
			for (Element link : links) {
				String linkHref = link.attr("abs:href");
				String linkText = link.text();

				ResponseModel responseModel = new ResponseModel();
				responseModel.setHref(linkHref);
				responseModel.setText(linkText);

				responses.add(responseModel);
			}
		}

		for (ResponseModel response : responses) {
			System.out.println(response.getText());
			System.out.println(response.getHref());
			System.out.println("***********************************");
		}

	}

	public int getPage() {
		String url = request.getUrl();
		String[] params = request.getParams();
		String[] values = request.getValues();
		String resultTagName = request.getResultTagName();
		int type = request.getType();
		int requestMethod = request.getRequestMethod();

		Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element element = document.getElementById(resultTagName);
		String page = element.text();
		int pageNum = Integer.parseInt(page);
		return pageNum;

	}
}
