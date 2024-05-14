
package model;

import java.io.IOException;
import java.util.List;

public interface Manejoex {
    void saveExperiment(experimento experiment);
    void saveExperimentAs(String currentFileName, String newFileName);
    experimento loadExperiment(String fileName) throws IOException; // Añade 'throws IOException' aquí
    List<experimento> getExperiments();
}
