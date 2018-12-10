package Helper;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import cucumber.api.DataTable;
import io.restassured.http.Header;
import io.restassured.specification.FilterableRequestSpecification;

public final class RequestHelper {
	
	/**
	 * Helps build an instance of FilterableRequestSpecification from a Cucumber feature file table.
	 * Requires a variable of type <b>cucumber.api.DataTable</b> to be provided.
	 * When designing the feature file, the table should have the following format:
	 * <pre>
	 * | request_base | Your request base path, eg. https://localhost:8080/api/v1                                      |
     * | request_path | Your request path to be appended to request base, eg. users/{@literal <user>}                  |
     * | headers      | Your headers in the following format: 'headerName:value', comma separated, eg. key:myKey       |
     * | query_params | Your query parameters in the following format: 'query=value', comma separated, eg. color=black |
     * | post_body    | Your content to be posted (in the case of a POST request), usually a JSON body                 |
     * </pre>
     * Request base is mandatory and can replace request_path if fully defining the request link.
     * <br>Rest of the parameters are optional and will be set as provided.
     * <br>Sets the request content and accept to application/json by default.
     * @param data ={@literal >} A <b>cucumber.api.DataTable</b> variable containing the above specified elements.
     * @return An io.restassured.specification.<b>FilterableRequestSpecification</b> object that can be used to perform HTTP requests with RestAssured/Cucumber.
	 */
	public static FilterableRequestSpecification buildRequest(DataTable data) {
		FilterableRequestSpecification toReturn = (FilterableRequestSpecification) given();
		Map<String,String> requestDetails = data.asMap(String.class, String.class);
		toReturn.given().baseUri(requestDetails.get("request_base"));
		if(requestDetails.containsKey("request_path")) {
			toReturn.given().basePath(requestDetails.get("request_path"));
		}
		if(requestDetails.containsKey("headers")) {
			String headers[] = requestDetails.get("headers").split(",");
			for (int i =0; i< headers.length; i++) {
				toReturn.given().header(headers[i].split(":")[0], headers[i].split(":")[1]);
			}
		}
		if(requestDetails.containsKey("query_params")) {
			String queryParams[] = requestDetails.get("query_params").split(",");
			for (int i =0; i<queryParams.length;i++) {
				toReturn.given().queryParam(queryParams[i].split("=")[0], queryParams[i].split("=")[1]);
			}
		}
		if(requestDetails.containsKey("post_body")) {
			toReturn.given().body(requestDetails.get("post_body"));
		}
		toReturn.given().contentType("application/json");
		toReturn.given().accept("application/json");
		
		System.out.println("Request prepared with the following details:");
		
		System.out.println("\tBase request link: " + toReturn.getBaseUri());
		if(requestDetails.containsKey("request_path")) {
			System.out.println("\tRquest path: " + toReturn.getBasePath());
		}
		if(requestDetails.containsKey("headers")) {
			System.out.println("\tHeaders:");
			List<Header> headers = toReturn.getHeaders().asList();
			for (int i =0; i<headers.size(); i++) {
				System.out.println("\t\t"+headers.get(i).getName()+ ": " + headers.get(i).getValue());
			}
		}
		if(requestDetails.containsKey("query_params")) {
			System.out.println("\tQuery parameters: ");
			Map<String,String> queryParams = toReturn.getQueryParams();
			for (Map.Entry<String, String> entry : queryParams.entrySet())
			{
			    System.out.println("\t\t" + entry.getKey() + ": " + entry.getValue());
			}
		}
		if(requestDetails.containsKey("post_body")) {
			System.out.println("\tPost body: " + toReturn.getBody());
		}
		return toReturn;
	}
}
