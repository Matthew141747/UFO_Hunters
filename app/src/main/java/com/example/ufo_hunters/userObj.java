package com.example.ufo_hunters;

public class userObj {
    private String FullName;
    private String email;

    private String documentId;

    //private String bio;

    public userObj(){
        //fire store requires empty contructor
    }

    public userObj(String FullName, String email){
        this.FullName = FullName;
        this.email = email;
    }

    public String getFullName(){
        return FullName;
    }

    public String getEmail(){
        return email;
    }
    /**
    public void setEmail(){
        this.email = email;
    }**/

    public String getDocumentId(){
        return documentId;
    }
    public void setDocumentId(String documentId){
        this.documentId = documentId;
    }


}
