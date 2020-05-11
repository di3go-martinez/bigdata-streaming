package bigdata.transfer;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@JsonSerialize
public class Message {

    private final String label;

    private final Long value;

}
