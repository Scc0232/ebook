package inteface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class InterfaceTest{
	

	/*随机数算法*/
	public static String generateNonceStr()
	{
	    return UUID.randomUUID().toString().replace("-", "");
	}
	
	private static final String  Key = "0e427d711dc3495fbe39c6168629d632";
	
	/*签名SIGN算法*/
	public static String sign(HashMap<String, String> param)
	{
	    String sign = "";
	    List<String> list = new ArrayList<String>(param.keySet());
	    Collections.sort(list);
	    for (String str : list) {
	    	sign = sign + str + "=" + param.get(str) + "&";
		}
	    sign = sign + "key=" + Key;
	    
	    return DigestUtils.md5Hex(sign);  /*进行MD5加密*/
	}
	
	@Test
	public void test(){
		try {
			WebResource client = Client.create().resource("");
			String url = "http://localhost:8080/zhetengba/industryMsg/findInDustryListByEmployeeCount.do";
			WebResource wr = client.path(url);
			MultivaluedMap<String, String> param = new MultivaluedMapImpl();
			param.add("industry", "互联网");

			String rs = wr.queryParams(param).accept(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println("返回结果=="+rs);
		} catch (Exception e) {
			System.err.print("Exception==" + e);
		}
	}

	
	@Test
	public void testProp(){
		String s = UUID.randomUUID().toString().replace("-", ""); 
		//String id = s.replace("-", "");
		System.out.println("s=[" + s + "] id = [" + s + "]");
	}
}
