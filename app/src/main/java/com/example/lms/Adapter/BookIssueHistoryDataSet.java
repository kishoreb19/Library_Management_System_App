package com.example.lms.Adapter;

public class BookIssueHistoryDataSet {
    public String user_email,book_name,issue_date;

    public BookIssueHistoryDataSet(String user_email, String book_name, String issue_date) {
        this.user_email = user_email;
        this.book_name = book_name;
        this.issue_date = issue_date;
    }
}
