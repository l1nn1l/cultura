package com.cultura.Requests;

import com.cultura.objects.Reactions;
import java.io.Serializable;

public class UpdateReactionsRequest implements Serializable {
    public String requestName;
    public Reactions reaction;

    public UpdateReactionsRequest(Reactions reaction){
        this.reaction = reaction;
        this.requestName = "UpdateReactionsRequest";
    }
}
