package ma.elkarroudi.utils.service;

import ma.elkarroudi.utils.exceptions.ResourceNotFoundException;
import ma.elkarroudi.utils.service.helper.IGenericServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

public interface IGenericService<T, ID, REQ, RES> extends IGenericServiceHelper<T, ID, REQ, RES> {

    Logger logger = LoggerFactory.getLogger(IGenericService.class);

    /**
     * Retrieves all entities of type T, paginated.
     *
     * @param pageable pagination information
     * @return a page of response DTOs
     */
    default Page<RES> findAll(Pageable pageable) {
        Page<T> entities = getRepository().findAll(pageable);
        return getMapper().toResponseDTOs(entities);
    }

    /**
     * Retrieves all entities of type T.
     *
     * @return a list of response DTOs
     */
    default List<RES> findAll() {
        List<T> entities = getRepository().findAll();
        return getMapper().toResponseDTOs(entities);
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity
     * @throws ResourceNotFoundException if the entity is not found
     */
    default T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
    }

    /**
     * Retrieves an entity by its ID and maps it to a response DTO.
     *
     * @param id the ID of the entity to retrieve
     * @return the response DTO
     * @throws ResourceNotFoundException if the entity is not found
     */
    default RES findByIdAndMapToResponse(ID id) {
        return getMapper().toResponseDTO(findById(id));
    }

    /**
     * Creates a new entity from the given request DTO.
     *
     * @param requestDTO the DTO containing the data for the new entity
     * @return the created entity
     */
    @Transactional
    default T create(REQ requestDTO) {
        logger.info("Creating entity with data: {}", requestDTO);

        T entity = getMapper().toEntity(requestDTO);
        return getRepository().save(entity);
    }

    /**
     * Updates an existing entity with the given ID using data from the request DTO.
     *
     * @param id the ID of the entity to update
     * @param dto the DTO containing the updated data
     * @return the updated entity
     * @throws ResourceNotFoundException if the entity is not found
     */
    @Transactional
    default T update(ID id, REQ dto) {
        logger.info("Updating entity with id: {} and data: {}", id, dto);

        return findAndExecute(id, entity -> {
            getMapper().updateEntity(entity, dto);
            return getRepository().save(entity);
        });
    }

    /**
     * Deletes an entity with the given ID.
     *
     * @param id the ID of the entity to delete
     * @throws ResourceNotFoundException if the entity is not found
     */
    @Transactional
    default void delete(ID id) {
        logger.info("Deleting entity with id: {}", id);

        findAndExecute(id, entity -> {
            getRepository().delete(entity);
            return null;
        });
    }

    /**
     * Checks if an entity with the given ID exists.
     *
     * @param id the ID to check
     * @return true if the entity exists, false otherwise
     */
    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    /**
     * Counts the total number of entities.
     *
     * @return the total count of entities
     */
    default long count() {
        return getRepository().count();
    }

    /**
     * Finds an entity by ID and executes a function on it.
     *
     * @param id the ID of the entity to find
     * @param function the function to execute on the found entity
     * @param <R> the return type of the function
     * @return the result of the function execution
     * @throws ResourceNotFoundException if the entity is not found
     */
    default <R> R findAndExecute(ID id, Function<T, R> function) {
        T entity = findById(id);
        return function.apply(entity);
    }

    /**
     * Retrieves entities by a list of IDs.
     *
     * @param ids the list of IDs
     * @return a list of entities
     */
    default List<T> findAllByIds(List<ID> ids) {
        return getRepository().findAllById(ids);
    }

    /**
     * Deletes entities by a list of IDs.
     *
     * @param ids the list of IDs
     */
    @Transactional
    default void deleteAllByIds(List<ID> ids) {
        ids.forEach(this::delete);
    }

}


