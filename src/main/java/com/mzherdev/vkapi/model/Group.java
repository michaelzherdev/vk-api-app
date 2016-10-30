package com.mzherdev.vkapi.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
@Entity
@Table(name = "groups")
public class Group {

    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "subscribed", nullable = false)
    private Boolean subscribed = true;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "photo")
    private String photo;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "group")
    @OrderBy("date DESC")
    private List<Post> posts;

    public Group() {
    }

    public Group(Long id, String name, Boolean subscribed, String link, String photo) {
        this.id = id;
        this.name = name;
        this.subscribed = subscribed;
        this.link = link;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    public int getPostsSize() {
        List<Post> todayPosts = new ArrayList<Post>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());

        for(Post p: posts) {
            String postDate = dateFormat.format(p.getDate());
            if(postDate.equals(todayDate))
                todayPosts.add(p);
        }
        return todayPosts.size();
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subscribed=" + subscribed +
                ", link='" + link + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
