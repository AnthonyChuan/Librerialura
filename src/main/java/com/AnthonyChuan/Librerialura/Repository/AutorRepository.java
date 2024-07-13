package com.AnthonyChuan.Librerialura.Repository;

import com.AnthonyChuan.Librerialura.Model.Autor;
import com.AnthonyChuan.Librerialura.Model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(int fechaNacimiento, int fechaFallecimiento);

    Autor findByNombre(String nombre);

}
