package ma.elkarroudi.utils.service;

import ma.elkarroudi.utils.mapper.IGenericMapper;
import ma.elkarroudi.utils.repository.IGenericRepository;

public interface BaseService<T, ID, REQ, RES> {

    /**
     * Retrieves the repository for the entity type T.
     * This method should return an instance of IGenericRepository that handles
     * the persistence operations for the entity of type T.
     *
     * @return the repository instance for the entity type T
     */
    IGenericRepository<T, ID> getRepository();

    /**
     * Retrieves the mapper for converting between entity type T and DTOs.
     * This method should return an instance of IGenericMapper that handles
     * the conversion between entities and DTOs (Data Transfer Objects).
     *
     * @return the mapper instance for converting between entities and DTOs
     */
    IGenericMapper<T, REQ, RES> getMapper();

}
