package com.emmanuelnike.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
/*import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;*/
import org.springframework.cache.annotation.Cacheable;

import com.emmanuelnike.models.AccessToken;
import com.emmanuelnike.models.Config;
import com.emmanuelnike.models.DiagnosedSpecialisation;
import com.emmanuelnike.models.Gender;
import com.emmanuelnike.models.HealthDiagnosis;
import com.emmanuelnike.models.HealthIssueInfo;
import com.emmanuelnike.models.HealthItem;
import com.emmanuelnike.models.HealthSymptomSelector;
import com.emmanuelnike.models.SelectorStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//@EnableCaching
public class DiagnosisClient {
	
	private AccessToken token;
    private String language;
    private String healthServiceUrl;
    
    private CloseableHttpClient httpclient;
	
    /// <summary>
    /// DiagnosisClient constructor
    /// </summary>
	public DiagnosisClient() throws Exception {

		HandleRequiredArguments(Config.username, Config.password, Config.authUrl, Config.language, Config.healthUrl);
		
		httpclient = HttpClients.createDefault();
		
        this.healthServiceUrl = Config.healthUrl;
        this.language = Config.language;
        
		token = LoadToken(Config.username, Config.password, Config.authUrl);

	}
	
	@Cacheable(cacheNames={"tokens"}, key="#username")
	private AccessToken LoadToken(String username, String password, String url) throws Exception {
		System.out.println("Tokens data - " + username + ":" + password);
		SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "HmacMD5");

