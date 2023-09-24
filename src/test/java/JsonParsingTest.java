import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Countries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonParsingTest {
    private static final ClassLoader cl = JsonParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение и проверка содержимого файла .json")
    public void testJsonParsing() throws IOException {

        try (InputStream stream = cl.getResourceAsStream("glossary.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Countries> countries = objectMapper.readValue(stream, new TypeReference<>() {
            });

            Assertions.assertThat(countries).hasSize(3);

            Countries firstCountry = countries.get(0);
            Assertions.assertThat(firstCountry.getId()).isEqualTo(1);
            Assertions.assertThat(firstCountry.getCountry()).isEqualTo("Spain");
            Assertions.assertThat(firstCountry.getCapital()).isEqualTo("Madrid");
            Assertions.assertThat(firstCountry.getContinent()).isEqualTo("Europe");

            Countries secondCountry = countries.get(1);
            Assertions.assertThat(secondCountry.getId()).isEqualTo(2);
            Assertions.assertThat(secondCountry.getCountry()).isEqualTo("Australia");
            Assertions.assertThat(secondCountry.getCapital()).isEqualTo("Canberra");
            Assertions.assertThat(secondCountry.getContinent()).isEqualTo("Australia");

            Countries thirdCountry = countries.get(2);
            Assertions.assertThat(thirdCountry.getId()).isEqualTo(3);
            Assertions.assertThat(thirdCountry.getCountry()).isEqualTo("Brazil");
            Assertions.assertThat(thirdCountry.getCapital()).isEqualTo("Brazilia");
            Assertions.assertThat(thirdCountry.getContinent()).isEqualTo("South America");

        }
    }
}
