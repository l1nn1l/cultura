package com.cultura.objects;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private Account account;
    private String date;
    private String caption;
    private int nbLikeReactions;
    private int nbLoveReactions;
    private int nbPartyReactions;
    private int nbSmileReactions;
    private int nbWowReactions;
    private int nbComments;
    private List<Comment> comments;



    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getNbLikeReactions() {
        return nbLikeReactions;
    }

    public void setNbLikeReactions(int nbLikeReactions) {
        this.nbLikeReactions = nbLikeReactions;
    }

    public int getNbLoveReactions() {
        return nbLoveReactions;
    }

    public void setNbLoveReactions(int nbLoveReactions) {
        this.nbLoveReactions = nbLoveReactions;
    }

    public int getNbSmileReactions() {
        return nbSmileReactions;
    }

    public void setNbSmileReactions(int nbSmileReactions) {
        this.nbSmileReactions = nbSmileReactions;
    }

    public int getNbPartyReactions() {
        return nbPartyReactions;
    }

    public void setNbPartyReactions(int nbPartyReactions) {
        this.nbPartyReactions = nbPartyReactions;
    }

    public int getNbWowReactions() {
        return nbWowReactions;
    }

    public void setNbWowReactions(int nbWowReactions) {
        this.nbWowReactions = nbWowReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>(); 
        }
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

}
