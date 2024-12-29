package ma.elkarroudi.utils.controller;

import lombok.RequiredArgsConstructor;
import ma.elkarroudi.utils.api.success.SuccessDTO;
import ma.elkarroudi.utils.groups.OnCreate;
import ma.elkarroudi.utils.groups.OnUpdate;
import ma.elkarroudi.utils.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    @GetMapping
    public ResponseEntity<SuccessDTO<Page<RES>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<RES> entities = service.findAll(pageable);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Entities retrieved successfully",
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
    public ResponseEntity<SuccessDTO<List<RES>>> findAll() {
        List<RES> entities = service.findAll();
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "All entities retrieved successfully",
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
    public ResponseEntity<SuccessDTO<RES>> findById(@PathVariable ID id) {
        RES entity = service.findByIdAndMapToResponse(id);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Entity retrieved successfully",
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
    public ResponseEntity<SuccessDTO<RES>> create(@Validated({ OnCreate.class }) @RequestBody REQ requestDTO) {
        T createdEntity = service.create(requestDTO);
        RES responseDTO = service.getMapper().toResponseDTO(createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessDTO<>(
                        HttpStatus.CREATED.value(),
                        "Entity created successfully",
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
    public ResponseEntity<SuccessDTO<RES>> update(@Validated({ OnUpdate.class }) @PathVariable ID id, @RequestBody REQ requestDTO) {
        T updatedEntity = service.update(id, requestDTO);
        RES responseDTO = service.getMapper().toResponseDTO(updatedEntity);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Entity updated successfully",
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
    public ResponseEntity<SuccessDTO<Void>> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        HttpStatus.OK.value(),
                        "Entity deleted successfully",
                        null
                )
        );
    }

}
