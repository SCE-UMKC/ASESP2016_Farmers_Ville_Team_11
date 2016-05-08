package com.example.nagakrishna.farmville_new;

/**
 * Created by Naga Krishna on 29-04-2016.
 */
import java.util.List;

public class TrendsModel {

    private String title;
    private String source;

    public void setAuthorsList(String authorsList) {
        this.authorsList = authorsList;
    }

    public String getAuthorsList() {
        return authorsList;
    }

    private String authorsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    public static class Authors{
        private String name;   //'name' is key value in the actual video but in our data we dont have any key value

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
