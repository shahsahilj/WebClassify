import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class BingCount {

	public  static DocumentQ getDocs(String question,String site,String accountKey)
	{
		int count=0;
		HttpClient httpclient = new DefaultHttpClient();
	    site="site:"+site;
		DocumentQ d= new DocumentQ();
		try
		{
			String q="\'"+site+" "+question+"\'";
			String query="https://api.datamarket.azure.com/Bing/SearchWeb/Composite?$top=4&$format=Json&Query="+URLEncoder.encode(q, "UTF-8");
			
            HttpGet httpget = new HttpGet(query);
            byte[] accountKeyBytes = Base64.encodeBase64((":" + accountKey).getBytes());
            String accountKeyEnc = new String(accountKeyBytes);
            httpget.setHeader("Authorization", "Basic "+accountKeyEnc);

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            JSONObject jsonObj = new JSONObject(responseBody);
            JSONObject jObj = jsonObj.getJSONObject("d");
            JSONArray arr = jObj.getJSONArray("results");
            JSONObject j = arr.getJSONObject(0);
            JSONArray arrW = arr.getJSONObject(0).getJSONArray("Web");
            String b=j.getString("WebTotal");
            count=Integer.parseInt(b);
            d.docCount=count;
            d.query=question;
            for (int i = 0; i < arrW.length(); i++) {
            	JSONObject ite = arrW.getJSONObject(i);
                String B = ite.getString("Url");
                d.url[i]=B;
            }
			
		}
		catch(Exception e){
			System.out.println("We could not connect to the site..................................................");
		}
		return d;
	}

}
