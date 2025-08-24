package com.acu.graphql;

import java.util.List;

public class BookConnection {
    private List<BookEdge> edges;
    private PageInfo pageInfo;
    private int totalCount;

    public BookConnection(List<BookEdge> edges, PageInfo pageInfo, int totalCount) {
        this.edges = edges;
        this.pageInfo = pageInfo;
        this.totalCount = totalCount;
    }

    public List<BookEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<BookEdge> edges) {
        this.edges = edges;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
