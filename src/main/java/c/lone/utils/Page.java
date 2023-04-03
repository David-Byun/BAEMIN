package c.lone.utils;

import lombok.Data;

/*
   처음에는 firstList = 1, lastList = 10으로 우리가 쿼리에서 설정한 ROWNUM을 기준으로 1~10번까지 데이터를
   가져오고 사용자가 스크롤을 바닥까지 내릴시 movePage가 2로 변하여 11~20번까지의 데이터를 가져오게 됩니다.
 */
@Data
public class Page {
    private int view = 10; //화면에 출력할 목록 수
    private int firstList; //페이지 첫번째
    private int lastList; //페이지 마지막

    public Page() {
        this(1);
    }

    public Page(int movePage) {
        page(movePage, view);
    }

    public Page(int movePage, int view) {
        page(movePage, view);
    }

    public void page(int movePage, int view) {
        firstList = (view * movePage) - view + 1;
        lastList = movePage * view;
    }
}
