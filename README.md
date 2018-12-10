# Cucumber_RequestHelper
<p>
This little helper class helps you build an instance of Rest Assured FilterableRequestSpecification from a given table in the Cucumber feature file.

It looks for specific fields, therefore the table should have the following format:
<pre>
| request_base | Your request base path, eg. https://localhost:8080/api/v1                                      |
| request_path | Your request path to be appended to request base, eg. users/&ltuser&gt                             |
| headers      | Your headers in the following format: 'headerName:value', comma separated, eg. key:myKey       |
| query_params | Your query parameters in the following format: 'query=value', comma separated, eg. color=black |
| post_body    | Your content to be posted (in the case of a POST request), usually a JSON body                 |
</pre>
Example of cucumber feature:
<pre>
    Given I prepared the GET request for the users endpoint with
      | request_base | https://localhost:8080/api  |
      | request_path | /users/v1                   |
      | headers      | api-secret:t5ecng4u         |
      | query_params | allDetails=true             |
</pre>
Would translate to the following generated step method:
<pre>
@Given("^I prepared the GET request for the users endpoint with$")
public void i_prepared_the_GET_request_for_the_users_endpoint_with(<b>DataTable arg1</b>) throws Exception {
// Write code here that turns the phrase above into concrete actions
}
</pre>
The bold text in the method is the parameter that contains the table data above , which should be passed to the helper method from this package.
The method would then return a FilterableRequestSpecification which can be used to perform the HTTP request, and will print the details set in the request.

Project integration:
<p>
Method 1:
Build the Jar and reference it to your project.
</p>
<p>
Method 2:
Reference it to your Maven project using jitpack.io as follows:
Add the below repository.
</p>
<pre>
&lt;repositories&gt;
	&lt;repository&gt;
		&lt;id&gt;jitpack.io&lt;/id&gt;
		&lt;url&gt;https://jitpack.io&lt;/url&gt;
	&lt;/repository&gt;
&lt;/repositories&gt;
</pre>
Include the following in your dependencies. Be sure to take the last commit id and use it as version.
<pre>
&lt;dependency&gt;
	&lt;groupId&gt;com.github.QANewb&lt;/groupId&gt;
	&lt;artifactId&gt;Cucumber_RequestHelper&lt;/artifactId&gt;
	&lt;version&gt;b6a6c8d31a0679eddb49ee5ae666d85321d964e9&lt;/version&gt;
&lt;/dependency&gt;
</pre>
</p>
