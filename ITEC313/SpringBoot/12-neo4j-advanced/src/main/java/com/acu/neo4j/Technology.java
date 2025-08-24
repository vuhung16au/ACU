package com.acu.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Technology")
public class Technology {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private String version;
    private Integer popularityScore;

    public Technology() {
    }

    public Technology(String name, String category, String version, Integer popularityScore) {
        this.name = name;
        this.category = category;
        this.version = version;
        this.popularityScore = popularityScore;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Integer popularityScore) {
        this.popularityScore = popularityScore;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", version='" + version + '\'' +
                ", popularityScore=" + popularityScore +
                '}';
    }
}
