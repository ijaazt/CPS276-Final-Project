package model;


import java.util.Date;

public class Learning {
    private String category;
    private String learning;
    private Date date;
    private int id;
    private int userId;
    public Learning(String category, String learning, Date date, int id, int userId) {
        this.category = category;
        this.learning = learning;
        this.date = date;
        this.id = id;
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLearning() {
        return learning;
    }

    public void setLearning(String learning) {
        this.learning = learning;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
