package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void usingZoneOffset() throws JsonProcessingException {
        ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
        assertSerializeAndSerialize(date); // fails!
    }

    @Test
    void usingZoneId() throws JsonProcessingException {
        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("UTC"));
        assertSerializeAndSerialize(date);
    }

    private void assertSerializeAndSerialize(final ZonedDateTime date) throws JsonProcessingException {
        final Example example1 = new Example(date);
        final String json = objectMapper.writeValueAsString(example1);
        final Example example2 = objectMapper.readValue(json, Example.class);

        assertEquals(example1.getDate(), example2.getDate());
    }

    static class Example {
        private ZonedDateTime date;

        public Example() {
        }

        public Example(final ZonedDateTime date) {
            this.date = date;
        }

        public ZonedDateTime getDate() {
            return date;
        }
    }

}
