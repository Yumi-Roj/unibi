package edu.universidad.unibi.cubusquedaejemplar;

import edu.universidad.dominio.unibi.TblAutores;
import edu.universidad.dominio.unibi.TblCatalogos;
import edu.universidad.dominio.unibi.TblCatalogosItems;
import edu.universidad.dominio.unibi.TblEjemplares;
import edu.universidad.dominio.unibi.TblPrestamos;
import edu.universidad.dominio.unibi.TblPrestamosDetalle;
import edu.universidad.dominio.unibi.TblPublicaciones;
import edu.universidad.dominio.unibi.TblPublicacionesAutores;
import edu.universidad.dominio.unibi.TblPublicacionesCarreras;
import edu.universidad.dominio.unibi.TblSanciones;
import edu.universidad.dominio.unibi.TblUsuarios;
import edu.universidad.unibi.cubusquedaejemplar.dto.dtoBusquedaEjemplar;
import edu.universidad.unibi.curegistrocatalogo.dto.DtoArea;
import edu.universidad.unibi.cubusquedaejemplar.dto.dtoEjemplaresPrestados;
import edu.universidad.unibi.cubusquedaejemplar.dto.dtoUsuario;
import edu.universidad.unibi.util.Bo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;


public class BoBusquedaEjemplarImpl extends Bo implements BoBusquedaEjemplar {
    //codigo COMPARTIDO ----------------------------------------------------------------------------
    private String getStringFromDate(Date fecha,String formato){
        SimpleDateFormat formateador = new SimpleDateFormat(formato); 
        return formateador.format(fecha);
    }
    private Date getDateFromString(String fecha, String formato){
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        try {
            return (Date) formateador.parse(fecha);
        } catch (ParseException e) {
            return null;
        }
    }
    //---- fin codigo COMPARTIDO -------------------------------------------------------------------
    
    //---- codigo para la vista BUSQUEDA EJEMPLAR --------------------------------------------------
    public List<DtoArea>  buscarPublicacionesPorArea(String textoBusqueda) {
            System.out.println("consultarAreas");
            List<DtoArea> lst = new ArrayList<DtoArea>();
            DtoArea dto = null;
            Query query = em.createNamedQuery("TblCatalogosItems.consultarPorCatalogo");
            TblCatalogos cat = em.find(TblCatalogos.class, 1);
            query.setParameter("catalogoId", cat);
            List<TblCatalogosItems> lstCatItems = query.getResultList();
            for (TblCatalogosItems catItem : lstCatItems) {
                System.out.println("catItem=" + catItem);
                dto = new DtoArea();
                dto.setDescripcion(catItem.getDescripcion());
                dto.setId(catItem.getId());
                lst.add(dto);
            }
            return lst;
    }
    
    public List<dtoBusquedaEjemplar> consultarEjemplarPorAutor(String nombre){
               List<dtoBusquedaEjemplar> lstDto = new ArrayList<dtoBusquedaEjemplar>();
                   Query query = em.createNamedQuery("TblAutores.consultarPorNombre");
                String cadenaBusqueda = "%" + nombre.toUpperCase() + "%";
                System.out.println("cadenaBusqueda=" + cadenaBusqueda);
                query.setParameter("nombre", cadenaBusqueda);
                List<TblAutores> lstTbl = query.getResultList();
                System.out.println("lstTbl = " + ((lstTbl == null) ? -1 : lstTbl.size()));
                dtoBusquedaEjemplar dto = null;
                
                for (TblAutores p : lstTbl) {
                    dto = new dtoBusquedaEjemplar();
                    dto.setAutor(p.getNombre());
                lstDto.add(dto);
                }
                
            return lstDto;
        }
    
