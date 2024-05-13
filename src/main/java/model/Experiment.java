package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Experiment {
    private String name;
    private List<BacteriaPopulation> bacteriaPopulations;

    public Experiment(String name) {
        this.name = name;
        this.bacteriaPopulations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        if (bacteriaPopulationExists(bacteriaPopulation.getName())) {
            throw new IllegalArgumentException("A bacteria population with the same name already exists in the experiment.");
        }
        this.bacteriaPopulations.add(bacteriaPopulation);
    }

    public void removeBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        this.bacteriaPopulations.remove(bacteriaPopulation);
    }

    public Optional<BacteriaPopulation> getBacteriaPopulation(String name) {
        return this.bacteriaPopulations.stream()
                .filter(bacteriaPopulation -> bacteriaPopulation.getName().equals(name))
                .findFirst();
    }

    public boolean bacteriaPopulationExists(String name) {
        return this.bacteriaPopulations.stream()
                .anyMatch(bacteriaPopulation -> bacteriaPopulation.getName().equals(name));
    }

    public int getNumberOfBacteriaPopulations() {
        return this.bacteriaPopulations.size();
    }

    public List<BacteriaPopulation> getBacteriaPopulations() {
        return bacteriaPopulations;
    }
}