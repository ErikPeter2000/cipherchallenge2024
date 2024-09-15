package main.utils;

public class TextFormatter {
    public static String formatText(String text){
        StringBuilder formatted = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if(c <65 || c > 90)continue;
            formatted.append(c);
        }
        return formatted.toString();
    }
}
