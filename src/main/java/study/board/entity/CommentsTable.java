package study.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsTable extends TimeEntity{
    @Id @GeneratedValue
    @Column(name = "comments_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //@NotEmpty
    private String com;

    private Long hierarchy;
    private Long orders;
    private Long grp;

    private Long likes = 0L;



    public CommentsTable(Posts posts, Member member, String comments, Long hierarchy, Long order, Long group) {
        this.posts = posts;
        this.member = member;
        this.com = comments;
        this.hierarchy = hierarchy;
        this.orders = order;
        this.grp = group;

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
        this.com = comment;
    }

    public void setHierarchy(Long hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void setOrder(Long order) {
        this.orders = order;
    }

    public void setGroup(Long group) {
        this.grp = group;
    }
}
