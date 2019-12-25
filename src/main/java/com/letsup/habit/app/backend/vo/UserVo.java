package com.letsup.habit.app.backend.vo;

public class UserVo {
    private Long id;
    private Long familyId;
    private String imei;
    private String newFamily;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String isNewFamily() {
        return newFamily;
    }

    public void setNewFamily(String newFamily) {
        this.newFamily = newFamily;
    }
}
