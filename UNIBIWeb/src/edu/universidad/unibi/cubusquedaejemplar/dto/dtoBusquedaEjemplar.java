package edu.universidad.unibi.cubusquedaejemplar.dto;

public class dtoBusquedaEjemplar {
    public dtoBusquedaEjemplar(String codigo, String titulo, String autor, String area, String estadoFisico, String disponibilidad, String fechaDevolucion) {
        super();
        
        this.codigo=codigo;
        this.titulo=titulo;
        this.autor=autor;
        this.area=area;
        this.estadoFisico=estadoFisico;
        this.disponibilidad=disponibilidad;
        this.fechaDevolucion=fechaDevolucion;
    }
    public dtoBusquedaEjemplar()
     {}

    protected String Id;
    protected String codigo;
    protected String titulo;
    protected String autor;
    protected String area;
    protected String estadoFisico;
    protected String disponibilidad;
    protected String fechaDevolucion;
    protected Boolean seleccionado=false;

    public void setId(String Id) {
           this.Id = Id;
    }

     public String getId() {
           return Id;
    }
     
    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

}
