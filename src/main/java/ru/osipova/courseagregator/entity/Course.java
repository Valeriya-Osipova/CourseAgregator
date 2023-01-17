package ru.osipova.courseagregator.entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private Long id;
    private String name;
    private String url;
    private String category;
    private String photo;
    private String school;
    private String cost;
    private String duration;
    @Transient
    private Float mark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Transient
    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    //    @Override
//    public String toString() {
//        return "Course{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", category='" + category + '\'' +
//                ", photo='" + photo + '\'' +
//                ", school='" + school + '\'' +
//                ", cost='" + cost + '\'' +
//                ", duration='" + duration + '\'' +
//                '}';
//    }
}
