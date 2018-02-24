import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class HTTPsRequest 
{
    public static void main(String[] args)
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/vision/v1.0/analyze?visualFeatures=Tags&details=Celebrities&language=en");

            builder.setParameter("visualFeatures", "Tags");
            builder.setParameter("details", "Celebrities");
            builder.setParameter("language", "en");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "4b9e55ceddcb464dbdca03f156d126ff");


            // Request body
            StringEntity reqEntity = new StringEntity("{\"url\":\"https://ufpro.com/media/uploads/public/product/-striker_xt_gen2_combat_pants_brown_grey_2.jpg\"}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        }
    }
}
