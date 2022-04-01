package com.example.qrcode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class OcrTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<String>();
		
		File[] files = new File("C:\\Users\\pt4113\\Desktop\\\\OCR Sample 24 May_img").listFiles();

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
	}
//
//	public RestTemplate restTemplate(HttpRequest request) {
//	    RestTemplate restTemplate = new RestTemplate(request);
//	    return restTemplate;
//	    
//	    String result = restTemplate.getForObject(
//	            "https://10.0.14.175:8434/PIM_HKIDCard/singleUpload", String.class, params);
//
//	    String url = "https://10.0.14.175:8434/PIM_HKIDCard/singleUpload";
//	    JSONObject param = new JSONObject();
//	    ResponseEntity<JSONObject> responseEntity=restTemplate.postForEntity(url, params, JSONObject.class);
//	}
//	
//	public Object uplaod(@RequestBody JSONObject params) throws Exception{
//
//        final String url = "https://10.0.14.175:8434/PIM_HKIDCard/singleUpload";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        FileSystemResource resource = new FileSystemResource("C:\\Users\\pt4113\\Desktop\\\\OCR Sample 24 May_img");
//       
//        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
//        form.add("file", resource);
//        form.add("param1","value1");
//
//        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
//        JSONObject s = restTemplate.postForObject(url, files, JSONObject.class);
//        return s;
//    }
//	
//	
//	
//	@RequestMapping(path = "post", method = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST})
//	public String post(HttpServletRequest request,
//	        @RequestParam(value = "name", required = false) String name,
//	        @RequestParam(value = "birthday", required = false) String birthday,
//			@RequestParam(value = "sex", required = false) String sex,
//			@RequestParam(value = "id", required = false) String id){
//	   Map<String, Object> map = new HashMap<>();
////	   map.put("code", "200");
////	   map.put("");
//	   return JSON.toJSONString(map);
//	}
//	
//	public void testPost() {
//	    String url = "";
//	    String name = "";
//	    String birthday = "";
//	    String sex = "";
//	    String id = "";
//
//	    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
//	    request.add("name", name);
//	    request.add("birthday", birthday);
//	    request.add("sex", sex);
//	    request.add("id", id);
//	    
//	    ans = restTemplate.postForObject(url, request, String.class);
//	    System.out.println(ans);
//
//	}


}
