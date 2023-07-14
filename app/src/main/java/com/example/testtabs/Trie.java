package com.example.testtabs;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private TrieNode root;

    public  Trie(){
        root = new TrieNode(' '); //empty char
    }
    //insert a word into Trie
    public void insert(String word){
        if(search(word) == true) return; //only insert if the word is not already in Trie

        TrieNode curr = root;
        TrieNode pre;
        for(char ch: word.toCharArray()) { //loop through all chars in the word
            pre = curr;
            TrieNode child = curr.getChild(ch);
            if(child != null){ //if a child exists, move on
                curr = child;
                child.parent = pre;
            } else { //if a child does not exist, insert the new character, then move on
                curr.children.add(new TrieNode(ch));
                curr = curr.getChild(ch);
                curr.parent = pre;
            }
        }
        curr.isWord = true; //at end, mark isWord true
    }
    //return true if a word exist in Trie
    public boolean search(String word) {
        TrieNode curr = root;

        //first, see if a match can be found
        for (char ch: word.toCharArray()) {
            if (curr.getChild(ch) == null) return false;
            else curr = curr.getChild(ch);
        }
        //second, need to check if the match is a word
        if(curr.isWord == true) return true;
        else return false;
    }
    //return all words in Trie matches a prefix
    public ArrayList<String> autocomplete(String prefix){
        TrieNode lastnode = root;
        //loop through prefix to the last node
        for(int i = 0; i < prefix.length(); i++){
            lastnode = lastnode.getChild(prefix.charAt(i));
            if(lastnode == null) return new ArrayList<String>();
        }
        //use last node to call getWords to get all words associated with the last node
        return  lastnode.getWords();
    }
}
