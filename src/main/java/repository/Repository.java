package repository;

import java.util.List;

/**
 * Cette interface définit la DAO du projet
 *
 * @param <T>
 * @author Franck Battu
 * @version  1.0
 */
public interface Repository<T> {

    public List<T> findAll();

    public void save(T t);
}
