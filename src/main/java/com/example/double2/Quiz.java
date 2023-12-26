package com.example.double2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> variant = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    int katar = -1;
    int count = 0;

    public ArrayList<String> getQuestions(String filename){
        File file = new File(filename);
        String question = "";
        try(Scanner in = new Scanner(file)){
            while(in.hasNext()){
                katar++;
                question = in.nextLine();
                if(question.length() == 0){
                    count++;
                }
                if(katar == count) {
                    if(question.indexOf("{true, false}") > 0) {
                        question.replaceAll("\\{true, false}", "");
                        questions.add(question);
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        answers.add(in.nextLine());
                        count += 1;
                    }
                    else {
                        questions.add(question);
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        answers.add(in.nextLine());
                        count += 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return questions;
    }public ArrayList<String> getVariant(String filename){
        File file = new File(filename);
        String question = "";
        try(Scanner in = new Scanner(file)){
            while(in.hasNext()){
                katar++;
                question = in.nextLine();
                if(question.length() == 0){
                    count++;
                }
                if(katar == count) {
                    if(question.indexOf("{true, false}") > 0) {
                        question.replaceAll("\\{true, false}", "");
                        questions.add(question);
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        answers.add(in.nextLine());
                        count += 1;
                    }
                    else {
                        questions.add(question);
                        for(int i = 0; i < 4; i++){
                            variant.add(in.nextLine());
                        }
                        answers.add(in.nextLine());
                        count += 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return variant;
    }
    public ArrayList<String> getAnswers(String filename){
        File file = new File(filename);
        String question = "";
        try(Scanner in = new Scanner(file)){
            while(in.hasNext()){
                katar++;
                question = in.nextLine();
                if(question.length() == 0){
                    count++;
                }
                if(katar == count) {
                    if(question.indexOf("{true, false}") > 0) {
                        question.replaceAll("\\{true, false}", "");
                        questions.add(question);
                        variant.add(in.nextLine());
                        variant.add(in.nextLine());
                        answers.add(in.nextLine());
                        count += 1;
                    }
                    else {
                        questions.add(question);
                        for(int i = 0; i < 4; i++){
                            variant.add(in.nextLine());
                        }
                        answers.add(in.nextLine());
                        count += 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public ArrayList<String> getVariant() {
        return variant;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }
}
