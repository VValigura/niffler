package guru.qa.niffler.jupiter.extension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class AllurePassedAttachmentExtension implements SuiteExtension {
    private static final ObjectMapper om = new ObjectMapper();
    private final String pathToAllureResults = "./niffler-e-2-e-tests/build/allure-results";

    @Override
    public void afterSuite() throws Exception {
        try( Stream<Path> files = Files.walk(Path.of(pathToAllureResults))){
            List<Path> allureResults = files.filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().endsWith("result.json"))
                    .toList();

            for (Path result: allureResults){
                JsonNode jsonResult = om.readTree(Files.newInputStream(result));
                if (jsonResult.get("status").asText().equals("passed") ) {
                    ((ObjectNode)jsonResult).putArray("attachments");

                    Files.write( Path.of(pathToAllureResults + "/" +  jsonResult.get("uuid").asText() + "-result.json"),
                            om.writeValueAsBytes(jsonResult));
                }
            }
        }


    }
}
