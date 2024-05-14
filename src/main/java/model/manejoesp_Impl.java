
package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class manejoesp_Impl implements Manejoex {
    private List<experimento> experiments;

    public void manejoesp_lmpl() {
        experiments = new ArrayList<>();
    }

    public List<experimento> getExperiments() {
        return this.experiments;
    }

    public void saveExperiment(experimento experiment) {
        this.experiments.add(experiment);
    }

    public void saveExperimentAs(String name, String description) {
        experimento experiment = new experimento(name, description);
        this.experiments.add(experiment);
    }

    public Optional<experimento> getExperimentByName(String name) { // Cambiado a public
        return experiments.stream()
                .filter(experiment -> experiment.getNombreArchivo().equals(name))
                .findFirst();
    }

    public experimento loadExperiment(String name) throws IOException {
        Optional<experimento> optionalExperiment = getExperimentByName(name);
        if (optionalExperiment.isPresent()) {
            return optionalExperiment.get();
        } else {
            throw new IOException("Experimento no encontrado");
        }
    }
}
