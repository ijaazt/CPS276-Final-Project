package model;


import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Learning {
    private String category;
    private String learning;
    private LocalDate date;
    private Integer id;
    private int userId;
    public Learning(String category, String learning, LocalDate date, Integer id, int userId) {
        this.category = category;
        this.learning = learning;
        this.date = date;
        this.id = id;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Learning learning1 = (Learning) o;
        return id == learning1.id &&
                userId == learning1.userId &&
                Objects.equals(category, learning1.category) &&
                Objects.equals(learning, learning1.learning) &&
                Objects.equals(date, learning1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, learning, date, id, userId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Learning{");
        sb.append("category='").append(category).append('\'');
        sb.append(", learning='").append(learning).append('\'');
        sb.append(", date=").append(date);
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }

    public void setUserId(int userId) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
