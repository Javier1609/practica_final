package model;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
    private String name;
    private String description;
    private List<BacteriaPopulation> bacteriaPopulations;

    public Experiment(String name, String description) {
        this.name = name;
        this.description = description;
        this.bacteriaPopulations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        if (bacteriaPopulationExists(bacteriaPopulation.getName())) {
            throw new IllegalArgumentException("A bacteria population with the same name already exists in the experiment.");
        }
        this.bacteriaPopulations.add(bacteriaPopulation);
    }

    public List<BacteriaPopulation> getBacteriaPopulations() {
        return this.bacteriaPopulations;
    }

    public void removeBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        this.bacteriaPopulations.remove(bacteriaPopulation);
    }

    private boolean bacteriaPopulationExists(String name) {
        return this.bacteriaPopulations.stream()
                .anyMatch(bacteriaPopulation -> bacteriaPopulation.getName().equals(name));
    }
}