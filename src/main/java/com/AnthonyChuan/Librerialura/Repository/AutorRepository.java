package com.AnthonyChuan.Librerialura.Repository;

import com.AnthonyChuan.Librerialura.Model.Autor;
import com.AnthonyChuan.Librerialura.Model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento > :fecha OR a.fechaFallecimiento IS NULL")
    List<Autor> buscarAutoresVivos(@Param("fecha") Integer fecha);

    @Query("SELECT l FROM Libros l WHERE l.idioma = :idioma")
    List<Libros> buscarLibrosPorIdioma(@Param("idioma") String idioma);

    @Query("SELECT l FROM Libros l")
    List<Libros> buscarTodosLosLibros();

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(int fechaNacimiento, int fechaFallecimiento);
    List<Autor> findByFechaFallecimientoIsNullOrFechaFallecimientoGreaterThanEqual(int fechaFallecimiento);

    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);
}
