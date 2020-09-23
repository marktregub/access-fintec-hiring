package accessfintec.hiring.reader;

import java.util.List;

public class SourcesReader {

    public SourcesReader(List<String> sources) {

    }



    private void validateSources(List<String> sources) {
        for(String source: sources) {
            if(source.endsWith(".csv") || source.endsWith(".json")) {

            }
        }
    }

}
