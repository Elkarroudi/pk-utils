package ma.elkarroudi.utils.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Document
public abstract class BaseEntity<ID> {

    @Id
    ID id;

    @CreatedDate
    @Field("createdAt")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updatedAt")
    LocalDateTime updatedAt;

}
