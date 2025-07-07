package pe.com.bn.maie.tranversal.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.com.bn.maie.persistencia.dto.LlaveBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonToLlaveBodyConverter {

    private static int orderCounter = 0; // To assign order sequentially during parsing

    /**
     * Converts a JSON InputStream into a flat list of LlaveBody objects.
     * Nested objects/arrays will be represented as separate LlaveBody entries
     * with 'esPadre' flag set, but without automatic idLlavePadre linkage
     * (as idLlavePadre is a database ID).
     *
     * @param jsonStream The InputStream of the JSON file.
     * @return A list of LlaveBody objects.
     * @throws IOException If an I/O error occurs during JSON parsing.
     */
    public static List<LlaveBody> convertJsonToLlaveBodyList(InputStream jsonStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonStream);
        List<LlaveBody> llaves = new ArrayList<>();
        orderCounter = 0; // Reset counter for each new conversion

        // Start extraction from the root node with no parent ID
        extractLlaves(rootNode, llaves); 
        return llaves;
    }

    /**
     * Recursive method to extract LlaveBody information from JSON nodes.
     *
     * @param node The current JsonNode being processed.
     * @param llaves The list where extracted LlaveBody objects are added.
     */
    private static void extractLlaves(JsonNode node, List<LlaveBody> llaves) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String tagName = field.getKey();
                JsonNode valueNode = field.getValue();

                LlaveBody llave = new LlaveBody();
                llave.setTagName(tagName);
                llave.setOrden(++orderCounter); // Assign sequential order
                llave.setIdLlavePadre(null); // idLlavePadre cannot be determined from JSON structure alone (it's a DB ID)

                // Determine tipoDatoEsperado and esPadre based on the JSON node type
                if (valueNode.isObject() || valueNode.isArray()) {
                    llave.setEsPadre(1); // It's a parent (Object or Array)
                    llave.setTipoDatoEsperado(6); // Type 6 for Object/Array
                    llave.setValorDefecto(null); // No default value for complex types directly
                    // Recursively process children
                    extractLlaves(valueNode, llaves);
                } else {
                    llave.setEsPadre(0); // It's a child (primitive value)
                    llave.setTipoDatoEsperado(determineTipoDatoEsperado(valueNode));
                    llave.setValorDefecto(valueNode.asText()); // Set default value from JSON primitive
                }

                // Default values for other fields if not explicitly in JSON or inferred
                llave.setIndObligatorio(0); // Default to not mandatory
                llave.setIndMapeable(0);    // Default to not mappable
                llave.setDescripcion("");   // Default empty description

                llaves.add(llave);
            }
        } else if (node.isArray()) {
            // If it's an array, process its elements.
            // For simplicity, we process each element if it's an object.
            // Primitive arrays will be represented by their parent's 'esPadre' and 'tipoDatoEsperado'.
            for (JsonNode elementNode : node) {
                if (elementNode.isObject() || elementNode.isArray()) {
                    extractLlaves(elementNode, llaves); // Recursively process nested objects/arrays within the array
                }
            }
        }
        // Primitive values (not part of an object's field) are not typically handled at the root level
        // by this method, as it expects objects or arrays containing fields.
    }

    /**
     * Determines the expected data type for a primitive JSON node.
     * Maps JSON types to integer codes used in LlaveBody.
     * (1: String, 2: Integer, 3: Decimal, 4: Boolean, 5: Date - not directly inferred, 6: Object/Array)
     *
     * @param node The JsonNode representing a primitive value.
     * @return The integer code for the expected data type.
     */
    private static Integer determineTipoDatoEsperado(JsonNode node) {
        if (node.isTextual()) {
            // Could be a date string, but without schema, treat as general String
            return 1; // String
        } else if (node.isIntegralNumber()) {
            return 2; // Integer
        } else if (node.isFloatingPointNumber()) {
            return 3; // Decimal
        } else if (node.isBoolean()) {
            return 4; // Boolean
        }
        return 1; // Default to String for unknown or null types
    }
}
