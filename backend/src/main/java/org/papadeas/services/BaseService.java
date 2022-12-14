package org.papadeas.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.papadeas.dto.BaseDto;
import org.papadeas.mappers.BaseMapper;
import org.papadeas.model.EntityBase;
import org.papadeas.repositories.GenericRepository;
import org.papadeas.services.interfaces.ServiceBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Transactional
@RequiredArgsConstructor
public abstract class BaseService<E extends EntityBase, D extends BaseDto> implements
        ServiceBase<E, D> {

    /**
     * the repository of the given entity
     */
    private GenericRepository<E> repository;

    /**
     * the mapper used for entity <--> DTO conversions
     */
    private BaseMapper<E, D> mapper;


    @Override
    public D create(D dto) {
        E entity = mapper.mapToEntity(dto);

        return mapper.mapToDTO(getRepository().save(entity));
    }

    @Override
    public D update(D dto) {
        E entity = mapper.mapToEntity(dto);

        return getMapper().mapToDTO(repository.saveAndFlush(entity));
    }

    @Override
    public D findById(String id) {
        return mapper.mapToDTO(findResource(id));
    }

    @Override
    public E findResource(String id) {
        return Objects.nonNull(id) ? Objects.requireNonNull(repository.findById(id).orElse(null)) : null;
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    /**
     * Utility method that takes a {@code List} and the paging requirements, and returns them paged
     *
     * @param page    the page requested
     * @param size    the size of the page
     * @param content the total content
     * @return a {@code Page} containing the results
     */
    public <T> Page<T> getPage(int page, int size, List<T> content) {

        PageRequest pageable = PageRequest.of(page, size);
        long start = Math.min(Math.max(pageable.getOffset(), 0), content.size());
        long end = Math.min((start + pageable.getPageSize()), content.size());
        return new PageImpl<>(content.subList(Math.toIntExact(start), Math.toIntExact(end)),
                pageable, content.size());
    }
}
