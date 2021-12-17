package model;

import model.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Post implements Entity {
    private Long id;
    private String postContent;
    private List<Long> tagsIdList;
    private PostStatus postStatus;

    public Post() {
        this.id = -1L;
        this.postContent = "null";
        this.tagsIdList = new ArrayList<>();
        this.postStatus = PostStatus.ACTIVE;
    }

    public Post(Long id, String postContent, List<Long> tagsIdList, PostStatus postStatus) {
        this.id = id;
        this.postContent = postContent;
        this.tagsIdList = tagsIdList;
        this.postStatus = postStatus;
    }

    public Post(Long id, String postContent) {
        this.id = id;
        this.postContent = postContent;
        this.tagsIdList = new ArrayList<>();
        this.postStatus = PostStatus.ACTIVE;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostContent() {
        return this.postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

//    public List<Long> getTagsIdList() {
//        return this.tagsIdList;
//    }

//    public void setTagsIdList(List<Long> tagsIdList) {
//        this.tagsIdList = tagsIdList;
//    }

    public PostStatus getPostStatus() {
        return this.postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getId(), ((Post) obj).getId());
    }

    @Override
    public String toString() {
        return  " :.:.:.: Post :.:.:.: \n" +
                " id: " + this.getId() + "\n" +
                " content: \n" +
                " \t" + this.postContent + "\n" +
                " Status: " + (this.postStatus == PostStatus.ACTIVE ? "ACTIVE":"DELETED") + "\n";
    }

    public Stream<Long> getTagsIdStream() {
        return this.tagsIdList.stream();
    }

    public void setTagsIdStream(Stream<Long> tagsIdStream) {
        this.tagsIdList = tagsIdStream.collect(Collectors.toList());
    }

    public void addTagId(Long tagId) {
        this.setTagsIdStream(Stream.concat(this.getTagsIdStream(), Stream.of(tagId)));
    }

    public void deleteTagId(Long tagId) { this.setTagsIdStream(this.getTagsIdStream().filter(id -> !Objects.equals(id, tagId)));}
}
