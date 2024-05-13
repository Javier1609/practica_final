package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExperimentManager {
    private List<Experiment> experiments;
    private List<BacteriaPopulation> bacteriaPopulations;

    public ExperimentManager() {
        experiments = new ArrayList<>();
        bacteriaPopulations = new ArrayList<>();
    }

    public List<Experiment> getExperiments() {
        return this.experiments;
    }

    public List<BacteriaPopulation> getBacteriaPopulations() {
        return this.bacteriaPopulations;
    }

    public void saveExperiment(Experiment experiment) {
        this.experiments.add(experiment);
    }

    public void addExperiment(Experiment experiment) {
        this.experiments.add(experiment);
    }

    public void addBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        this.bacteriaPopulations.add(bacteriaPopulation);
    }

    public void saveExperimentAs(String name, String description) {
        Experiment experiment = new Experiment(name, description);
        this.experiments.add(experiment);
    }

    public Optional<Experiment> getExperimentByName(String name) {
        return experiments.stream()
                .filter(experiment -> experiment.getName().equals(name))
                .findFirst();
    }

    public Experiment loadExperiment(String name) throws IOException {
        Optional<Experiment> optionalExperiment = getExperimentByName(name);
        if (optionalExperiment.isPresent()) {
            return optionalExperiment.get();
        } else {
            throw new IOException("Experiment not found");
        }
    }
}