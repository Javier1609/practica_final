package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExperimentManager {
    private List<Experiment> experiments;
    private Experiment currentExperiment;
    private Food foodManager; // Cambiado de Food a FoodManager

    public ExperimentManager() {
        this.experiments = new ArrayList<>();
        this.foodManager = new Food(); // Cambiado de Food() a FoodManager()
    }

    public void createNewExperiment(String name) {
        this.currentExperiment = new Experiment(name);
        this.experiments.add(currentExperiment);
    }

    public void deleteExperiment(String name) {
        this.experiments.removeIf(experiment -> experiment.getName().equals(name));
        if (currentExperiment != null && currentExperiment.getName().equals(name)) {
            currentExperiment = null;
        }
    }

    public void addPopulationToCurrentExperiment(BacteriaPopulation population) {
        if (this.currentExperiment != null) {
            this.currentExperiment.addBacteriaPopulation(population);
        } else {
            throw new IllegalStateException("No current experiment. Please create a new experiment first.");
        }
    }

    public void removePopulationFromCurrentExperiment(BacteriaPopulation population) {
        if (this.currentExperiment != null) {
            this.currentExperiment.removeBacteriaPopulation(population);
        } else {
            throw new IllegalStateException("No current experiment. Please create a new experiment first.");
        }
    }

    public Optional<BacteriaPopulation> getPopulationDetails(String name) {
        if (this.currentExperiment != null) {
            return this.currentExperiment.getBacteriaPopulation(name);
        } else {
            throw new IllegalStateException("No current experiment. Please create a new experiment first.");
        }
    }
    public void feed(String bacteriaName, String foodType, int quantity) {
        if (this.currentExperiment != null) {
            Optional<BacteriaPopulation> bacteriaPopulation = this.currentExperiment.getBacteriaPopulation(bacteriaName);
            if (bacteriaPopulation.isPresent()) {
                // Aquí se llama al método feed en FoodManager
                int foodFed = this.foodManager.feed(foodType, quantity);
                // Aquí puedes agregar el código para actualizar la población de bacterias con la comida alimentada
            } else {
                throw new IllegalArgumentException("No bacteria population with the given name in the current experiment.");
            }
        } else {
            throw new IllegalStateException("No current experiment. Please create a new experiment first.");
        }
    }

    public void saveExperiments(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Experiment experiment : experiments) {
            sb.append(experiment.toString()).append("\n");
        }
        Files.writeString(Path.of(filePath), sb.toString());
    }

    public void loadExperiments(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        for (String line : lines) {
            Experiment experiment = new Experiment(line);
            experiments.add(experiment);
        }
    }

    public List<Experiment> getAllExperiments() {
        return this.experiments;
    }

    public void setCurrentExperiment(Experiment experiment) {
        if (this.experiments.contains(experiment)) {
            this.currentExperiment = experiment;
        } else {
            throw new IllegalArgumentException("The provided experiment does not exist.");
        }
    }

    public boolean experimentExists(String name) {
        return this.experiments.stream()
                .anyMatch(experiment -> experiment.getName().equals(name));
    }

    public void feedBacteriaPopulation(String bacteriaName, String foodType, int quantity) {
        if (this.currentExperiment != null) {
            Optional<BacteriaPopulation> bacteriaPopulation = this.currentExperiment.getBacteriaPopulation(bacteriaName);
            if (bacteriaPopulation.isPresent()) {
                int foodFed = this.foodManager.feed(foodType, quantity); // Aquí se llama al método feed en FoodManager
                // Aquí puedes agregar el código para actualizar la población de bacterias con la comida alimentada
            } else {
                throw new IllegalArgumentException("No bacteria population with the given name in the current experiment.");
            }
        } else {
            throw new IllegalStateException("No current experiment. Please create a new experiment first.");
        }
    }
}