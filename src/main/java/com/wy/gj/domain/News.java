package com.wy.gj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A News.
 */
@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "news_name")
    private String newsName;

    @Column(name = "news_type")
    private String newsType;

    @Column(name = "news_content")
    private String newsContent;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "send_time")
    private String sendTime;

    @ManyToOne
    private Household household;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        if(news.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "News{" +
            "id=" + id +
            ", newsId='" + newsId + "'" +
            ", newsName='" + newsName + "'" +
            ", newsType='" + newsType + "'" +
            ", newsContent='" + newsContent + "'" +
            ", receiver='" + receiver + "'" +
            ", sendTime='" + sendTime + "'" +
            '}';
    }
}
