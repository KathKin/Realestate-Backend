package com.example.Laba7.dto;

public class PropertyWithApplicationsDto {
    private Long propertyId;
    private String propertyTitle;
    private String propertyImageUrl;
    private long applicationsCount;

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }
    public String getPropertyImageUrl() { return propertyImageUrl; }
    public void setPropertyImageUrl(String propertyImageUrl) { this.propertyImageUrl = propertyImageUrl; }
    public long getApplicationsCount() { return applicationsCount; }
    public void setApplicationsCount(long applicationsCount) { this.applicationsCount = applicationsCount; }
}
