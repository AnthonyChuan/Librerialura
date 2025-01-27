package com.AnthonyChuan.Librerialura.Repository;

import com.AnthonyChuan.Librerialura.Model.Libros;
import com.AnthonyChuan.Librerialura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros,Long> {
    Optional<Libros> findById(Long id);

    List<Libros> findByTitulo(String titulo);

    @Query("SELECT l FROM Libros l WHERE l.idioma = :idioma")
    List<Libros> buscarLibrosPorIdioma(@Param("idioma") String idioma);

}
