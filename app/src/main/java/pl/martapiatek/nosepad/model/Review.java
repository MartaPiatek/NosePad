package pl.martapiatek.nosepad.model;

public class Review {

    private String brand;
    private String fragrance;
    private String notes;
    private String description;
    private float rating;


    public Review() {
    }

    public Review(String brand, String fragrance, String notes, String description, float rating) {
        this.brand = brand;
        this.fragrance = fragrance;
        this.notes = notes;
        this.description = description;
        this.rating = rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFragrance() {
        return fragrance;
    }

    public void setFragrance(String fragrance) {
        this.fragrance = fragrance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
