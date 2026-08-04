package android.bignerdranch.learn2survive.domain.model;

public class LessonSection {
    private String id;
    private String title;
    private String content;
    private String lottieAnimationUrl;
    private String iconUrl;
    private SectionType type;
    private int order;

    public LessonSection() {
    }

    public LessonSection(String id, String title, String content, String lottieAnimationUrl, 
                        String iconUrl, SectionType type, int order) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lottieAnimationUrl = lottieAnimationUrl;
        this.iconUrl = iconUrl;
        this.type = type;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLottieAnimationUrl() {
        return lottieAnimationUrl;
    }

    public void setLottieAnimationUrl(String lottieAnimationUrl) {
        this.lottieAnimationUrl = lottieAnimationUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public enum SectionType {
        INTRODUCTION,
        BEFORE_DISASTER,
        DURING_DISASTER,
        AFTER_DISASTER,
        EMERGENCY_KIT,
        EVACUATION_TIPS,
        SAFETY_TIPS
    }
}
