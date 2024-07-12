package com.AnthonyChuan.Librerialura.Service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class <T> clase);

}
