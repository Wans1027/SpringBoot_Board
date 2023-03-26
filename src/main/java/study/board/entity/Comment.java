package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeEntity{
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty
    private String comment;

    private Long hierarchy;
    private Long orders;
    private Long groups;

    private Long likes = 0L;



    public Comment(Posts posts, Member member, String comment, Long hierarchy, Long order, Long group) {
        this.posts = posts;
        this.member = member;
        this.comment = comment;
        this.hierarchy = hierarchy;
        this.orders = order;
        this.groups = group;

    }
    public void setLike(Long like) {
        this.likes = like;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setHierarchy(Long hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void setOrder(Long order) {
        this.orders = order;
    }

    public void setGroup(Long group) {
        this.groups = group;
    }
}
