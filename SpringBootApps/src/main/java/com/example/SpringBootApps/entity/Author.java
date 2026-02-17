package com.example.SpringBootApps.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "author_table")
public class Author {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String publisher;


    //Getters and Setters

    //Getters
    public Long getId() {
        return id;
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

    public String getBio(){
        return bio;
    }
    
    public String getPublisher(){
        return publisher;
    }

    //Setters
    public void setId(Long id){
        this.id = id;
    }

    public void setFirstName(String first_name){
        this.first_name = first_name;
    }

    public void setLastName(String last_name){
        this.last_name = last_name;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
}
