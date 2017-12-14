package com.crawler.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		String term = args[0];
		ArrayList<String> results = new ArrayList<>();
		
		try {
			System.out.println("Looking results page for: " + term + "...");
			results = getGoogleResultsPage(term);
			System.out.println(results.size() + " results found.");
		} catch (IOException e) {
			System.out.println("Error while trying to get google results page: " + e.getMessage());
		}
		
		HashMap<String, Integer> topScripts = getTopScripts(results);
		
		printResults(topScripts);

					
	}
	
	private static void printResults(HashMap<String, Integer> topScripts) {
		//Sorting topScripts through TreeMap and Integer Comparator and printing top 5 scripts:
		TreeMap<String, Integer> sortedMap = sortMapByValue(topScripts);  
		Object[] values = sortedMap.values().toArray();
		Object[] keys = sortedMap.keySet().toArray();
		int ind = sortedMap.size() -1;
		for(int i = 0; i < 5; i++) {
			if (ind > -1) {
				System.out.println(keys[ind] + " : " + values[ind]);
				ind--;
			}
		}

	} 
	private static ArrayList<String> getGoogleResultsPage(String term) throws IOException
	{
		String searchUrl = "https://www.google.com.br/search?q=" + term;
		Connection connection = Jsoup.connect(searchUrl);
		connection.maxBodySize(0);
		Document doc = connection.userAgent("Chrome").get();
		// finding Results
		ArrayList<String> results = new ArrayList<>();
		Elements newsHeadlines = doc.select("#search").select("h3.r a");
		for (Element headline : newsHeadlines) {
			// cleaning urls
			String result = headline.attr("href").toString().replace("/url?q=","");
			result = result.substring(0, result.indexOf("&"));
			if (!result.equals("/search?q="+term))
				results.add(result);
		}
 		
		return results;
		
	}
	
	private static HashMap<String, Integer> getTopScripts(ArrayList<String> links) {
		HashMap<String, Integer> totalResults = new HashMap<>();
		
		for (String link : links) {
			System.out.println("Looking scrtips for: " + link + "...");
			Connection connection = Jsoup.connect(link);
			connection.maxBodySize(0);
			Document doc;
			try {
				doc = connection.userAgent("Chrome").get();
				//finding scripts
				Elements scripts = doc.select("script");
				for (Element script : scripts) {
					//finding libraries
					String lib = script.attr("src");
					//cleaning libraries
					if (!lib.equals("")) {
						lib = cleanScriptName(lib).toLowerCase();
						if(totalResults.get(lib) == null)
							totalResults.put(lib, 1);
						else
							totalResults.put(lib, totalResults.get(lib) +1);
					}
				}
			} catch (IOException e) {
				System.out.println("Error while trying to get scripts from " + link);
			}
		}
		
		return totalResults;
	};
	
	private static String cleanScriptName(String lib) {
		if (lib.lastIndexOf("/") > 0)
			lib = lib.substring(lib.lastIndexOf("/")+1);
		
		return lib;
	}
	
	private static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
		Comparator<String> comparator = new ValueComparator(map); 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
}
