package ma.elkarroudi.utils.controller;

import lombok.RequiredArgsConstructor;
import ma.elkarroudi.utils.api.success.Success;
import ma.elkarroudi.utils.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class AGenericController<T, ID, REQ, RES> {

    protected final IGenericService<T, ID, REQ, RES> service;

    /**
     * Retrieves all entities, paginated.
     *
     * @return ResponseEntity containing a page of response DTOs
     */
    public ResponseEntity<Success<Page<RES>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<RES> entities = service.findAll(pageable);
        return ResponseEntity.ok(
                new Success<>(
                        HttpStatus.OK.value(),
                        entities
                )
        );
    }


    /**
     * Retrieves all entities without pagination.
     *
     * @return ResponseEntity containing a list of all response DTOs
     */
    @GetMapping("/all")
    public ResponseEntity<Success<List<RES>>> findAll() {
        List<RES> entities = service.findAll();
        return ResponseEntity.ok(
                new Success<>(
                        HttpStatus.OK.value(),
                        entities
                )
        );
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return ResponseEntity containing the response DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<Success<RES>> findById(@PathVariable ID id) {
        RES entity = service.findByIdAndMapToResponse(id);
        return ResponseEntity.ok(
                new Success<>(
                        HttpStatus.OK.value(),
                        entity
                )
        );
    }

    /**
     * Creates a new entity.
     *
     * @param requestDTO the DTO containing the data for the new entity
     * @return ResponseEntity containing the response DTO of the created entity
     */
    @PostMapping
    public ResponseEntity<Success<RES>> create(@RequestBody REQ requestDTO) {
        T createdEntity = service.create(requestDTO);
        RES responseDTO = service.getMapper().toResponseDTO(createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Success<>(
                        HttpStatus.CREATED.value(),
                        responseDTO
                )
        );
    }

    /**
     * Updates an existing entity.
     *
     * @param id the ID of the entity to update
     * @param requestDTO the DTO containing the updated data
     * @return ResponseEntity containing the response DTO of the updated entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Success<RES>> update(@PathVariable ID id, @RequestBody REQ requestDTO) {
        T updatedEntity = service.update(id, requestDTO);
        RES responseDTO = service.getMapper().toResponseDTO(updatedEntity);
        return ResponseEntity.ok(
                new Success<>(
                        HttpStatus.OK.value(),
                        responseDTO
                )
        );
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Success<Void>> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.ok(
                new Success<>(
                        HttpStatus.OK.value(),
                        null
                )
        );
    }

}
