package bigdata;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@JsonSerialize
public class Message implements Serializable {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Message(@JsonProperty("label") String label, @JsonProperty("value")  Long value) {
        this.label = label;
        this.value = value;
    }

    private final String label;

    private final Long value;

    private final String id = UUID.randomUUID().toString();

    private final java.sql.Timestamp created_at = new  java.sql.Timestamp(new Date().getTime());

}
