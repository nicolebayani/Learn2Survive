package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;
import java.util.List;

public class UserProgress {
    private String userId;
    private String lessonId;
    private int completedSections;
    private int totalSections;
    private boolean isBookmarked;
    private boolean isCompleted;
    private List<String> completedSectionIds;
    private Date lastAccessedAt;
    private Date completedAt;

    public UserProgress() {
    }

    public UserProgress(String userId, String lessonId, int completedSections, int totalSections,
                       boolean isBookmarked, boolean isCompleted, List<String> completedSectionIds,
                       Date lastAccessedAt, Date completedAt) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.completedSections = completedSections;
        this.totalSections = totalSections;
        this.isBookmarked = isBookmarked;
        this.isCompleted = isCompleted;
        this.completedSectionIds = completedSectionIds;
        this.lastAccessedAt = lastAccessedAt;
        this.completedAt = completedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public int getCompletedSections() {
        return completedSections;
    }

    public void setCompletedSections(int completedSections) {
        this.completedSections = completedSections;
    }

    public int getTotalSections() {
        return totalSections;
    }

    public void setTotalSections(int totalSections) {
        this.totalSections = totalSections;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<String> getCompletedSectionIds() {
        return completedSectionIds;
    }

    public void setCompletedSectionIds(List<String> completedSectionIds) {
        this.completedSectionIds = completedSectionIds;
    }

    public Date getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(Date lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public double getProgressPercentage() {
        if (totalSections == 0) return 0;
        return (double) completedSections / totalSections * 100;
    }
}
