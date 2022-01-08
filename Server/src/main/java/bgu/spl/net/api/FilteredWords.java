package bgu.spl.net.api;

import java.util.HashSet;

public class FilteredWords {
    private static HashSet<String> filteredWords = new HashSet<String>(){{
        add("facebook");
        add("twitter");
        add("instagram");
        add("tiktok");
        add("meta");
    }};

    public static String filterWords(String content) {
        String filteredContent = content;
        for (String forbiddenWord: filteredWords) {
            if(content.contains(forbiddenWord)){
                filteredContent = filteredContent.replaceAll(forbiddenWord,"<filtered>");
            }
        }
        return filteredContent;
    }
}
