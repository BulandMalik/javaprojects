import java.util.concurrent.Future;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
 
public class Program {
    
    static void GetMonograph()
    {
        try {  
            String authScheme = "SHAREDKEY";           
            String clientid = "2051";
            String secret = "3tAioH+QAGzn3yONpRC2mKVp9ND+jDjckACH4TrXDFw=";
                         
            // Build connection
            String url = "https://api.fdbcloudconnector.com/CC/api/v1_3/";
            String authString = authScheme + " " + clientid  + ":" + secret; 
             
            // This is the request target
            String request = "DispensableGenerics/11/PatientEducationMonographs?callSystemName=ConsoleAppExample";
               
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url + request);
            // uses the Jersey "fluent" code notation
            final AsyncInvoker asyncInvoker = webTarget.request()
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("Authorization", authString)
                        .accept(MediaType.APPLICATION_JSON)
                        .async();
             
            final Future<javax.ws.rs.core.Response> responseFuture = asyncInvoker.get();
            final Response response = responseFuture.get();
             
            // jackson json mapper (In this case only used for pretty-printing the json result)
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            // cast response to an object so the mapper can serialize to json pretty print
            Object json = mapper.readValue(response.readEntity(String.class), Object.class);
             
            System.out.println("\nSending 'GET' request : " + url + request);
            System.out.println("Response Code : " + response.getStatus());
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
      
        } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
        }
    }


    static void GetPrescribableDrugs()
    {
        try {
            String authScheme = "SHAREDKEY";
            String clientid = "2051";
            String secret = "3tAioH+QAGzn3yONpRC2mKVp9ND+jDjckACH4TrXDFw=";

            // Build connection
            String url = "https://api.fdbcloudconnector.com/CC/api/v1_4/";
            String authString = authScheme + " " + clientid  + ":" + secret;

            // This is the request target
            String request = "/PrescribableDrugs/451299/Sigs?callSystemName=PrescribeNow&callId=1234&sigTypeCode=2";

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url + request);
            // uses the Jersey "fluent" code notation
            final AsyncInvoker asyncInvoker = webTarget.request()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Authorization", authString)
                    .accept(MediaType.APPLICATION_JSON)
                    .async();

            final Future<javax.ws.rs.core.Response> responseFuture = asyncInvoker.get();
            final Response response = responseFuture.get();

            // jackson json mapper (In this case only used for pretty-printing the json result)
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            // cast response to an object so the mapper can serialize to json pretty print
            Object json = mapper.readValue(response.readEntity(String.class), Object.class);

            System.out.println("\nSending 'GET' request : " + url + request);
            System.out.println("Response Code : " + response.getStatus());
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
  
    public static void main(String [] args)
    {
        System.out.println("************ Calling GetMonograph()");
        GetMonograph();
        System.out.println("\n\n_______________________________\n\n\n\n______________________________\n\n");
        System.out.println("************ Calling GetPrescribableDrugs()");
        GetPrescribableDrugs();
    }
}