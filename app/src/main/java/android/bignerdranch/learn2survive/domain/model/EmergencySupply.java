package android.bignerdranch.learn2survive.domain.model;

public class EmergencySupply {
    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private int weight;
    private int importance;
    private boolean isEssential;
    private String category;

    public EmergencySupply() {
    }

    public EmergencySupply(String id, String name, String description, String iconUrl,
                          int weight, int importance, boolean isEssential, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.weight = weight;
        this.importance = importance;
        this.isEssential = isEssential;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isEssential() {
        return isEssential;
    }

    public void setEssential(boolean essential) {
        isEssential = essential;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
