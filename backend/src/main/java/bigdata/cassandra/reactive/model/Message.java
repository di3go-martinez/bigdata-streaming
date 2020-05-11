package bigdata.cassandra.reactive.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Date;

@Table("data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @PrimaryKey
    private String id;

    private  String label;

    private  Long value;

    private Date created_at;

}
