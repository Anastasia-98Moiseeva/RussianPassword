package com.example.russianpassword;

import java.util.Random;

class PasswordHelper {

    private final String[] russians;
    private final String[] latin;

    PasswordHelper(String[] russians, String[] latin) {
        this.russians = russians;
        this.latin = latin;

        if(russians.length != latin.length){
            throw new IllegalArgumentException();
        }
    }

    String generate(int length, String strForGenerate){
        String res = "";
        for(int i = 0; i < length; i++){
            Random rng = new Random();
            res += strForGenerate.charAt(rng.nextInt(strForGenerate.length()));
        }
        return res;
    }

    String convert(CharSequence source){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            String s = String.valueOf(c).toLowerCase();

            boolean flag = false;
            for (int j = 0; j < russians.length; j++) {
                if(russians[j].equals(s)){
                    result.append(Character.isUpperCase(c) ? latin[j].toUpperCase() : latin[j]);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                result.append(c);
            }
        }
        return result.toString();
    }

    int determinePasswordComplexity(String password) {
        int complexity = 0;
        boolean lowLetters = false;
        boolean upLetters = false;
        boolean numbers = false;
        boolean specialSymbols = false;
        for (int i = 0; i < password.length(); i++){
            if(String.valueOf(password.charAt(i)).matches("[a-zа-я]")){
                lowLetters = true;
            }
            if(String.valueOf(password.charAt(i)).matches("[0-9]")){
                upLetters = true;
            }
            if(String.valueOf(password.charAt(i)).matches("[A-ZА-Я]")){
                numbers = true;
            }
            if(String.valueOf(password.charAt(i)).matches("[!@#$%^*_]")){
                specialSymbols = true;
            }
        }
        if(lowLetters){
            complexity++;
        }
        if(upLetters){
            complexity++;
        }
        if(numbers){
            complexity++;
        }
        if(specialSymbols){
            complexity++;
        }
        if(password.length() > 7){
            complexity++;
        }
        if(password.length() > 11){
            complexity++;
        }
        if(complexity > 6) {
            complexity = 6;
        }
        return complexity;
    }
}
