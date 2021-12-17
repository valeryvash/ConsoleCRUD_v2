package model;

import model.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Writer implements Entity {
    private Long id;
    private String writerName;
    private List<Long> postsIdList;

    public Writer() {
        this.id = -1L;
        this.writerName = "null";
        this.postsIdList = new ArrayList<>();
    }

    public Writer(Long id, String writerName, List<Long> postsIdList) {
        this.id = id;
        this.writerName = writerName;
        this.postsIdList = postsIdList;
    }

    public Writer(Long id, String writerName) {
        this.id = id;
        this.writerName = writerName;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

//    public List<Long> getPostsIdList() {
//        return postsIdList;
//    }

//    public void setPostsIdList(List<Long> postsIdList) {
//        this.postsIdList = postsIdList;
//    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getId(), ((Writer) obj).getId());
    }

    @Override
    public String toString() {
        return          " :.:.:.: Writer :.:.:.:\n" +
                        " id: " + this.getId() + "\n" +
                        " name: " + this.getWriterName() + "\n" +
                        " post amount: " + this.getPostsIdStream().count() + "\n" +
                        " :.:.:.:.:.::.:.:.:.:.:";
    }

    public Stream<Long> getPostsIdStream() {
        return (Stream<Long>) this.postsIdList.stream();
    }

    public void setPostsIdStream(Stream<Long> postsIdStream) {
        this.postsIdList = postsIdStream.collect(Collectors.toList());
    }

    public void addPost(Post post) throws IllegalArgumentException{
        long id = post.getId();
        if (getPostsIdStream().anyMatch(postId -> postId==id)) {
            throw new IllegalArgumentException("Such post id already contains for current writer instance\n" + this.toString());
        } else {
            setPostsIdStream(Stream.concat(
                    getPostsIdStream(),
                    Stream.of(post.getId())
            ));
        }
    }

    public void deletePost(Post post) throws IllegalArgumentException {
        long id = post.getId();
        if (!getPostsIdStream().anyMatch(postId -> postId==id)) {
            throw new IllegalArgumentException("Such post id already doesn't contains for current writer instance\n" + this.toString());
        } else {
            setPostsIdStream(getPostsIdStream().filter(postId -> postId != id));
        }
    }
}
