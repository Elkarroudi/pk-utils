package ma.elkarroudi.utils.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepository<T, ID> extends MongoRepository<T, ID> {
}
