package com.example.lms;

public class CircularEncryption {
    public String circularEncryption(String s, int key1, int key2){
        //key 1 - Even
        //key 2 - Odd
        String s_encrypted = "";
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if((32<=c && c<=47) || (58<=c && c<=64) || (91<=c && c<=96) || (123<=c && c<=127) ){ //special characters
                s_encrypted = s_encrypted + c;
            }
            if(i%2==0){//For Even Iteration
                if(65<=c && c<=90){//For Capital Letters
                    if(c+key1>90){
                        s_encrypted = s_encrypted + (char)(c+key1-26);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key1);
                    }
                }
                else if(97<=c && c<=122){ // For Small letters
                    if(c+key1>122){
                        s_encrypted = s_encrypted + (char)(c+key1-26);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key1);
                    }
                }
                else if(48<=c && c<=57){// For Numbers
                    if(c+key1>57){
                        s_encrypted = s_encrypted + (char)(c+key1-10);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key1);
                    }
                }
            }
            else{//For Odd Iterations
                if(65<=c && c<=90){//For Capital Letters
                    if(c+key2>90){
                        s_encrypted = s_encrypted + (char)(c+key2-26);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key2);
                    }
                }
                else if(97<=c && c<=122){ // For Small letters
                    if(c+key2>122){
                        s_encrypted = s_encrypted + (char)(c+key2-26);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key2);
                    }
                }
                else if(48<=c && c<=57){// For Numbers
                    if(c+key2>57){
                        s_encrypted = s_encrypted + (char)(c+key2-10);
                    }
                    else {
                        s_encrypted = s_encrypted+(char)(c+key2);
                    }
                }
            }
        }
        return s_encrypted;
    }
}