    public List<dtoBusquedaEjemplar> consultarEjemplarPorTitulo(String titulo){
                List<dtoBusquedaEjemplar> lstDto = new ArrayList<dtoBusquedaEjemplar>();
                Query query = em.createNamedQuery("TblLibros.consultarPorTitulo");
                String cadenaBusqueda = "%" + titulo.toUpperCase() + "%";
                System.out.println("cadenaBusqueda=" + cadenaBusqueda);
                query.setParameter("titulo", cadenaBusqueda);
                List<TblPublicaciones> lstTbl = query.getResultList();
                System.out.println("lstTbl = " + ((lstTbl == null) ? -1 : lstTbl.size()));
                dtoBusquedaEjemplar dto = null;
                for (TblPublicaciones p : lstTbl) {
                    System.out.println("p.getTitulo=" + p.getFechaPublicacion());
                    dto = new dtoBusquedaEjemplar();
                    dto.setId(p.getId());
                    dto.setTitulo(p.getTitulo());
                                
                    List<TblPublicacionesAutores> lstPubAut = p.getTblPublicacionesAutoresList();
                    String autores = "";
                    for (TblPublicacionesAutores pa : lstPubAut) {
                        TblAutores a = pa.getAutorId();
                        autores += a.getNombre() + ", ";
                    }
                    List<TblPublicacionesCarreras> lstPubCa=p.getTblPublicacionesCarrerasList();
                    String carreras="";
                    for(TblPublicacionesCarreras par : lstPubCa){
                        TblCatalogosItems ar=par.getCarreraId();
                        carreras += ar.getDescripcion() + ", ";
                    }
                    List<TblEjemplares> lstPubPre=p.getTblEjemplaresList();
                    String estado="";
                    for(TblEjemplares pe : lstPubPre){
                        estado += pe.getEstadoFisico() + ", ";
                    }
                    
                    dto.setArea(carreras);
                    dto.setAutor(autores);
                   
                    lstDto.add(dto);
                }
                return lstDto;
            }
    
    //---- fin busqueda ejemplar -------------------------------------------------------------------
    
    
    
    //---- codigo para la vista LISTA PRESTAMO -----------------------------------------------------
    protected TblUsuarios usuario; //usuario para la vista listaPrestamos y PrestamosRealizados
    
    public dtoUsuario getUsuarioDto(String nroDocumento){
        usuario= getUsuarioTbl(nroDocumento);
        
        if (usuario!=null){
            Boolean tienePrestamosActivos=false;
            if (getEstadoPrestamosActivos()!=""){tienePrestamosActivos=true;}
            return new dtoUsuario(nroDocumento, getApellidosNombresUsuario(),getEstadoUsuario(),tienePrestamosActivos);     
        }else{
            return new dtoUsuario(nroDocumento,null,"Sin registrarse",false);
        }
    }
        
    private TblUsuarios getUsuarioTbl(String dni) {
        Query query = em.createNamedQuery("TblUsuarios.consultarPorNumeroDocumento");
        query.setParameter("numeroDocumento",dni);
        List<TblUsuarios> busqueda = query.getResultList();
        
        //EXCEPTION: edu.universidad.dominio.unibi.TblUsuarios cannot be cast to edu.universidad.dominio.unibi.TblUsuarios
        //Evento: al realizar run por segunda vez a la pagina.
        //Solucion; cerrar UNIBI e IntegratedWebLogicServer y volver a iniciar
        if (busqueda.size()>0){
            TblUsuarios usuario= busqueda.get(0);
            System.out.println("se encontro al usuario="+usuario.getNumeroDocumento());
            return usuario;
        }else{
            return null;
        }
    }
    private String getApellidosNombresUsuario(){
        return usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+" "+usuario.getNombres();
    }
    private String getEstadoUsuario(){
        String estado;
        estado=getEstadoAmonestado();
        if (estado==""){estado=getEstadoPrestamosActivos();}
        if (estado==""){estado="Registrado";}
        return estado;
    }
    private String getEstadoAmonestado() {
        String estado="";
        if (usuario!=null){
            TblSanciones sancionMasLarga=null;
            List<TblSanciones> sanciones=usuario.getTblSancionesList();
            
            //revisar las sanciones
            for (TblSanciones sancion:sanciones){
                if (sancionMasLarga==null){sancionMasLarga=sancion;}
                if (sancionMasLarga.getFechaFin().before(sancion.getFechaFin())){sancionMasLarga=sancion;}
            }
            
            //elaborar mensaje de amonestacion
            if (sancionMasLarga!=null){return "Esta amonestado hasta "+getStringFromDate(sancionMasLarga.getFechaFin(),"dd/MM/yyyy")+"/n motivo:"+sancionMasLarga.getMotivo();}
        }
        
        return estado;
    }
    private String getEstadoPrestamosActivos(){
        String estado="";
        int nroEjemplares=0;
        
        if (usuario!=null){
            List<TblPrestamos> prestamos = usuario.getTblPrestamosList();
            for (TblPrestamos prestamo:prestamos){
                nroEjemplares+=prestamo.getTblPrestamosDetalleList().size();        
            }
        }
        
        if (nroEjemplares>0){return "Tiene ("+String.valueOf(nroEjemplares)+") ejemplares prestados a la fecha.";}
        
        return estado;
    }

