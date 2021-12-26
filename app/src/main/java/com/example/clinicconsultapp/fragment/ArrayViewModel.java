package com.example.clinicconsultapp.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ArrayViewModel extends ViewModel {
    private  final MutableLiveData<ArrayList<String>> docNames = new MutableLiveData<ArrayList<String>>();
    private  final MutableLiveData<ArrayList<Integer>> docCategories = new MutableLiveData<ArrayList<Integer>>();
    private  final MutableLiveData<ArrayList<String>> docCabinets = new MutableLiveData<ArrayList<String>>();


    public void setDocNames(ArrayList<String> docName){
        docNames.setValue(docName);
    }
    public void setDocCategory(ArrayList<Integer> docCategory){
        docCategories.setValue(docCategory);
    }
    public void setDocCabinets(ArrayList<String> docCabinet){docCabinets.setValue(docCabinet);}
    public MutableLiveData<ArrayList<String>> getDocCabinets() {
        return docCabinets;
    }

    public LiveData<ArrayList<String>> getDocNames(){
        return docNames;
    }

    public LiveData<ArrayList<Integer>> getDocCategories(){
        return docCategories;
    }



}
