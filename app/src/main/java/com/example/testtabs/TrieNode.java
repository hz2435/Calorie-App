package com.example.testtabs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrieNode { //store individual letters
    char content;
    LinkedList<TrieNode> children;
    TrieNode parent;
    boolean isWord;

    public TrieNode(char c){ //constructor
        content = c;
        //use LinkedList for children, no limit
        children = new LinkedList<TrieNode>();
        isWord = false;
    }
    public TrieNode getChild(char c){
        if(children != null)
            for (TrieNode eachChild : children)
                if(eachChild.content == c)
                    return eachChild;
        return null;
    }
    protected ArrayList<String> getWords(){
        ArrayList<String> list = new ArrayList<String>();
        if(isWord){ //if the node is a word, add the word to the ArrayList
            list.add(toString());
        }
        //then continue to go through all child nodes
        if (children != null){
            for (int i = 0; i < children.size(); i++){
                if (children.get(i) != null){
                    list.addAll(children.get(i).getWords());
                }
            }
        }
        return list;
    }
    //concatenate all chars into a string (word)
    public String toString(){
        if (parent == null) return "";
        return parent.toString() + new String(new char[] {content});
    }
}
