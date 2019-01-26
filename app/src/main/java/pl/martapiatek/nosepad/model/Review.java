package pl.martapiatek.nosepad.model;

public class Review {

    private int key;
    private String brand;
    private String fragrance;
    private String notes;
    private String description;
    private int rating;


    public Review() {
    }

    public Review(String brand, String fragrance, String notes, String description, int rating) {
        this.brand = brand;
        this.fragrance = fragrance;
        this.notes = notes;
        this.description = description;
        this.rating = rating;
    }


    public int getKey() {
        return key;
    }

    public void setKry(int key) {
        this.key = key;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
