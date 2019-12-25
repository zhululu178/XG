package com.letsup.habit.app.backend.vo;

import java.util.ArrayList;
import java.util.List;

public class FamilyVo {
    private List<MemberVo> adults = new ArrayList<>();//大人
    private List<MemberVo> kids = new ArrayList<>();//小孩
    private List<MemberVo> all = new ArrayList<>();//所有成员

    public List<MemberVo> getAdults() {
        return adults;
    }

    public void setAdults(List<MemberVo> adults) {
        this.adults = adults;
    }

    public List<MemberVo> getAll() {
        return all;
    }

    public void setAll(List<MemberVo> all) {
        this.all = all;
    }

    public List<MemberVo> getKids() {
        return kids;
    }

    public void setKids(List<MemberVo> kids) {
        this.kids = kids;
    }
}
