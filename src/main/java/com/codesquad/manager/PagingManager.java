package com.codesquad.manager;

public class PagingManager {

    private final int requestsedPage;
    private final int groupSize;
    private final int totalPages;
    private final int startPage;
    private final int endPage;
    private final boolean hasNextGroup;
    private final boolean hasPreviousGroup;

    public PagingManager( int requestedPage, int totalPages, int groupSize){
        this.requestsedPage = requestedPage;
        this.totalPages = totalPages;
        this.groupSize = groupSize;
        this.startPage = this.calculateStartPage();
        this.endPage = this.calculateEndPage();
        this.hasNextGroup = this.checkHasNextGroup();
        this.hasPreviousGroup = this.checkHasPreviousGroup();
    }


    private int calculateStartPage(){
        return (((this.requestsedPage - 1) / 5) * 5)+ 1;
    }

    private int calculateEndPage(){
        int startPage = this.calculateStartPage();
        if(!this.checkHasNextGroup()){
            return this.totalPages;
        }
        return startPage + this.groupSize - 1;
    }

    private boolean checkHasNextGroup(){
        int startPage = this.calculateStartPage();
        if(startPage + groupSize > this.totalPages){
            return false;
        }
        return true;
    }

    private boolean checkHasPreviousGroup(){
        int startPage = this.calculateStartPage();
        if(startPage >= this.groupSize){
            return true;
        }
        return false;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean isHasNextGroup() {
        return hasNextGroup;
    }

    public boolean isHasPreviousGroup() {
        return hasPreviousGroup;
    }
}
