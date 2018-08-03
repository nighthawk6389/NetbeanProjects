package ecarnomics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GetMediaHelper {
    
    public GetMediaHelper(){
        
    }
    
	private static String WINNERS = "winners";
	private static String ARTICLES = "articles";
	private static String VIDEOS = "videos";
    private static String MISC = "misc";
        
        public static void main(String args []){
        Map holder = GetMediaHelper.getMediaList("media/media_11-26-2011.php");
        
        Iterator it = holder.values().iterator();
        int counter = 0;
        while(it.hasNext()){
        System.out.println(counter++ +": "+it.next());
        System.out.println();
        System.out.println();
        System.out.println();
        }
    }
        
	
        public static Map<String,List> getMediaList(String restOfURL){
        URL url = null;
        try {
            url = new URL("http://www.sidebump.com/mediaoftheweek/"+restOfURL);
        } catch (MalformedURLException ex) {
            System.out.println("MaformedURLException: " + ex.getMessage());
            System.exit(1);
        }
        
        BufferedReader stream = null;
		try {
			stream = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		List winnersLink = new ArrayList();
		List articlesLink = new ArrayList();
		List videosLink = new ArrayList();
		List winnersSum = new ArrayList();
		List articlesSum = new ArrayList();
		List videosSum = new ArrayList();
        List miscLink = new ArrayList();
		List miscSum = new ArrayList();
		
		Map<String,List> holder = new HashMap<String,List>();
		holder.put("winnersLink",winnersLink);
		holder.put("winnersSum",winnersSum);
		holder.put("articlesLink",articlesLink);
		holder.put("articlesSum",articlesSum);
		holder.put("videosLink",videosLink);
		holder.put("videosSum",videosSum);
		holder.put("miscLink",miscLink);
		holder.put("miscSum",miscSum);
		
		String currentType = WINNERS;
		Scanner s = new Scanner(stream);
		String next = "";
		String link = "";
		String summary = "";
		while(s.hasNext()){
			next = s.nextLine();
                        if(next.length() < 0)
                            continue;
                        
                        if(next.isEmpty() || next.equals("") || next.equals(" ")){
                            holder = putCorrectFieldsIntoArrays(holder,currentType,link,summary);
                            summary = "";
                            link = "";
                        }
			if( next.equalsIgnoreCase("winners") ){			
				currentType = WINNERS;
				continue;
			}
			if ( next.equalsIgnoreCase("articles") ){
				currentType = ARTICLES;
				continue;
			}
			if( next.equalsIgnoreCase("videos") ){
				currentType = VIDEOS;
				continue;
			}
			if( next.equalsIgnoreCase("misc") || next.equalsIgnoreCase("misc.") ){
				currentType = MISC;
				continue;
			}
			
			if( next.contains("http") || next.contains("www")){
				link = next;
                continue;
			}
			
			summary += next;
			
		}
		
		return holder;
	}

        private static Map<String,List> putCorrectFieldsIntoArrays(Map<String,List> holder, String currentType, String link, String summary) {
            
            if(Character.isDigit(link.charAt(0))){
                int from = 1;
                if( !Character.isLetter(link.charAt(1)))
                    from = 2;
                link = link.substring(from);
            }
            
            if( !link.startsWith("http") && !link.startsWith("www")){
                int index = link.indexOf("http");
                if( index == -1){
                    index = link.indexOf("www");
                }
                
                summary += link.substring(0, index);
                link = link.substring(index);
            }
            
            holder.get(currentType+"Link").add(link);
            holder.get(currentType+"Sum").add(summary);
            
            return holder;
        }

}
