package dao;

import java.util.List;

public interface CrudOperations<E> {
    List<E> getAll();

    E findById(int id);

    // Both create (if does not exist) or update (if exist) entities
    List<E> saveAll(List<E> entities);
}
