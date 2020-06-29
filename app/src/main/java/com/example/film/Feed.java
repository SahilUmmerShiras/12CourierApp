package com.example.film;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.http.GET;

public class Feed {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_result")
    @Expose
    private double total_results;
    @SerializedName("total_pages")
    @Expose
    private double total_pages;
    @SerializedName("results")
    @Expose
    private ArrayList<Results> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public double getTotal_results() {
        return total_results;
    }

    public void setTotal_results(double total_results) {
        this.total_results = total_results;
    }

    public double getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(double total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }
}
