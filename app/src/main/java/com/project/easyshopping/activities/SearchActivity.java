package com.project.easyshopping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.google.firebase.auth.FirebaseAuth;
import com.project.easyshopping.R;
import com.project.easyshopping.data.model.CustomAdapter;
import com.project.easyshopping.data.model.RowItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

	private FirebaseAuth firebaseAuth;
	String currentUser;
	private Spinner cities;
	private Spinner categories;
	private Spinner subCategories;
	private Button btnSubmit;
	private WebView mWebView;
	ProgressDialog progressDialog;
	String[] websiteTitles;
	URI[] imageLinks;
	String[] description;
	ArrayList<RowItem> rowItems;
	ListView listView;
	CustomAdapter customAdapter;
	String googleApiKey = "";
	String cX = "";
	StringBuilder searchQuery = new StringBuilder();
	String searchCategory;
	String searchSubCategory;
	String searchCity;
	String googleSearchAPI = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCbcrx3RKOQxKspMkZCV-uhhDMtlYrZFAw&cx=004505579087157181330:ppsddjwrvuz&q=";
	private static final String GOOGLE_API_KEY = "AIzaSyCbcrx3RKOQxKspMkZCV-uhhDMtlYrZFAw";
	private static final String SEARCH_ENGINE_ID = "004505579087157181330:ppsddjwrvuz";
	private static final String TAG = "Search Activity";
	private static final int HTTP_REQUEST_TIMEOUT = 3 * 600000;
	static String result = null;
	Integer responseCode = null;
	String responseMessage = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		firebaseAuth = FirebaseAuth.getInstance();
		currentUser = firebaseAuth.getCurrentUser().toString();
		progressDialog = new ProgressDialog(this);
		listView = findViewById(R.id.list);
		cities = findViewById(R.id.cities);
		categories = findViewById(R.id.categories);
		subCategories = findViewById(R.id.subcategories);
		btnSubmit = findViewById(R.id.beginButton);
		rowItems = new ArrayList<>();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
		listView.setOnItemClickListener(SearchActivity.this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
		intent.putExtra("title", rowItems.get(position).getTitle());
		intent.putExtra("url", rowItems.get(position).getLink());
		startActivity(intent);
	}

	private void addListenerOnButton() {

		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder searchAPI = new StringBuilder(googleSearchAPI);
				if(searchCity == null) {
					Toast.makeText(SearchActivity.this, "Please select city", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(searchCategory == null) {
					Toast.makeText(SearchActivity.this,"Please select category", Toast.LENGTH_SHORT).show();
					return;
				}
				progressDialog.setTitle("Searching Popular E Stores");
				progressDialog.setMessage("Please wait...");
				progressDialog.setCanceledOnTouchOutside(true);
				progressDialog.show();
				String searchText = "Food in Islamabad";
				String cX = "";
				URL url = null;
				try {
					url = new URL(searchAPI.append(searchCategory).append("%20").append(joinString(searchSubCategory.split("\\s"))).append("%20").append("Order%20Online%20").append("in%20").append(searchCity).append("&alt=json").toString());
				} catch (MalformedURLException ex ){
					Log.e(TAG, "Error Creating String to URL " + ex.toString());
				}
				Log.d(TAG, "Url = "+  url.toString());

				GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
				searchTask.execute(url);
//				List<Result> results = search(searchQuery.append(searchCategory).append("%20").append(joinString(searchSubCategory.split("\\s"))).append("%20").append("Order%20Online%20").append("in%20").append(searchCity).append("&alt=json").toString());
//				setAdapter(results);
				rowItems.clear();

			}

		});
	}

	public void setAdapter(List<Result> results) {
//		for(Result result : results) {
//			RowItem rowItem = new RowItem(result.getTitle(), result.getImage(), result.getFormattedUrl());
//			rowItems.add(rowItem);
//		}
//		customAdapter = new CustomAdapter(SearchActivity.this , rowItems);
//		listView.setAdapter(customAdapter);
	}

	public static List<Result> search(String keyword){
		Customsearch customsearch= null;
		try {
			customsearch = new Customsearch(new NetHttpTransport(),new JacksonFactory(), new HttpRequestInitializer() {
				public void initialize(HttpRequest httpRequest) {
					try {
						// set connect and read timeouts
						httpRequest.setConnectTimeout(HTTP_REQUEST_TIMEOUT);
						httpRequest.setReadTimeout(HTTP_REQUEST_TIMEOUT);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Result> resultList=null;
		try {
			Customsearch.Cse.List list=customsearch.cse().list("Food in Islamabad");
			list.setKey(GOOGLE_API_KEY);
			list.setCx(SEARCH_ENGINE_ID);
			Search results=list.execute();
			resultList=results.getItems();
		}
		catch (  Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	private void addListenerOnSpinnerItemSelection() {
		cities.setOnItemSelectedListener(this);
		categories.setOnItemSelectedListener(this);
		subCategories.setOnItemSelectedListener(this);
		List<String> citiesList = new ArrayList<>();
		List<String> categoriesList = new ArrayList<>();
		List<String> subCategoriesList = new ArrayList<>();
		citiesList.add("Select City");
		citiesList.add("Islamabad");
		citiesList.add("Rawalpindi");
		citiesList.add("Lahore");
		citiesList.add("Karachi");
		citiesList.add("Faisalabad");
		citiesList.add("Quetta");
		citiesList.add("Peshawar");

		categoriesList.add("Select Category");
		categoriesList.add("Food");
		categoriesList.add("Clothing");
		categoriesList.add("Phones");
		categoriesList.add("Cars");

		subCategoriesList.add("Select SubCategory");


		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, citiesList);
		dataAdapter1.setDropDownViewResource(R.layout.spinner_item);

		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, categoriesList);
		dataAdapter2.setDropDownViewResource(R.layout.spinner_item);

		ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(this, R.layout.spinner_item, subCategoriesList);
		dataAdapter3.setDropDownViewResource(R.layout.spinner_item);


		// attaching data adapter to spinner
		cities.setAdapter(dataAdapter1);
		categories.setAdapter(dataAdapter2);
		subCategories.setAdapter(dataAdapter3);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_sign_out: {
				// Destroying login season.
				firebaseAuth.signOut();

				// Finishing current User Profile activity.
				finish();

				// Redirect to Login Activity after click on logout button.
				Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
				startActivity(intent);

				// Showing toast message on logout.
				Toast.makeText(SearchActivity.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(currentUser == null) {
			sendUserToLoginActivity();
		}
	}

	private void sendUserToLoginActivity() {
		Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
		startActivity(intent);
		Toast.makeText(SearchActivity.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		if(mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		ArrayAdapter<String> dataAdapter = null;
		if(parent.getId() == R.id.categories && position > 0){
			searchCategory = (String) parent.getItemAtPosition(position);
			List<String> subCategoresList = new ArrayList();
			if(parent.getSelectedItem() == "Food"){
				subCategoresList.add("Fast Food");
				subCategoresList.add("Restaurants");
			}else if(parent.getSelectedItem() == "Clothing"){
				subCategoresList.add("Brands");
				subCategoresList.add("Mall");
			}else if(parent.getSelectedItem() == "Phones"){
				subCategoresList.add("Brand New");
				subCategoresList.add("Used");
			}else if(parent.getSelectedItem() == "Cars"){
				subCategoresList.add("Brand New");
				subCategoresList.add("Used");
			}
			dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, subCategoresList);
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
			subCategories.setAdapter(dataAdapter);
			searchSubCategory = subCategoresList.get(0);
		}else if(parent.getId() == R.id.cities && position > 0)  {
			searchCity = (String) parent.getItemAtPosition(position);
		}else if(parent.getId() == R.id.subcategories){
			searchSubCategory = (String) parent.getItemAtPosition(position);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

		protected void onPreExecute() {

			Log.d(TAG, "AsyncTask - onPreExecute");

		}

		@Override
		protected String doInBackground(URL... urls) {

			URL url = urls[0];

			Log.d(TAG, "Async Task - doInBackground, url = " + url);

			// Http connection
			HttpURLConnection conn = null;

			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
			} catch (IOException e) {
				Log.e(TAG, "Http Connection Error " + e.toString());
			}

			try {
				responseCode = conn.getResponseCode();
				responseMessage = conn.getResponseMessage();
			} catch (IOException e) {
				Log.e(TAG, "Http Getting Response Code Error " + e.toString());
			}

			Log.d(TAG, "Http Response Code = " + responseCode + " Message :" + responseMessage);

			try {
				if (responseCode != null && responseCode == 200) {
					//respone OK
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line + "\n");
					}
					bufferedReader.close();
					conn.disconnect();
					result = sb.toString();
					Log.d(TAG, "Result = " + result);
					return result;
				} else {
					// Response Problem
					String errorMessage = "Http Error Response " + responseMessage + "\n" + " Are You Online ? " + "\n" +
							"Make sure to replace in code your own Google Api Key and Search Engine ID";
					Log.e(TAG, errorMessage);
					result = errorMessage;
					return result;
				}
			} catch (IOException e) {
				Log.e(TAG, "Http Response Error " + e.toString());
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {

			Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);
		}

		protected void onPostExecute(String result) {

			Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);

			// hide mProgressBar
			progressDialog.dismiss();

			// make TextView scrollable
			Log.d("GoogleSearchAsyncTask", "onPostExecute Method");
			List<HashMap<String, String>> searchList = null;
			try {
				if(result != null){
					JSONObject obj = new JSONObject(result);
					JSONArray itemsArray = obj.getJSONArray("items");
					for(int i = 0; i< itemsArray.length(); i++) {
						JSONObject itemsObject = itemsArray.getJSONObject(i);
						Uri uri = null;
//						if(itemsObject.getJSONObject("pagemap") != null && itemsObject.getJSONObject("pagemap").getJSONArray("cse_thumbnail") != null) {
//							uri = Uri.parse(itemsObject.getJSONObject("pagemap").getJSONArray("cse_thumbnail").getJSONObject(0).getString("src"));
//						}
						RowItem rowItem = new RowItem(itemsObject.getString("title"), R.drawable.easy_shopping_logo, itemsObject.getString("link"));
						rowItems.add(rowItem);
					}
					customAdapter = new CustomAdapter(SearchActivity.this , rowItems);
					listView.setAdapter(customAdapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  String[] is concatenated with %20 because it is a space in subcategory
	 *  It needs to be removed.
	 * @param element
	 * @return
	 */
	private String joinString(String[] element) {
		String mergeString = null;
		for(int i = 0; i < (element.length -1); i++ ) {
			mergeString = element[i] + "%20";
		}
		return mergeString + element[element.length - 1 ];
	}
}
