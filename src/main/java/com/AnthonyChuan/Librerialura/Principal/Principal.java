package com.AnthonyChuan.Librerialura.Principal;

import com.AnthonyChuan.Librerialura.Model.*;
import com.AnthonyChuan.Librerialura.Repository.AutorRepository;
import com.AnthonyChuan.Librerialura.Repository.LibrosRepository;
import com.AnthonyChuan.Librerialura.Service.ConsumoApi;
import com.AnthonyChuan.Librerialura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class Principal {
    private Scanner teclado=new Scanner(System.in);
    private ConsumoApi consumoApi=new ConsumoApi();
    private ConvierteDatos convierteDatos=new ConvierteDatos();
    private String URLbase="https://gutendex.com/books/";
    private String json=consumoApi.obtenerDatos(URLbase);
    private final AutorRepository autorRepository;
    private final LibrosRepository librosRepository;
    @Autowired
    public Principal(LibrosRepository librosRepository, AutorRepository autorRepository) {
        this.librosRepository = librosRepository;
        this.autorRepository = autorRepository;
    }
    public void muestraMenu(){
        var respuesta=-1;
        while (respuesta != 0){
            var menu= """
                    1-Buscar libro por nombre
                    2-Lista de libros registrados
                    3-Lista de autores registrados
                    4-Lista autores vivos
                    5-Buscar por idioma
                    0-Salir
                    """;
            System.out.println(menu);

                respuesta = teclado.nextInt();
                teclado.nextLine();

            switch (respuesta){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    manejarMenuIdiomas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida");
            }

        }

    }



    private DatosLibros getLibro(){
        System.out.println("Ingrese el libro a buscar: ");
        var libro=teclado.nextLine();

        json=consumoApi.obtenerDatos(URLbase+"?search="+libro.replace(" ","%20"));
        Datos datosBusqueda=convierteDatos.obtenerDatos(json,Datos.class);

        if (datosBusqueda.resultados() != null && !datosBusqueda.resultados().isEmpty()) {
            return datosBusqueda.resultados().get(0);
        }
        return null;

    }

    private Libros almacenarLibro(DatosLibros datosLibros,Autor autor){
        List<Libros> existenciaLibro=librosRepository.findByTitulo(datosLibros.titulo());
        if (!existenciaLibro.isEmpty()){
            return existenciaLibro.get(0);
        }
        Libros libro = new Libros(datosLibros, autor);
        return librosRepository.save(libro);

    }

    private void buscarLibroPorTitulo() {
        DatosLibros datos = getLibro();
        if (datos != null) {
            Autor autor = datos.autor().stream()
                    .map(a -> {
                        Autor existingAutor = autorRepository.findByNombre(a.nombre());
                        if (existingAutor != null) {
                            return existingAutor;
                        } else {
                            Autor nuevoAutor = new Autor(a);
                            return autorRepository.save(nuevoAutor);
                        }
                    })
                    .findFirst()
                    .orElse(null);
            if (autor != null) {
                Libros libro = almacenarLibro(datos, autor);
                System.out.println(/*"Libro almacenado:  "+ */libro);
            }
        } else {
            System.out.println("********************" + "\n" +
                    "No se encontro titulo" + "\n" +
                    "********************" + "\n");
        }
    }
    private void listarLibrosRegistrados() {
        System.out.println("Los libros registrados son: ");
        List<Libros> libros = librosRepository.findAll();
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("No hay libros registrados");
        }
    }
    private void listarAutoresRegistrados() {
        System.out.println("Los autores registrados son: ");
        List<Autor> autores = autorRepository.findAll();
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No hay autores registrados");
        }
    }
    private void listarAutoresVivos() {
        System.out.println("Escribe el año que quieras saber qué autor estaba vivo: ");
        int fecha = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autores = autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(fecha, fecha);
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encotraron autores vivos en el año: "+fecha);
        }
    }
    private void mostrarMenuIdiomas() {
        System.out.println("1. Español "+"\n"+
                            "2. Francés "+"\n"+
                            "3. Inglés "+"\n"+
                            "4. Portugués "+"\n"+
                            "0. Volver al menú principal");
    }

    public void manejarMenuIdiomas() {
        int opcionIdioma = -1;
        while (opcionIdioma != 0) {
            mostrarMenuIdiomas();
            opcionIdioma = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer del scanner

            switch (opcionIdioma) {
                case 1:
                    listarLibrosPorIdioma("es");
                    break;
                case 2:
                    listarLibrosPorIdioma("fr");
                    break;
                case 3:
                    listarLibrosPorIdioma("en");
                    break;
                case 4:
                    listarLibrosPorIdioma("pt");
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

        }
    }
    private String obtenerNombreIdioma(String siglaIdioma) {
        switch (siglaIdioma) {
            case "es":
                return "Español";
            case "fr":
                return "Francés";
            case "en":
                return "Inglés";
            case "pt":
                return "Portugués";
            default:
                return "Desconocido";
        }
    }

    private void listarLibrosPorIdioma(String idioma) {
        String nombreIdioma = obtenerNombreIdioma(idioma);
        List<Libros> libros = librosRepository.buscarLibrosPorIdioma(idioma);
        if (!libros.isEmpty()) {
            System.out.println("Los libros en " + nombreIdioma + " son: ");
            libros.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en el idioma " + nombreIdioma + ".");
        }
    }

}
