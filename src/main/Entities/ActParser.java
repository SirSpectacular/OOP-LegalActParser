package Entities;

import Assets.Act;
import Assets.ActComponent;
import Assets.Hierarchy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActParser {
    private List<String> text;
    private List<Pattern> patternsToRemove;

    ActParser(List<String> text, List<Pattern> patterns){
        this.text = text;
        this.patternsToRemove = patterns;
    }

    private void removeLines() {
        for (Pattern pattern : patternsToRemove) {
            text = text.stream().
                    filter(pattern.asPredicate().negate()).
                    collect(Collectors.toList());
        }

    }

    private void joinLines(){
        for (int i = 0; i < text.size() - 1; i++){
            if(Pattern.matches("-$", text.get(i))){
                text.set(i, text.get(i).substring(0, text.get(i).length() - 1) + text.get(i+1));
                text.remove(i + 1);
            }
            else if(Pattern.matches("^Rozdział (\\d+[a-zA-Z]*|[IVXCDL]+[a-zA-Z]*)\\s*" ,text.get(i))){
                text.set(i, text.get(i) + ' ' + text.get(i+1));
                text.remove(i + 1);
            }
            else if(Pattern.matches("^DZIAŁ ([IVXCD]+[A-Z]*)\\s*", text.get(i))){
                text.set(i, text.get(i) + ' ' + text.get(i+1));
                text.remove(i + 1);
            }
        }
    }

    public  Act listToTree() {
        removeLines();
        joinLines();
        Act output = new Act();
        Stack stack = new Stack();
        for (String line : text) {
            for (Hierarchy type : Hierarchy.values()) {
                Pattern p = Pattern.compile(getPattern(type));
                Matcher m = p.matcher(line);
                if (m.find()) {
                    ActComponent component = ComponentFactory.makeComponent(type, m.group());
                    line = line.substring(m.end());
                    while (!stack.empty() && component.getWeight() <= ((ActComponent) stack.peek()).getWeight()) {
                        stack.pop();
                    }
                    if (!stack.empty()) {
                        ((ActComponent) stack.peek()).subComponents.add(component);
                    }
                    else {
                        output.subComponents.add(component);
                    }
                    stack.push(component);
                }
            }
            if(!line.isEmpty()) {
                if(!stack.empty()){
                    ((ActComponent)stack.peek()).extendBody(line);
                }
            }
        }
        return output;
    }


    private static String getPattern(Hierarchy type) {
            switch (type) {
                case Section:
                    return ("^DZIAŁ ([IVXCD]+[A-Z]?)\\s*.*");
                case Chapter:
                    return ("^Rozdział (\\d+\\p{L}?|[IVXCDL]+)\\s*.*");
                case Title:
                    return ("^[\\p{Lu} ]+\\s*$");
                case Article:
                    return ("^Art\\. (\\d+\\p{L}?)\\.\\s*");
                case Paragraph:
                    return ("^(\\d+\\p{L}?)\\.\\s.*");
                case Point:
                    return ("^(\\d+\\p{L}?)\\)\\s.*");
                case Letter:
                    return ("^(\\p{L})\\)\\s.*");
                default:
                    throw new IllegalArgumentException();
        }
    }
}