package android.bignerdranch.learn2survive.domain.model;

import java.util.List;

public class Lesson {
    private String id;
    private DisasterType disasterType;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String mainLottieAnimation;
    private List<LessonSection> sections;
    private int totalSections;
    private long estimatedDurationMinutes;

    public Lesson() {
    }

    public Lesson(String id, DisasterType disasterType, String title, String description,
                 String thumbnailUrl, String mainLottieAnimation, List<LessonSection> sections,
                 int totalSections, long estimatedDurationMinutes) {
        this.id = id;
        this.disasterType = disasterType;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.mainLottieAnimation = mainLottieAnimation;
        this.sections = sections;
        this.totalSections = totalSections;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DisasterType getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(DisasterType disasterType) {
        this.disasterType = disasterType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMainLottieAnimation() {
        return mainLottieAnimation;
    }

    public void setMainLottieAnimation(String mainLottieAnimation) {
        this.mainLottieAnimation = mainLottieAnimation;
    }

    public List<LessonSection> getSections() {
        return sections;
    }

    public void setSections(List<LessonSection> sections) {
        this.sections = sections;
    }

    public int getTotalSections() {
        return totalSections;
    }

    public void setTotalSections(int totalSections) {
        this.totalSections = totalSections;
    }

    public long getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public void setEstimatedDurationMinutes(long estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }
}
