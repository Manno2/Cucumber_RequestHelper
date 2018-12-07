package Helper;
import static io.restassured.RestAssured.given;

import java.util.Map;

import cucumber.api.DataTable;
import io.restassured.specification.FilterableRequestSpecification;
/**
 * 
 *Helps build an instance of FilterableRequestSpecification from a Cucumber feature file table.
 *<br>Reference in your project as follows:
 *<br>Jar should be copied to the project root location.
 *<pre>
 *{@literal <}dependency{@literal >}
 *	{@literal <}groupId{@literal >}RequestHelper.Cucumber.RestAssured{@literal <}/groupId{@literal >}
 *	{@literal <}artifactId{@literal >}Cucumber_RequestHelper{@literal <}/artifactId{@literal >}
 *	{@literal <}version{@literal >}0.0.1-SNAPSHOT{@literal <}/version{@literal >}
 *	{@literal <}scope{@literal >}system{@literal <}/scope{@literal >}
 *	{@literal <}systemPath{@literal >}${project.basedir}/RequestHelper.jar{@literal <}/systemPath{@literal >}
 *{@literal <}/dependency{@literal >}
 *<pre>
 *
 */
public final class RequestHelper {
	
	/**
	 * 
	 * Requires a variable of type DataTable to be provided.
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
			System.out.println("\tHeaders: " + toReturn.getHeaders());
		}
		if(requestDetails.containsKey("query_params")) {
			System.out.println("\tQuery parameters: " + toReturn.getQueryParams());
		}
		if(requestDetails.containsKey("post_body")) {
			System.out.println("\tPost body: " + toReturn.getBody());
		}
		return toReturn;
	}
}
