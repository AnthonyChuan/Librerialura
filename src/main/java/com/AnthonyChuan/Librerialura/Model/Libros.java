package com.AnthonyChuan.Librerialura.Model;

import jakarta.persistence.*;

@Entity
@Table(name= "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @Column(name = "idioma")
    private String idioma;
    private Double numeroDescargas;

    public Libros() {
    }

    public Libros(DatosLibros datosLibros,Autor autor) {
        this.titulo=datosLibros.titulo();
        this.autor=autor;
        if (datosLibros.idioma().isEmpty()){
        this.idioma ="Desconocido";
        }else{
            this.idioma =datosLibros.idioma().get(0);
        }
        this.numeroDescargas= datosLibros.numeroDescargas();

    }

    public Libros(String titulo, String idioma, Autor autor, Double aDouble) {
    }

    @Override
    public String toString() {
        return "\n" +"///////////////Libros///////////////////" +"\n"+
                "titulo=" + titulo + "\n" +
                "autor=" + autor.getNombre() +"\n"+
                "idiomas=" + idioma +"\n"+
                "numeroDescargas=" + numeroDescargas +"\n"+
                "///////////////////////////////////////////"+"\n" ;
    }

    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