		String computedHashString = "";
		try {
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(keySpec);
			byte[] result = mac.doFinal(url.getBytes());
			
	        //BASE64Encoder encoder = new BASE64Encoder();
	        computedHashString = Base64.getEncoder().encodeToString(result); 
	        
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Can not create token (NoSuchAlgorithmException)");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Can not create token (InvalidKeyException)");
		}
		
		HttpPost httpPost = new HttpPost(url);	
		httpPost.setHeader("Authorization", "Bearer " + username + ":" + computedHashString);
		
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			
			ObjectMapper objectMapper = new ObjectMapper();
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				RetrieveException(response, objectMapper);
			}
			return objectMapper.readValue(response.getEntity().getContent(), AccessToken.class);
			//token = accessToken;
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Can not create token (ClientProtocolException)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Can not create token (IOException)");
		}
    }

	
	private void RetrieveException(CloseableHttpResponse response, ObjectMapper objectMapper) throws Exception {
		System.out.println("Resposne with status code: " + response.getStatusLine().getStatusCode());
		System.out.println(response.getEntity().getContent());
		String errorMessage = objectMapper.readValue(response.getEntity().getContent(), String.class);
		System.out.println("Resposne with status code: " + response.getStatusLine().getStatusCode() + ", error message: " + errorMessage);
		throw new Exception(errorMessage);
	}
	
	
	private void HandleRequiredArguments(String username, String password, String authServiceUrl, String language, String healthServiceUrl)     {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Argument missing: username");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Argument missing: password");

        if (authServiceUrl == null || authServiceUrl.isEmpty())
            throw new IllegalArgumentException("Argument missing: authServiceUrl");

        if (language == null || language.isEmpty())
            throw new IllegalArgumentException("Argument missing: language");

        if (healthServiceUrl == null || healthServiceUrl.isEmpty())
            throw new IllegalArgumentException("Argument missing: healthServiceUrl");
    }
	
	
	private <T> T loadFromWebService(String action, TypeReference<?> valueTypeRef) throws Exception 
    {
        String extraArgs = "token=" + this.token.Token + "&format=json&language=" + this.language;
        String url = new StringBuilder(this.healthServiceUrl).append("/").append(action).append(action.contains("?") ? "&" : "?").append(extraArgs).toString();

        HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			ObjectMapper objectMapper = new ObjectMapper();
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				RetrieveException(response, objectMapper);
			}	
			
			@SuppressWarnings("unchecked")
			T resultsObject = (T) objectMapper.readValue(response.getEntity().getContent(), valueTypeRef);
			return resultsObject;				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Fail communication with web-service");
		}			
    }
	
	/// <summary>
    /// Load all symptoms
    /// </summary>
    /// <returns>Returns list of all symptoms</returns>
	public List<HealthItem> loadSymptoms() throws Exception
    {
        return this.<List<HealthItem>>loadFromWebService("symptoms", new TypeReference<List<HealthItem>>(){});
    }
	
	/// <summary>
    /// Load all issues
    /// </summary>
    /// <returns>Returns list of all issues</returns>
	public List<HealthItem> loadIssues() throws Exception
    {
        return this.<List<HealthItem>>loadFromWebService("issues", new TypeReference<List<HealthItem>>(){});
    }
	 
	 /// <summary>
     /// Load detail informations about selected issue
     /// </summary>
     /// <param name="issueId"></param>
     /// <returns>Returns detail informations about selected issue</returns>
	 public HealthIssueInfo loadIssueInfo(int issueId) throws Exception
     {
         String action = "issues/" + issueId + "/info";
         return this.<HealthIssueInfo>loadFromWebService(action, new TypeReference<HealthIssueInfo>(){});
     }

	 /// <summary>
     /// Load calculated list of potential issues for selected parameters
     /// </summary>
     /// <param name="selectedSymptoms">List of selected symptom ids</param>
     /// <param name="gender">Selected person gender (Male, Female)</param>
     /// <param name="yearOfBirth">Selected person year of born</param>
     /// <returns>Returns calculated list of potential issues for selected parameters</returns>
     public List<HealthDiagnosis> loadDiagnosis(List<Integer> selectedSymptoms, Gender gender, int yearOfBirth) throws Exception
     {
         if (selectedSymptoms == null || selectedSymptoms.size() == 0)
             throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

         String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
         String action = "diagnosis?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;
         return this.<List<HealthDiagnosis>>loadFromWebService(action, new TypeReference<List<HealthDiagnosis>>(){});
     }

     
     /// <summary>
     /// Load calculated list of specialisations for selected parameters
     /// </summary>
     /// <param name="selectedSymptoms">List of selected symptom ids</param>
     /// <param name="gender">Selected person gender (Male, Female)</param>
     /// <param name="yearOfBirth">Selected person year of born</param>
     /// <returns>Returns calculated list of specialisations for selected parameters</returns>
     public List<DiagnosedSpecialisation> loadSpecialisations(List<Integer> selectedSymptoms, Gender gender, int yearOfBirth) throws Exception
     {
    	 if (selectedSymptoms == null || selectedSymptoms.size() == 0)
             throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

         String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
         String action = "diagnosis/specialisations?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;
         return this.<List<DiagnosedSpecialisation>>loadFromWebService(action, new TypeReference<List<DiagnosedSpecialisation>>(){});
     }
     
     
     /// <summary>
     /// Load human body locations
     /// </summary>
     /// <returns>Returns list of human body locations</returns>
     public List<HealthItem> loadBodyLocations() throws Exception
     {
         return this.<List<HealthItem>>loadFromWebService("body/locations", new TypeReference<List<HealthItem>>(){});
     }

     /// <summary>
     /// Load human body sublocations for selected human body location
     /// </summary>
     /// <param name="bodyLocationId">Human body location id</param>
     /// <returns>Returns list of human body sublocations for selected human body location</returns>
     public List<HealthItem> loadBodySubLocations(int bodyLocationId) throws Exception
     {
         String action = "body/locations/" + bodyLocationId;
         return this.<List<HealthItem>>loadFromWebService(action, new TypeReference<List<HealthItem>>(){});
     }

     /// <summary>
     /// Load all symptoms for selected human body location
     /// </summary>
     /// <param name="locationId">Human body sub location id</param>
     /// <param name="selectedSelectorStatus">Selector status (Man, Woman, Boy, Girl)</param>
     /// <returns>Returns list of all symptoms for selected human body location</returns>
     public List<HealthSymptomSelector> loadSublocationSymptoms(int locationId, SelectorStatus selectedSelectorStatus) throws Exception
     {
         String action ="symptoms/" + locationId + "/" + selectedSelectorStatus.toString();
         return this.<List<HealthSymptomSelector>>loadFromWebService(action, new TypeReference<List<HealthSymptomSelector>>(){});
     }

     ///<summary>
     /// Load list of proposed symptoms for selected symptoms combination
     /// </summary>
     /// <param name="selectedSymptoms">List of selected symptom ids</param>
     /// <param name="gender">Selected person gender (Male, Female)</param>
     /// <param name="yearOfBirth">Selected person year of born</param>
     /// <returns>Returns list of proposed symptoms for selected symptoms combination</returns>
     public List<HealthItem> loadProposedSymptoms(List<Integer> selectedSymptoms, Gender gender, Integer yearOfBirth) throws Exception
     {
         if (selectedSymptoms == null || selectedSymptoms.size() == 0)
             throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

         String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
         String action = "symptoms/proposed?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;

         return this.<List<HealthItem>>loadFromWebService(action, new TypeReference<List<HealthItem>>(){});
     }

     /// <summary>
     /// Load red flag text for selected symptom
     /// </summary>
     /// <param name="symptomId">Selected symptom id</param>
     /// <returns>Returns red flag text for selected symptom</returns>
     public String loadRedFlag(int symptomId) throws Exception
     {
         String action = "redflag?symptomId=" + symptomId;
         return this.<String>loadFromWebService(action,  new TypeReference<String>(){});
     }
}