    //---- fin lista prestamo ----------------------------------------------------------------------
    
    
    
    //---- codigo para la vista PRESTAMOS REALIZADOS -----------------------------------------------
     public List<dtoEjemplaresPrestados> getlistaEjemplaresPrestados() {
        List<dtoEjemplaresPrestados> listaDtoEjemplaresPrestados=new ArrayList<dtoEjemplaresPrestados>();
        
        List<TblPrestamos> prestamosRealizados=usuario.getTblPrestamosList();
        List<TblPrestamosDetalle> detallesPrestamo=new ArrayList<TblPrestamosDetalle>();
        List<TblEjemplares> ejemplaresPrestados=new ArrayList<TblEjemplares>();
        
        
        for (TblPrestamos prestamo:prestamosRealizados){
            System.out.println("examinando prestamo id:"+prestamo.getId());
            detallesPrestamo=prestamo.getTblPrestamosDetalleList();
            
            for(TblPrestamosDetalle prestamoDetalles:detallesPrestamo){
                TblEjemplares ejemplar=prestamoDetalles.getEjemplarId();
                TblPublicaciones publicacion =ejemplar.getPublicacionId();
                
                //nullPointerException
                String autores = "";
                String titulo="";
                if (publicacion!=null) {
                    //obtener lista de autores
                    List<TblPublicacionesAutores> listaAutores = publicacion.getTblPublicacionesAutoresList();
                    for (int k=0;k<=listaAutores.size()-1;k++){
                        if (k>0){autores+=",";}
                        autores+=listaAutores.get(k).getAutorId().getNombre();
                    }
                    
                    //obtener el titulo
                    titulo=publicacion.getTitulo();
                }
                else {
                    System.out.println("la publicacion del ejemplar esta vacia e="+ejemplar.getId());
                }
                
                String estadoFisico="";
                switch(ejemplar.getEstadoFisico()) {
                case 0:
                    estadoFisico="Bueno";
                    break;
                case 1:
                    estadoFisico="Regular";
                    break;
                case 3:
                    estadoFisico="Malo";
                    break;
                }

                SimpleDateFormat formateador=new SimpleDateFormat("dd-MM-yyyy");
                String fechaMaxDevolucion = formateador.format(prestamo.getFechaDevolucionMax());

                dtoEjemplaresPrestados dtoEjemplar=new dtoEjemplaresPrestados(ejemplar.getId(),titulo,autores,estadoFisico,fechaMaxDevolucion);
                System.out.println("agregando a la lista final:"+dtoEjemplar.getTitulo());
                listaDtoEjemplaresPrestados.add(dtoEjemplar);
            }
        }
        
        return listaDtoEjemplaresPrestados;
    }
    
        
    //---- fin prestamos realizados ----------------------------------------------------------------
}
