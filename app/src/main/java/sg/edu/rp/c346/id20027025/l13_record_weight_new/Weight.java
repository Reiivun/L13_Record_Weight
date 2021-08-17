package sg.edu.rp.c346.id20027025.l13_record_weight_new;

import java.io.Serializable;

public class Weight implements Serializable {

    private int id;
    private String description;
    private int weight;

    public Weight(int weight, String description) {
        this.description = description;
        this.weight = weight;
    }

    public Weight(int id, int weight, String description) {
        this.id = id;
        this.description = description;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public Weight setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Weight setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Weight setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
