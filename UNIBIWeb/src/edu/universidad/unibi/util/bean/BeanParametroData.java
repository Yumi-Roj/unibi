package edu.universidad.unibi.util.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "beanParametroData")
@ApplicationScoped
public class BeanParametroData {
    
    /**
     * M�ximo cantidad de ejemplares de pr�stamo por d�a.
     */
    private int maxPrestamosDia = 2;
    
    /**
     * M�xima cantidad de d�as de pr�stamo.
     */
    private int maxDiasPrestamo = 3;
    
    /**
     * M�xima cantidad de d�as de atraso en la devoluci�n 
     * antes de considerar una notificaci�n para sanci�n del lector.
     */
    private int maxDiasDevolucionAtraso = 7;
    
    /**
     * M�xima cantidad de d�as de atraso en la devoluci�n
     * antes de considerar el ejemplar como perdido.
     */
    private int maxDiasPrestamoPerdida = 10;
    
    public BeanParametroData() {
    }


    public void setMaxPrestamosDia(int maxPrestamosDia) {
        this.maxPrestamosDia = maxPrestamosDia;
    }

    public int getMaxPrestamosDia() {
        return maxPrestamosDia;
    }

    public void setMaxDiasPrestamo(int maxDiasPrestamo) {
        this.maxDiasPrestamo = maxDiasPrestamo;
    }

    public int getMaxDiasPrestamo() {
        return maxDiasPrestamo;
    }

    public void setMaxDiasDevolucionAtraso(int maxDiasDevolucionAtraso) {
        this.maxDiasDevolucionAtraso = maxDiasDevolucionAtraso;
    }

    public int getMaxDiasDevolucionAtraso() {
        return maxDiasDevolucionAtraso;
    }

    public void setMaxDiasPrestamoPerdida(int maxDiasPrestamoPerdida) {
        this.maxDiasPrestamoPerdida = maxDiasPrestamoPerdida;
    }

    public int getMaxDiasPrestamoPerdida() {
        return maxDiasPrestamoPerdida;
    }
}
