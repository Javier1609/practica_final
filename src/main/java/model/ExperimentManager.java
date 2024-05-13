package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExperimentManager {
    private List<Experiment> experiments;

    public ExperimentManager() {
        experiments = new ArrayList<>();
    }

    public List<Experiment> getExperiments() {
        return this.experiments;
    }

    public void saveExperiment(Experiment experiment) {
        this.experiments.add(experiment);
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